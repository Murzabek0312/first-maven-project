package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.MoviesActor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MovieActorRepository extends RepositoryBase<Long, MoviesActor> {

    public MovieActorRepository(EntityManager entityManager) {
        super(MoviesActor.class, entityManager);
    }
}
