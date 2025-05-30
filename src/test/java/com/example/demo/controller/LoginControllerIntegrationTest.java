package com.example.demo.controller;

import com.example.demo.Utils.TestBase;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BCryptUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginControllerIntegrationTest extends TestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("Ichwurste123");
        userRepository.save(user);
    }

    @Test
    void login_validCredentials_returnsToken() throws Exception {
        String json = "{\"username\": \"testuser\", \"password\": \"secret\"}";

        ResultActions result = mockMvc.perform(post("/login")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(json));

        result.andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.isEmptyString())));
    }

    @Test
    void login_invalidCredentials_returnsUnauthorized() throws Exception {
        String json = "{\"username\": \"testuser\", \"password\": \"wrong\"}";

        mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
            .andExpect(status().isUnauthorized());
    }
}
