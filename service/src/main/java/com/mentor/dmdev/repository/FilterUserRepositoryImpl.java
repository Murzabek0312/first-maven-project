package com.mentor.dmdev.repository;

import com.mentor.dmdev.dto.filters.UserFilter;
import com.mentor.dmdev.entity.User;
import com.mentor.dmdev.repository.predicates.QPredicate;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mentor.dmdev.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(@NonNull UserFilter filter) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::containsIgnoreCase)
                .add(filter.getSecondName(), user.secondName::containsIgnoreCase)
                .buildAnd();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}