package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.SubscriptionCreateEditDto;
import com.mentor.dmdev.dto.SubscriptionReadDto;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.mappers.SubscriptionMapper;
import com.mentor.dmdev.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Captor
    private ArgumentCaptor<SubscriptionCreateEditDto> captor;

    @InjectMocks
    private SubscriptionService subscriptionService;


    @Test
    void shouldFindAll() {
        // When:
        subscriptionService.findAll();

        // THen:
        verify(subscriptionRepository).findAll();
        verify(subscriptionMapper).map(anyList());
    }

    @Test
    void shouldCreate() {
        // Given:
        var subscriptionCreateEditDto = mock(SubscriptionCreateEditDto.class);
        var subscription = mock(Subscription.class);
        var savedSubscription = mock(Subscription.class);
        var subscriptionReadDto = mock(SubscriptionReadDto.class);

        doReturn(subscription).when(subscriptionMapper).map(subscriptionCreateEditDto);
        doReturn(savedSubscription).when(subscriptionRepository).save(subscription);
        doReturn(subscriptionReadDto).when(subscriptionMapper).map(savedSubscription);

        // When:
        subscriptionService.create(subscriptionCreateEditDto);

        // Then:
        verify(subscriptionMapper).map(captor.capture());
        assertEquals(subscriptionCreateEditDto, captor.getValue());
        verify(subscriptionRepository).save(subscription);
        verify(subscriptionMapper).map(savedSubscription);
    }
}