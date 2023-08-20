package com.mentor.dmdev.integration.controller;

import com.mentor.dmdev.BaseIT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.mentor.dmdev.dto.UserCreateEditDto.Fields.email;
import static com.mentor.dmdev.dto.UserCreateEditDto.Fields.firstName;
import static com.mentor.dmdev.dto.UserCreateEditDto.Fields.secondName;
import static com.mentor.dmdev.dto.UserCreateEditDto.Fields.username;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends BaseIT {

    private static final Long USER_ID = 1L;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/users/registration")
                        .param("subscriptionName", "PREMIUM")
                        .param(username, "username")
                        .param(firstName, "firstName")
                        .param(secondName, "secondName")
                        .param(email, "email123")
                )
                .andExpect(model().attributeExists("user"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/registration"));
    }

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("userFilter"));
    }

    @Test
    void findById() throws Exception {
        var id = 1L;
        mockMvc.perform(get("/users/" + id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("subscriptions"))
                .andExpect(view().name("user/user"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "username")
                        .param(firstName, "firstName")
                        .param(secondName, "secondName")
                        .param(email, "email123")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/" + USER_ID + "/update")
                .param("subscriptionName", "PREMIUM")
                .param(username, "username")
                .param(firstName, "firstName")
                .param(secondName, "secondName")
                .param(email, "email123")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/users/{\\d+}")
        );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/" + USER_ID + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users")
                );
    }

    @Test
    void throwNotFound() throws Exception {
        var id = 34L;
        mockMvc.perform(post("/users/" + id + "/delete"))
                .andExpect(status().is4xxClientError());
    }
}