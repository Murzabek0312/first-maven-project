package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.Movie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MovieRepository extends RepositoryBase<Long, Movie> {

    public MovieRepository(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }
}