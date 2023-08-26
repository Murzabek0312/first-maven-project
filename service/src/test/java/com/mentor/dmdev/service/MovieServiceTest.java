package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.MovieCreateEditDto;
import com.mentor.dmdev.dto.MovieReadDto;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.mappers.MovieMapper;
import com.mentor.dmdev.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository repository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieService movieService;


    @Test
    void shouldFindAll() {
        // Given:
        var pageable = mock(Pageable.class);

        var page = mock(Page.class);
        var resultPage = mock(Page.class);

        doReturn(page).when(repository).findAll(pageable);
        doReturn(resultPage).when(page).map(any(Function.class));

        // When:
        var result = movieService.findAll(pageable);

        // Then:
        assertEquals(resultPage, result);
    }

    @Test
    void shouldFindById() {
        // Given:
        var id = 123L;

        var movie = mock(Movie.class);
        var movieReadDto = mock(MovieReadDto.class);

        doReturn(Optional.of(movie)).when(repository).findById(id);
        doReturn(movieReadDto).when(movieMapper).map(movie);

        // When:
        Optional<MovieReadDto> result = movieService.findById(id);

        // Then:
        assertTrue(result.isPresent());
        assertEquals(movieReadDto, result.get());

    }

    @Test
    void shouldCreate() {
        // Given:
        var movieCreateEditDto = mock(MovieCreateEditDto.class);
        var movie = mock(Movie.class);
        var savedMovie = mock(Movie.class);
        var movieReadDto = mock(MovieReadDto.class);

        doReturn(movie).when(movieMapper).map(movieCreateEditDto);
        doReturn(savedMovie).when(repository).save(movie);
        doReturn(movieReadDto).when(movieMapper).map(savedMovie);

        // When:
        var result = movieService.create(movieCreateEditDto);

        // Then:
        assertEquals(movieReadDto, result);
    }

    @Test
    void shouldUpdate() {
        // Given:
        var id = 123L;
        var movieCreateEditDto = mock(MovieCreateEditDto.class);
        var movie = mock(Movie.class);
        var mappedMovie = mock(Movie.class);
        var updatedMovie = mock(Movie.class);
        var movieReadDto = mock(MovieReadDto.class);

        doReturn(Optional.of(movie)).when(repository).findById(id);
        doReturn(mappedMovie).when(movieMapper).map(movieCreateEditDto, movie);
        doReturn(updatedMovie).when(repository).saveAndFlush(mappedMovie);
        doReturn(movieReadDto).when(movieMapper).map(updatedMovie);

        // When:
        var result = movieService.update(id, movieCreateEditDto);

        // Then:
        assertTrue(result.isPresent());
        assertEquals(movieReadDto, result.get());
    }

    @Test
    void shouldDelete() {
        // Given:
        var id = 123L;
        var movie = mock(Movie.class);

        doReturn(Optional.of(movie)).when(repository).findById(id);

        // When:
        boolean result = movieService.delete(id);

        // Then:
        verify(repository).delete(movie);
        assertTrue(result);
    }
}