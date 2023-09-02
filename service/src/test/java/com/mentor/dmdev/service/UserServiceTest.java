package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.Role;
import com.mentor.dmdev.dto.UserCreateEditDto;
import com.mentor.dmdev.dto.UserReadDto;
import com.mentor.dmdev.dto.filters.UserFilter;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.entity.User;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.mappers.UserMapper;
import com.mentor.dmdev.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ImageService imageService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindAll() {
        // When:
        userService.findAll();

        // Then:
        verify(userRepository).findAll();
        verify(userMapper).map(anyList());
    }

    @Test
    void shouldFindAllByFilter() {
        // Given:
        var firstname = "firstname";
        var secondName = "secondName";

        var page = mock(Page.class);
        var pageable = mock(Pageable.class);
        var userFilter = UserFilter.builder()
                .firstName(firstname)
                .secondName(secondName)
                .build();
        doReturn(page).when(userRepository).findAll(any(Predicate.class), eq(pageable));

        // When:
        userService.findAll(userFilter, pageable);

        // Then:
        verify(userRepository).findAll(any(Predicate.class), eq(pageable));
    }

    @Test
    void shouldFindById() {
        // Given:
        var id = 123L;
        var user = mock(User.class);
        var userReadDto = mock(UserReadDto.class);

        doReturn(Optional.of(user)).when(userRepository).findById(id);
        doReturn(userReadDto).when(userMapper).map(user);

        // When:
        userService.findById(id);

        // Then:
        verify(userRepository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given:
        var id = 123L;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userRepository).findById(id);

        // When-Then:
        assertThrows(ResponseStatusException.class, () -> userService.findById(id));
    }

    @Test
    void shouldCreate() {
        // Given:
        var userCreateEditDto = mock(UserCreateEditDto.class);
        var user = mock(User.class);
        var savedUser = mock(User.class);
        var userReadDto = mock(UserReadDto.class);
        Subscription subscription = mock(Subscription.class);
        var multipartFile = mock(MultipartFile.class);
        var originalName = "originalName";

        doReturn(user).when(userMapper).map(userCreateEditDto, subscription);
        doReturn(savedUser).when(userRepository).save(user);
        doReturn(userReadDto).when(userMapper).map(savedUser);
        doReturn(subscription).when(subscriptionService).createDefaultSubscription();
        doReturn(multipartFile).when(userCreateEditDto).getImage();
        doReturn(originalName).when(multipartFile).getOriginalFilename();

        // When:
        userService.create(userCreateEditDto);

        // Then:
        verify(imageService).upload(eq(originalName), any());
        verify(userMapper).map(userCreateEditDto, subscription);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals(user, userArgumentCaptor.getValue());
    }

    @Test
    void shouldUpdateUser() throws IOException {
        // Given:
        var id = 123L;
        var subscriptionName = SubscriptionTypes.PREMIUM;

        var userCreateEditDto = mock(UserCreateEditDto.class);
        var user = mock(User.class);
        var mappedUser = mock(User.class);
        var updatedUser = mock(User.class);
        var subscription = mock(Subscription.class);
        var userReadDto = mock(UserReadDto.class);
        var multipartFile = mock(MultipartFile.class);

        doReturn(Optional.of(user)).when(userRepository).findById(id);
        doReturn(subscription).when(user).getSubscription();
        doReturn(mappedUser).when(userMapper).map(userCreateEditDto, user);
        doReturn(updatedUser).when(userRepository).saveAndFlush(mappedUser);
        doReturn(userReadDto).when(userMapper).map(updatedUser);
        doReturn(multipartFile).when(userCreateEditDto).getImage();

        // When:
        userService.update(id, userCreateEditDto, subscriptionName);

        // Then:
        verify(subscription).setType(SubscriptionTypes.PREMIUM);
        verify(user).setSubscription(subscription);
        verify(userMapper).map(updatedUser);
        verify(multipartFile).getOriginalFilename();
        verify(multipartFile).getInputStream();
    }

    @Test
    void shouldThrowExceptionNotFoundUser() {
        // Given:
        var id = 12L;
        var userCreateEditDto = mock(UserCreateEditDto.class);
        var subscriptionName = SubscriptionTypes.PREMIUM;

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userRepository).findById(id);

        // When-Then:
        assertThrows(ResponseStatusException.class,
                () -> userService.update(12L, userCreateEditDto, subscriptionName));
    }

    @Test
    void shouldDelete() {
        // Given:
        var id = 12L;
        var user = mock(User.class);

        doReturn(Optional.of(user)).when(userRepository).findById(id);

        // When:
        userService.delete(id);

        // Then:
        verify(userRepository).delete(user);
    }

    @Test
    void shouldLoadUserByUsername() {
        // Given:
        var username = "username";
        var password = "password";
        var role = Role.ADMIN;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        doReturn(Optional.of(user)).when(userRepository).findByUsername(username);

        // When:
        UserDetails actualResult = userService.loadUserByUsername(username);

        // Then:
        assertEquals(username, actualResult.getUsername());
        assertEquals(password, actualResult.getPassword());
        assertNotNull(actualResult.getAuthorities());
        actualResult.getAuthorities().stream().findFirst().ifPresent(actualRole -> assertEquals(role, actualRole));
    }

    @Test
    void shouldThrowExceptionIfNotFoundUserByUsername() {
        // Given:
        var username = "username";

        doReturn(Optional.empty()).when(userRepository).findByUsername(username);

        // When-Then:
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}