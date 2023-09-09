package com.mentor.dmdev.integration.controller;

import com.mentor.dmdev.BaseIT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.mentor.dmdev.dto.LoginDto.Fields.password;
import static com.mentor.dmdev.dto.LoginDto.Fields.username;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class LoginControllerTest extends BaseIT {

    private final MockMvc mockMvc;

    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/login"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(post("/login")
                        .param(username, "username")
                        .param(password, "password")
                )
                .andExpect(redirectedUrl("/users"));
    }
}