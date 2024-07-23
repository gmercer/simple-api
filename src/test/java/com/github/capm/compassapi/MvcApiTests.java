package com.github.capm.compassapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
                        .get("/compass-api/greeting")
                        .with(user("admin").password("secret").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"content\":\"Hello, World!\"}"))
        ;
    }


    @Test
    void contextLoads() {
    }


}
