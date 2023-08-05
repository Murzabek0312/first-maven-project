package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubscriptionRepositoryTest extends BaseIT {

    private static SubscriptionRepository subscriptionRepository;
    private static Long subscriptionId;

    @BeforeEach
    void prepare() {
        subscriptionRepository = context.getBean(SubscriptionRepository.class);
        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        subscriptionId = (long) session.save(subscription);
    }

    @Test
    void shouldGetSubscription() {
        Optional<Subscription> actualResult = subscriptionRepository.findById(subscriptionId);

        assertTrue(actualResult.isPresent());
        assertEquals(SubscriptionTypes.PREMIUM, actualResult.get().getType());
        assertEquals(SubscriptionStatus.ACTIVE, actualResult.get().getStatus());
    }

    @Test
    void shouldUpdateSubscription() {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
        subscription.setStatus(SubscriptionStatus.EXPIRED);
        subscription.setType(SubscriptionTypes.STANDART);

        subscriptionRepository.update(subscription);
        session.flush();
        session.clear();

        Optional<Subscription> actualResult = subscriptionRepository.findById(subscriptionId);
        assertTrue(actualResult.isPresent());
        assertEquals(SubscriptionStatus.EXPIRED, actualResult.get().getStatus());
        assertEquals(SubscriptionTypes.STANDART, actualResult.get().getType());
    }

    @Test
    void shouldDeleteSubscription() {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
        subscriptionRepository.delete(subscription);
        Optional<Subscription> actualResult = subscriptionRepository.findById(subscriptionId);

        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllSubscription() {
        var subscription1 = Subscription.builder()
                .type(SubscriptionTypes.ULTRA)
                .status(SubscriptionStatus.EXPIRED)
                .build();

        var subscription2 = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);

        session.flush();
        session.clear();

        List<Subscription> actualResult = subscriptionRepository.findAll();
        assertEquals(3, actualResult.size());
    }
}