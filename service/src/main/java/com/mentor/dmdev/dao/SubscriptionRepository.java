package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.Subscription;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SubscriptionRepository extends RepositoryBase<Long, Subscription> {

    public SubscriptionRepository(EntityManager entityManager) {
        super(Subscription.class, entityManager);
    }
}