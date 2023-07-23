package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.Movie;

import javax.persistence.EntityManager;

public class MovieRepository extends RepositoryBase<Long, Movie> {

    public MovieRepository(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }
}