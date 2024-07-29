package com.github.capm.controller;

import com.github.capm.compassapi.CompassApiApplication;
import com.github.capm.entity.Greeting;
import com.github.capm.entity.User;
import com.github.capm.entity.GrantedAuthority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CompassApiApplication.class)
class ControllerTest {

    public static final String PASSWORD = "secret";
    public static final String USERNAME = "admin";
    public static final String ADMINS = "ADMINS";
    public static final String USERS = "USERS";
    public static final String GROUP_ADMINS = "GROUP_ADMINS";
    public static final String GROUP_USERS = "GROUP_USERS";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getGreeting() throws Exception {
        ResponseEntity<Greeting> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/compass-api/greeting", Greeting.class)
                ;
        Greeting greeting = response.getBody();
        assertNotNull(greeting);
        assertThat(greeting.getContent()).isEqualTo("Hello, World!");
    }

    @Test
    public void getUser() throws Exception {

        ResponseEntity<User> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/users/" + USERNAME, User.class)
                ;
        User user = response.getBody();
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.isEnabled()).isEqualTo(true);
    }

    @Test
    public void getFake() throws Exception {

        ResponseEntity<User> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/fake", User.class)
                ;
        User user = response.getBody();
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("FAKE");
        assertThat(user.isEnabled()).isEqualTo(false);
    }

    @Test
    public void getAllUsers() throws Exception {

        ResponseEntity<User[]> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/users", User[].class)
                ;
        User[] users = response.getBody();
        assertNotNull(users);
        assertThat(users).isNotEmpty();

        assertThat(users).extracting(User::getUsername).contains(USERNAME);
    }

    @Test
    public void getAllGroups() throws Exception {

        ResponseEntity<String[]> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/groups", String[].class)
                ;
        String[] groups = response.getBody();
        assertNotNull(groups);
        assertThat(groups).isNotEmpty();

        assertThat(groups).contains(GROUP_ADMINS);
        assertThat(groups).contains(GROUP_USERS);
    }

    @Test
    public void getAllUsersInGroup() throws Exception {

        ResponseEntity<String[]> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/groups/" + ADMINS + "/users", String[].class)
                ;
        String[] users = response.getBody();
        assertNotNull(users);
        assertThat(users).isNotEmpty();
        assertThat(users).contains(USERNAME);
    }

    @Test
    public void getAllAuthoritiesInGroup() throws Exception {

        ResponseEntity<GrantedAuthority[]> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/groups/" + ADMINS + "/authorities", GrantedAuthority[].class)
                ;
        GrantedAuthority[] auths = response.getBody();
        assertNotNull(auths);
        assertThat(auths).isNotEmpty();
        assertThat(auths).extracting(GrantedAuthority::getAuthority).contains(ROLE_ADMIN);
    }

    @Test
    public void createGroup() throws Exception {
        ResponseEntity<GrantedAuthority[]> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/groups/create/" + "AVENGERS:AVENGER", GrantedAuthority[].class)
                ;
        GrantedAuthority[] auths = response.getBody();
        assertNotNull(auths);
        assertThat(auths).isNotEmpty();
        assertThat(auths).extracting(GrantedAuthority::getAuthority).contains("ROLE_AVENGER");

    }

    @Test
    public void createUser() throws Exception {
        ResponseEntity<User> response =
                template
                        .withBasicAuth(USERNAME, PASSWORD)
                        .getForEntity("/auth/users/create/" + "capman:secret", User.class)
                ;
        User user = response.getBody();
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("capman");
        assertThat(user.getPassword()).isNotEqualTo("secret");
    }

}

