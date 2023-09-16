package com.mentor.dmdev.integration.controller;

import com.mentor.dmdev.BaseIT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.country;
import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.directorId;
import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.genre;
import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.name;
import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.releaseDate;
import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.subscriptionId;
import static com.mentor.dmdev.dto.MovieCreateEditDto.Fields.subscriptionTypes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class MovieControllerTest extends BaseIT {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie/movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().size(1));
    }

    @Test
    void findById() throws Exception {
        var id = 1L;
        mockMvc.perform(get("/movies/" + id))
                .andExpectAll(
                        status().isOk(),
                        model().attributeExists("subscriptions", "director", "subscription", "actors", "movie"),
                        view().name("movie/movie")
                );
    }

    @Test
    void update() throws Exception {
        var id = 1L;
        mockMvc.perform(post("/movies/" + id + "/update")
                        .param(name, "name")
                        .param(directorId, "2")
                        .param(releaseDate, "2023-08-26")
                        .param(country, "USA")
                        .param(genre, "ACTION")
                        .param(subscriptionId, "1")
                        .param(subscriptionTypes, "ULTRA"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/movies/{\\d+}")
                );
    }
}