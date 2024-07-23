package com.github.capm.controller;

import com.github.capm.entity.User;
import com.github.capm.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user")
    public User getUser(@RequestParam(value = "username") String username) {
        return userService.getUserByName(username);
    }

    @GetMapping("/fake")
    public User getFake(@RequestParam(value = "username", defaultValue = "FAKE") String username) {
        return new User(username, "password", false);
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
