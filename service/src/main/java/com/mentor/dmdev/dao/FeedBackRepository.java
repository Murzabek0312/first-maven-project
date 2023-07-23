package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.FeedBack;

import javax.persistence.EntityManager;

public class FeedBackRepository extends RepositoryBase<Long, FeedBack> {

    public FeedBackRepository(EntityManager entityManager) {
        super(FeedBack.class, entityManager);
    }
}