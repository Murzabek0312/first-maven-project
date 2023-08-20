package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.SubscriptionCreateEditDto;
import com.mentor.dmdev.dto.SubscriptionReadDto;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class SubscriptionMapperTest {

    private final SubscriptionMapper mapper = new SubscriptionMapperImpl();

    @Test
    void shouldMapFromSubscriptionToSubscriptionReadDto() {
        //Given:
        var id = 123L;
        var subscriptionType = SubscriptionTypes.PREMIUM;
        var status = SubscriptionStatus.ACTIVE;
        var subscription = mock(Subscription.class);

        doReturn(id).when(subscription).getId();
        doReturn(subscriptionType).when(subscription).getType();
        doReturn(status).when(subscription).getStatus();

        // When:
        SubscriptionReadDto actualResult = mapper.map(subscription);

        // Then:
        assertEquals(id, actualResult.getId());
        assertEquals(subscriptionType, actualResult.getType());
        assertEquals(status, actualResult.getStatus());
    }

    @Test
    void shouldMapFromSubscriptionCreateEditDtoToSubscription() {
        //Given:
        var subscriptionType = SubscriptionTypes.PREMIUM;
        var status = SubscriptionStatus.ACTIVE;

        var subscriptionCreateEditDto = mock(SubscriptionCreateEditDto.class);
        doReturn(subscriptionType).when(subscriptionCreateEditDto).getType();
        doReturn(status).when(subscriptionCreateEditDto).getStatus();

        // When:
        var actualResult = mapper.map(subscriptionCreateEditDto);

        // Then:
        assertEquals(subscriptionType, actualResult.getType());
        assertEquals(status, actualResult.getStatus());
    }

    @Test
    void shouldMapListSubscriptionsToListSubscriptionReadDto() {
        // Given:
        var id1 = 123L;
        var subscriptionType1 = SubscriptionTypes.PREMIUM;
        var status1 = SubscriptionStatus.ACTIVE;

        var id2 = 222L;
        var subscriptionType2 = SubscriptionTypes.ULTRA;
        var status2 = SubscriptionStatus.EXPIRED;

        var subscription1 = mock(Subscription.class);
        var subscription2 = mock(Subscription.class);

        doReturn(id1).when(subscription1).getId();
        doReturn(subscriptionType1).when(subscription1).getType();
        doReturn(status1).when(subscription1).getStatus();

        doReturn(id2).when(subscription2).getId();
        doReturn(subscriptionType2).when(subscription2).getType();
        doReturn(status2).when(subscription2).getStatus();

        // When:
        List<SubscriptionReadDto> actualResult = mapper.map(List.of(subscription1, subscription2));

        // Then:
        assertEquals(2, actualResult.size());
        SubscriptionReadDto actualElem1 = actualResult.get(0);
        assertEquals(id1, actualElem1.getId());
        assertEquals(subscriptionType1, actualElem1.getType());
        assertEquals(status1, actualElem1.getStatus());

        SubscriptionReadDto actualElem2 = actualResult.get(1);
        assertEquals(id2, actualElem2.getId());
        assertEquals(subscriptionType2, actualElem2.getType());
        assertEquals(status2, actualElem2.getStatus());
    }
}