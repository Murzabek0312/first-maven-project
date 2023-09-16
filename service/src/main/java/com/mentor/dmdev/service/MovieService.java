package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.MovieCreateEditDto;
import com.mentor.dmdev.dto.MovieReadDto;
import com.mentor.dmdev.mappers.MovieMapper;
import com.mentor.dmdev.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository repository;
    private final MovieMapper movieMapper;
    private final SubscriptionService subscriptionService;

    public Page<MovieReadDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(movieMapper::map);
    }

    public Optional<MovieReadDto> findById(Long id) {
        return repository.findById(id)
                .map(movieMapper::map);
    }

    @Transactional
    public MovieReadDto create(MovieCreateEditDto movieCreateEditDto) {
        return Optional.of(movieCreateEditDto)
                .map(movieMapper::map)
                .map(repository::save)
                .map(movieMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<MovieReadDto> update(Long id, MovieCreateEditDto movieCreateEditDto) {
        subscriptionService.updateType(movieCreateEditDto.getSubscriptionId(),
                movieCreateEditDto.getSubscriptionTypes());
        return repository.findById(id)
                .map(movie -> movieMapper.map(movieCreateEditDto, movie))
                .map(repository::saveAndFlush)
                .map(movieMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }
}