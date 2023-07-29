package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseTest;
import com.mentor.dmdev.entity.User;
import com.mentor.dmdev.util.HibernateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryTest extends BaseTest {

    private static UserRepository userRepository;
    private static Long userId;

    @BeforeEach
    void prepare() {
        session = HibernateUtil.getSessionByProxy(sessionFactory);
        userRepository = new UserRepository(session);
        session.beginTransaction();

        var user = User.builder()
                .username("username1")
                .firstName("firstName1")
                .secondName("secondName1")
                .password("password1")
                .email("email1")
                .build();

        userId = (long) session.save(user);
    }

    @Test
    void shouldReturnUser() {
        Optional<User> actualResult = userRepository.findById(userId);
        assertTrue(actualResult.isPresent());
        assertEquals("username1", actualResult.get().getUsername());
        assertEquals("firstName1", actualResult.get().getFirstName());
        assertEquals("secondName1", actualResult.get().getSecondName());
        assertEquals("password1", actualResult.get().getPassword());
        assertEquals("email1", actualResult.get().getEmail());
    }

    @Test
    void shouldUpdateUser() {
        User user = userRepository.findById(userId).get();
        user.setFirstName("new firstname");
        user.setSecondName("new secondname");

        userRepository.update(user);
        session.flush();
        session.clear();

        Optional<User> actualResult = userRepository.findById(userId);
        assertTrue(actualResult.isPresent());
        assertEquals("new firstname", actualResult.get().getFirstName());
        assertEquals("new secondname", actualResult.get().getSecondName());
        assertEquals("password1", actualResult.get().getPassword());
        assertEquals("email1", actualResult.get().getEmail());
        assertEquals("username1", actualResult.get().getUsername());
    }

    @Test
    void shouldDeleteUser() {
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
        Optional<User> actualResult = userRepository.findById(userId);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllUser() {
        var user2 = User.builder()
                .username("username2")
                .firstName("firstName2")
                .secondName("secondName2")
                .password("password2")
                .email("email2")
                .build();
        var user3 = User.builder()
                .username("username3")
                .firstName("firstName3")
                .secondName("secondName3")
                .password("password3")
                .email("email3")
                .build();

        userRepository.save(user2);
        userRepository.save(user3);

        session.flush();
        session.clear();

        List<User> actaulResult = userRepository.findAll();
        assertEquals(3, actaulResult.size());
    }
}