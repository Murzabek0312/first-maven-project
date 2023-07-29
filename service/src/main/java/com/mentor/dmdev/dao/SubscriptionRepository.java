package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.Subscription;

import javax.persistence.EntityManager;

public class SubscriptionRepository extends RepositoryBase<Long, Subscription> {

    public SubscriptionRepository(EntityManager entityManager) {
        super(Subscription.class, entityManager);
    }
}