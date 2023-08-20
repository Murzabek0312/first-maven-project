package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.UserCreateEditDto;
import com.mentor.dmdev.dto.UserReadDto;
import com.mentor.dmdev.dto.filters.UserFilter;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.mappers.UserMapper;
import com.mentor.dmdev.repository.UserRepository;
import com.mentor.dmdev.repository.predicates.QPredicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.mentor.dmdev.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SubscriptionService subscriptionService;

    @NonNull
    public Page<UserReadDto> findAll(@NonNull UserFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::containsIgnoreCase)
                .add(filter.getSecondName(), user.secondName::containsIgnoreCase)
                .buildAnd();

        return userRepository.findAll(predicate, pageable).map(userMapper::map);
    }

    @NonNull
    public List<UserReadDto> findAll() {
        return userMapper.map(userRepository.findAll());
    }

    @NonNull
    public Optional<UserReadDto> findById(@NonNull Long id) {
        return userRepository.findById(id)
                .map(userMapper::map);
    }

    @Transactional
    @NonNull
    public UserReadDto create(@NonNull UserCreateEditDto userCreateEditDto) {
        var subscription = subscriptionService.createDefaultSubscription();
        return Optional.of(userCreateEditDto)
                .map(dto -> userMapper.map(dto, subscription))
                .map(userRepository::save)
                .map(userMapper::map)
                .orElseThrow();
    }

    @Transactional
    @NonNull
    public UserReadDto update(@NonNull Long id,
                              @NonNull UserCreateEditDto userCreateEditDto,
                              @NonNull SubscriptionTypes subscriptionTypes) {

        return userRepository.findById(id)
                .map(entity -> {
                    Subscription subscription = entity.getSubscription();
                    subscription.setType(subscriptionTypes);
                    entity.setSubscription(subscription);
                    return userMapper.map(userCreateEditDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Transactional
    public boolean delete(@NonNull Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                }).orElse(false);
    }
}