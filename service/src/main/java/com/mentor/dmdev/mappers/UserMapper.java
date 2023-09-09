package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.UserCreateEditDto;
import com.mentor.dmdev.dto.UserReadDto;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.entity.User;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {SubscriptionMapper.class, FeedbackMapper.class})
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract UserReadDto map(User source);

    public abstract List<UserReadDto> map(List<User> source);

    @Named("encode")
    @NonNull
    public String encodePassword(String rawPassword) {
        return Optional.of(rawPassword)
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .orElse("{noop}123");
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "role", source = "source.role")
    @Mapping(target = "password", source = "source.rawPassword", qualifiedByName = "encode")
    @Mapping(target = "subscription", source = "subscription")
    @Mapping(target = "image", expression = "java(source.getImage().getOriginalFilename())")
    public abstract User map(UserCreateEditDto source, Subscription subscription);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "role", source = "source.role")
    @Mapping(target = "username", source = "source.username")
    @Mapping(target = "firstName", source = "source.firstName")
    @Mapping(target = "secondName", source = "source.secondName")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "email", source = "source.email")
    @Mapping(target = "image", expression = "java(java.util.Objects.requireNonNull(source.getImage().getOriginalFilename()).isEmpty() ? user.getImage() : source.getImage().getOriginalFilename())")
    public abstract User map(UserCreateEditDto source, User user);
}