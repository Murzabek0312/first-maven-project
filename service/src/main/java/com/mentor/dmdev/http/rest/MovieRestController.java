package com.mentor.dmdev.http.rest;

import com.mentor.dmdev.dto.MovieCreateEditDto;
import com.mentor.dmdev.dto.MovieReadDto;
import com.mentor.dmdev.dto.paging.PageResponse;
import com.mentor.dmdev.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieService movieService;

    @GetMapping
    public PageResponse<MovieReadDto> findAll(Pageable pageable) {
        return PageResponse.of(movieService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public MovieReadDto findById(@PathVariable("id") Long id) {
        return movieService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieReadDto create(@RequestBody MovieCreateEditDto movieCreateEditDto) {
        return movieService.create(movieCreateEditDto);
    }

    @PutMapping("/{id}")
    public MovieReadDto update(@PathVariable("id") Long id,
                               @Validated @RequestBody MovieCreateEditDto movieCreateEditDto) {
        return movieService.update(id, movieCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!movieService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}