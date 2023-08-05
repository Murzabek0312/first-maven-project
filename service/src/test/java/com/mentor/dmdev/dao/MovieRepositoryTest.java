package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.enums.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class MovieRepositoryTest extends BaseIT {

    private static final Long MOVIE_ID = 1L;

    private final MovieRepository movieRepository;

    @Test
    void shouldGetMovie() {
        Optional<Movie> actualResult = movieRepository.findById(MOVIE_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("The Hateful Eight", actualResult.get().getName());
        assertEquals("USA", actualResult.get().getCountry());
        assertEquals(Genre.ACTION, actualResult.get().getGenre());
    }

    @Test
    void shouldUpdateMovie() {
        Movie movie = movieRepository.findById(MOVIE_ID).get();
        movie.setName("The Pursuit of Happyness");
        movie.setGenre(Genre.DRAMA);
        movieRepository.update(movie);
        session.flush();
        session.clear();

        Optional<Movie> actualResult = movieRepository.findById(MOVIE_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("The Pursuit of Happyness", actualResult.get().getName());
        assertEquals(Genre.DRAMA, actualResult.get().getGenre());
    }

    @Test
    void shouldReturnAllMovie() {
        List<Movie> actualResult = movieRepository.findAll();
        assertEquals(4, actualResult.size());
    }
}