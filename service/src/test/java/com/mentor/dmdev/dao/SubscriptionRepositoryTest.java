package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class SubscriptionRepositoryTest extends BaseIT {

    private static final Long SUBSCRIPTION_ID = 2L;

    private final SubscriptionRepository subscriptionRepository;

    @Test
    void shouldGetSubscription() {
        Optional<Subscription> actualResult = subscriptionRepository.findById(SUBSCRIPTION_ID);

        assertTrue(actualResult.isPresent());
        assertEquals(SubscriptionTypes.STANDART, actualResult.get().getType());
        assertEquals(SubscriptionStatus.EXPIRED, actualResult.get().getStatus());
    }

    @Test
    void shouldUpdateSubscription() {
        Subscription subscription = subscriptionRepository.findById(SUBSCRIPTION_ID).get();
        subscription.setStatus(SubscriptionStatus.EXPIRED);
        subscription.setType(SubscriptionTypes.STANDART);

        subscriptionRepository.update(subscription);
        session.flush();
        session.clear();

        Optional<Subscription> actualResult = subscriptionRepository.findById(SUBSCRIPTION_ID);
        assertTrue(actualResult.isPresent());
        assertEquals(SubscriptionStatus.EXPIRED, actualResult.get().getStatus());
        assertEquals(SubscriptionTypes.STANDART, actualResult.get().getType());
    }

    @Test
    void shouldDeleteSubscription() {
        Subscription subscription = subscriptionRepository.findById(SUBSCRIPTION_ID).get();
        subscriptionRepository.delete(subscription);
        Optional<Subscription> actualResult = subscriptionRepository.findById(SUBSCRIPTION_ID);

        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllSubscription() {

        List<Subscription> actualResult = subscriptionRepository.findAll();
        assertEquals(2, actualResult.size());
    }
}