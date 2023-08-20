package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.SubscriptionCreateEditDto;
import com.mentor.dmdev.dto.SubscriptionReadDto;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.mappers.SubscriptionMapper;
import com.mentor.dmdev.repository.SubscriptionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @NonNull
    public List<SubscriptionReadDto> findAll() {
        return subscriptionMapper.map(subscriptionRepository.findAll());
    }

    @Transactional
    @NonNull
    public SubscriptionReadDto create(@NonNull SubscriptionCreateEditDto subscriptionCreateEditDto) {
        return Optional.of(subscriptionCreateEditDto)
                .map(subscriptionMapper::map)
                .map(subscriptionRepository::save)
                .map(subscriptionMapper::map)
                .orElseThrow();
    }

    @Transactional
    @NonNull
    public Subscription createDefaultSubscription() {
        Subscription subscription = new Subscription();
        subscription.setType(SubscriptionTypes.STANDART);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        return subscriptionRepository.save(subscription);
    }
}