package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.MoviesActor;

import javax.persistence.EntityManager;

public class MovieActorRepository extends RepositoryBase<Long, MoviesActor> {

    public MovieActorRepository(EntityManager entityManager) {
        super(MoviesActor.class, entityManager);
    }
}
