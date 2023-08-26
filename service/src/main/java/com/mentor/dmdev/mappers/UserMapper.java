package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.UserCreateEditDto;
import com.mentor.dmdev.dto.UserReadDto;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubscriptionMapper.class, FeedbackMapper.class})
public interface UserMapper {

    UserReadDto map(User source);

    List<UserReadDto> map(List<User> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "subscription", source = "subscription")
    @Mapping(target = "image", expression = "java(source.getImage().getOriginalFilename())")
    User map(UserCreateEditDto source, Subscription subscription);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "source.username")
    @Mapping(target = "firstName", source = "source.firstName")
    @Mapping(target = "secondName", source = "source.secondName")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "email", source = "source.email")
    @Mapping(target = "image", expression = "java(java.util.Objects.requireNonNull(source.getImage().getOriginalFilename()).isEmpty() ? user.getImage() : source.getImage().getOriginalFilename())")
    User map(UserCreateEditDto source, User user);
}