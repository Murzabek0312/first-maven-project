package com.mentor.dmdev.integration.rest;

import com.mentor.dmdev.BaseIT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class MovieRestControllerIT extends BaseIT {

    private final static Long MOVIE_ID = 1L;

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.metadata.page", is(0)))
                .andExpect(jsonPath("$.metadata.size", is(5)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/api/v1/movies/" + MOVIE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.directorId", is(3)))
                .andExpect(jsonPath("$.subscriptionId", is(1)))
                .andExpect(jsonPath("$.name", is("The Hateful Eight")))
                .andExpect(jsonPath("$.genre", is("ACTION")))
                .andExpect(jsonPath("$.actorIds", hasSize(2)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedFilm\",\"directorId\":\"3\",\"releaseDate\":\"2023-08-26\",\"country\":\"Canada\",\"genre\":\"ACTION\",\"subscriptionId\":\"1\"}"))
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/api/v1/movies/" + MOVIE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedFilm\",\"directorId\":\"3\",\"releaseDate\":\"2023-08-26\",\"country\":\"Canada\",\"genre\":\"ACTION\",\"subscriptionId\":\"1\"}"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.name", is("updatedFilm")),
                        jsonPath("$.country", is("Canada")),
                        jsonPath("$.directorId", is(3)),
                        jsonPath("$.releaseDate", is("2023-08-26"))
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movies/" + MOVIE_ID))
                .andExpect(status().isNoContent());
    }
}