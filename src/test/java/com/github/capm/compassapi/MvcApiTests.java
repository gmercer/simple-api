package com.github.capm.compassapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MvcApiTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGreeting() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/greetings/greeting")
                        .with(user("admin").password("secret").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"content\":\"Hello, World!\"}"))
        ;
    }

    @Test
    void testWhoAmI() throws Exception {
        String user = "nobody" ;
        String role = "DOESNOTEXIST";

        Pattern p = Pattern.compile(".*" + user + ".*" + role+ ".*",Pattern.DOTALL);
        mvc.perform(MockMvcRequestBuilders
                        .get("/greetings/whoami")
                        .with(user(user).password("unknown").roles(role))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesPattern(p)))
        ;
    }

    @Test
    void contextLoads() {
    }


}
