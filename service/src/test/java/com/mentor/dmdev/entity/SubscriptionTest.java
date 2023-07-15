package com.mentor.dmdev.entity;

import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SubscriptionTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldSaveSubscription() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var subscriptionId = session.save(subscription);
        session.flush();

        assertNotNull(subscriptionId);

        session.getTransaction().rollback();
    }

    @Test
    void shouldGetSubscription() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var subscriptionId = session.save(subscription);
        session.flush();
        session.clear();

        Subscription actualResult = session.get(Subscription.class, subscriptionId);

        assertEquals(SubscriptionTypes.PREMIUM, actualResult.getType());
        assertEquals(SubscriptionStatus.ACTIVE, actualResult.getStatus());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateSubscription() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var subscriptionId = session.save(subscription);
        session.flush();
        session.clear();

        Subscription currentSubscription = session.get(Subscription.class, subscriptionId);

        currentSubscription.setStatus(SubscriptionStatus.EXPIRED);

        session.update(currentSubscription);
        session.flush();
        session.clear();

        Subscription actualResult = session.get(Subscription.class, subscriptionId);

        assertEquals(SubscriptionTypes.PREMIUM, actualResult.getType());
        assertEquals(SubscriptionStatus.EXPIRED, actualResult.getStatus());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteSubscription() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var subscriptionId = session.save(subscription);
        session.flush();
        session.clear();

        Subscription currentSubscription = session.get(Subscription.class, subscriptionId);

        session.remove(currentSubscription);
        session.flush();
        session.clear();

        Subscription actualResult = session.get(Subscription.class, subscriptionId);

        assertNull(actualResult);

        session.getTransaction().rollback();
    }

}