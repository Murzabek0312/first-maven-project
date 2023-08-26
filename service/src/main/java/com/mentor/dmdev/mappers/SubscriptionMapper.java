package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.SubscriptionCreateEditDto;
import com.mentor.dmdev.dto.SubscriptionReadDto;
import com.mentor.dmdev.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionReadDto map(Subscription source);

    @Mapping(target = "id", ignore = true)
    Subscription map(SubscriptionCreateEditDto source);

    List<SubscriptionReadDto> map(List<Subscription> subscriptions);
}