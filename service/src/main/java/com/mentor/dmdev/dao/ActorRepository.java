package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.Actor;

import javax.persistence.EntityManager;

public class ActorRepository extends RepositoryBase<Long, Actor> {

    public ActorRepository(EntityManager entityManager) {
        super(Actor.class, entityManager);
    }
}