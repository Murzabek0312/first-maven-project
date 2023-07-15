package com.mentor.dmdev.entity;

import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldSaveUser() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var user = User.builder()
                .username("username1")
                .firstName("firstName1")
                .secondName("secondName1")
                .password("password1")
                .email("email1")
                .build();

        Serializable actualResult = session.save(user);

        assertNotNull(actualResult);

        session.getTransaction().rollback();
    }

    @Test
    void shouldGetUser() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var user = User.builder()
                .username("username1")
                .firstName("firstName1")
                .secondName("secondName1")
                .password("password1")
                .email("email1")
                .build();

        Serializable userId = session.save(user);
        session.flush();
        session.clear();

        User actualResult = session.get(User.class, userId);

        assertEquals("username1", actualResult.getUsername());
        assertEquals("firstName1", actualResult.getFirstName());
        assertEquals("secondName1", actualResult.getSecondName());
        assertEquals("password1", actualResult.getPassword());
        assertEquals("email1", actualResult.getEmail());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateUser() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var user = User.builder()
                .username("username1")
                .firstName("firstName1")
                .secondName("secondName1")
                .password("password1")
                .email("email1")
                .build();

        Serializable userId = session.save(user);
        session.flush();
        session.clear();

        User currentUser = session.get(User.class, userId);

        currentUser.setEmail("newEmail");
        currentUser.setPassword("newPassword");
        session.update(currentUser);
        session.flush();
        session.clear();

        User actualResult = session.get(User.class, userId);

        assertEquals("username1", actualResult.getUsername());
        assertEquals("firstName1", actualResult.getFirstName());
        assertEquals("secondName1", actualResult.getSecondName());
        assertEquals("newPassword", actualResult.getPassword());
        assertEquals("newEmail", actualResult.getEmail());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteUser() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var user = User.builder()
                .username("username1")
                .firstName("firstName1")
                .secondName("secondName1")
                .password("password1")
                .email("email1")
                .build();

        Serializable userId = session.save(user);
        session.flush();
        session.clear();

        session.remove(user);
        session.flush();

        User actualResult = session.get(User.class, userId);

        assertNull(actualResult);

        session.getTransaction().rollback();
    }

}