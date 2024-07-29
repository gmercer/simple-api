package com.github.capm.controller;

import com.github.capm.entity.User;
import com.github.capm.mapper.GrantedAuthorityRowMapper;
import com.github.capm.mapper.UserDetailRowMapper;
import com.github.capm.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    public static final String GROUP_PREFIX = "GROUP_";
    public static final String ROLE_PREFIX = "ROLE_";

    private final JdbcUserDetailsManager userDetailsManager;

    @Resource
    private UserService userService;


    public AuthenticationController(JdbcUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping("/fake")
    public User getFake(@RequestParam(value = "username", defaultValue = "FAKE") String username) {
        return new User(username, "password", false);
    }

    @GetMapping("/users")
    public List<UserDetails> getUsers() {
        return userDetailsManager.getJdbcTemplate().query("select * from users",  new UserDetailRowMapper());
    }


    @GetMapping("/users/{userName}")
    User loadUser(@PathVariable String userName) {
        return userService.getUserByName(userName);
    }

    @GetMapping("/users/create/{userName}:{password}")
    User createUser(
            @PathVariable String userName,
            @PathVariable String password
    ) {
        userDetailsManager.createUser(
                org.springframework.security.core.userdetails.User.builder()
                        .username(userName)
                        .password(password)
                        .roles("ROLE_USER")
                        .build()
        );
        addUserToGroup("USERS", userName);
        return loadUser(userName);
    }

    @GetMapping("/groups")
    List<String> findAllGroups() {
        return userDetailsManager.findAllGroups();
    }
    @GetMapping("/groups/{groupName}/users")
    List<String> getUsersInGroup(@PathVariable String groupName) {
        return userDetailsManager.findUsersInGroup(GROUP_PREFIX + groupName);
    }

    @GetMapping("/groups/{groupName}/authorities")
    List<GrantedAuthority> getGroupAuthorities(@PathVariable String groupName) {
        return userDetailsManager.findGroupAuthorities(GROUP_PREFIX +groupName);
    }

    @GetMapping("/groups/create/{groupName}:{role}")
    List<GrantedAuthority> createGroup(@PathVariable String groupName, @PathVariable String role) {
        userDetailsManager.createGroup(
                GROUP_PREFIX + groupName,
                AuthorityUtils.createAuthorityList(ROLE_PREFIX + role)
        );
        return userDetailsManager.findGroupAuthorities(GROUP_PREFIX + groupName);
    }

    @GetMapping("/groups/{groupName}/add/{userName}")
    List<String> addUserToGroup(
            @PathVariable String groupName,
            @PathVariable String userName
    ) {
        userDetailsManager.addUserToGroup(userName, GROUP_PREFIX + groupName);
        return getUsersInGroup(groupName);
    }

    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        return userDetailsManager.findGroupAuthorities(groupName);
    }

    public List<GrantedAuthority> findAllGroupAuthorities(String groupName) {
        return userDetailsManager.getJdbcTemplate().query(
                "select g.id, g.group_name, ga.authority " +
                        "from groups g, group_authorities ga " +
                        "where g.id = ga.group_id", new GrantedAuthorityRowMapper(ROLE_PREFIX));
    }

}
