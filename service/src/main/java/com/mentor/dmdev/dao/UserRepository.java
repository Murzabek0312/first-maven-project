package com.mentor.dmdev.dao;

import com.mentor.dmdev.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}