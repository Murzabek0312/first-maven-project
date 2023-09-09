package com.mentor.dmdev.repository;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class UserRepositoryTest extends BaseIT {

    private static final Long USER_ID = 1L;

    private final UserRepository userRepository;

    @Test
    void shouldReturnUser() {
        Optional<User> actualResult = userRepository.findById(USER_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("username", actualResult.get().getUsername());
        assertEquals("firstName", actualResult.get().getFirstName());
        assertEquals("secondName", actualResult.get().getSecondName());
        assertEquals("{noop}password", actualResult.get().getPassword());
        assertEquals("email", actualResult.get().getEmail());
    }

    @Test
    void shouldUpdateUser() {
        User user = userRepository.findById(USER_ID).get();
        user.setFirstName("new firstname");
        user.setSecondName("new secondname");

        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        Optional<User> actualResult = userRepository.findById(USER_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("new firstname", actualResult.get().getFirstName());
        assertEquals("new secondname", actualResult.get().getSecondName());
        assertEquals("{noop}password", actualResult.get().getPassword());
        assertEquals("email", actualResult.get().getEmail());
        assertEquals("username", actualResult.get().getUsername());
    }

    @Test
    void shouldDeleteUser() {
        User user = userRepository.findById(USER_ID).get();
        userRepository.delete(user);
        Optional<User> actualResult = userRepository.findById(USER_ID);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllUser() {
        List<User> actaulResult = userRepository.findAll();
        assertEquals(3, actaulResult.size());
    }
}