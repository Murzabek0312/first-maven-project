package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.FeedBack;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class FeedBackRepository extends RepositoryBase<Long, FeedBack> {

    public FeedBackRepository(EntityManager entityManager) {
        super(FeedBack.class, entityManager);
    }
}