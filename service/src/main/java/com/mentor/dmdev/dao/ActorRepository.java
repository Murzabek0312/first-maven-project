package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.Actor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ActorRepository extends RepositoryBase<Long, Actor> {

    public ActorRepository(EntityManager entityManager) {
        super(Actor.class, entityManager);
    }
}