package com.github.capm.controller;

import com.github.capm.compassapi.CompassApiApplication;
import com.github.capm.entity.Greeting;
import com.github.capm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CompassApiApplication.class)
class ControllerTest {
    @Autowired
    private TestRestTemplate template;

    @Test
    public void getGreeting() throws Exception {
        ResponseEntity<Greeting> response =
                template
                        .withBasicAuth("admin", "secret")
                        .getForEntity("/compass-api/greeting", Greeting.class)
                ;
        Greeting greeting = response.getBody();
        assertNotNull(greeting);
        assertThat(greeting.getContent()).isEqualTo("Hello, World!");
    }

    @Test
    public void getUser() throws Exception {
        Map<String, String> args = new HashMap<>();
        args.put("dog", "admin");

        ResponseEntity<User> response =
                template
                        .withBasicAuth("admin", "secret")
                        .getForEntity("/users/user?username={dog}", User.class, args)
                ;
        User user = response.getBody();
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getEnabled()).isEqualTo(true);
    }

    @Test
    public void getFake() throws Exception {

        ResponseEntity<User> response =
                template
                        .withBasicAuth("admin", "secret")
                        .getForEntity("/users/fake", User.class)
                ;
        User user = response.getBody();
        assertNotNull(user);
        assertThat(user.getUsername()).isEqualTo("FAKE");
        assertThat(user.getEnabled()).isEqualTo(false);
    }

    @Test
    public void getAll() throws Exception {

        ResponseEntity<User[]> response =
                template
                        .withBasicAuth("admin", "secret")
                        .getForEntity("/users/all", User[].class)
                ;
        User[] users = response.getBody();
        assertNotNull(users);
        assertThat(users.length).isNotEqualTo(0);

        assertThat(users).extracting(User::getUsername).contains("admin");
    }

}

