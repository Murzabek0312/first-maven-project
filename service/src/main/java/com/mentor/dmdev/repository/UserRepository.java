package com.mentor.dmdev.repository;

import com.mentor.dmdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        QuerydslPredicateExecutor<User> {
}