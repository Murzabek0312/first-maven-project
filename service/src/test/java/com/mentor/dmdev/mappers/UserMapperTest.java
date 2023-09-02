package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.FeedBackReadDto;
import com.mentor.dmdev.dto.SubscriptionReadDto;
import com.mentor.dmdev.dto.UserCreateEditDto;
import com.mentor.dmdev.dto.UserReadDto;
import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private FeedbackMapper feedbackMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapperImpl mapper;

    @Test
    void shouldMapFromUserToUserReadDto() {
        //Given:
        var id = 123L;
        var username = "username";
        var firstname = "firstname";
        var secondName = "secondName";
        var password = "password";
        var email = "email";
        Subscription subscription = mock(Subscription.class);
        SubscriptionReadDto subscriptionReadDto = mock(SubscriptionReadDto.class);
        FeedBack feedBack1 = mock(FeedBack.class);
        FeedBack feedBack2 = mock(FeedBack.class);
        FeedBackReadDto feedBackReadDto1 = mock(FeedBackReadDto.class);
        FeedBackReadDto feedBackReadDto2 = mock(FeedBackReadDto.class);
        List<FeedBack> feedBacks = List.of(feedBack1, feedBack2);
        List<FeedBackReadDto> feedBackReadDtoList = List.of(feedBackReadDto1, feedBackReadDto2);

        User user = mock(User.class);

        doReturn(id).when(user).getId();
        doReturn(username).when(user).getUsername();
        doReturn(firstname).when(user).getFirstName();
        doReturn(secondName).when(user).getSecondName();
        doReturn(password).when(user).getPassword();
        doReturn(email).when(user).getEmail();
        doReturn(subscription).when(user).getSubscription();
        doReturn(feedBacks).when(user).getFeedbacks();
        doReturn(subscriptionReadDto).when(subscriptionMapper).map(subscription);
        doReturn(feedBackReadDtoList).when(feedbackMapper).map(feedBacks);

        //When:
        UserReadDto actualResult = mapper.map(user);

        //Then:
        assertEquals(id, actualResult.getId());
        assertEquals(username, actualResult.getUsername());
        assertEquals(firstname, actualResult.getFirstName());
        assertEquals(secondName, actualResult.getSecondName());
        assertEquals(password, actualResult.getPassword());
        assertEquals(email, actualResult.getEmail());
        assertEquals(feedBackReadDtoList, actualResult.getFeedbacks());
        assertEquals(subscriptionReadDto, actualResult.getSubscription());
    }

    @Test
    void shouldMapFromListUserToListUserReadDto() {
        //Given:
        var user1 = mock(User.class);
        var user2 = mock(User.class);

        //When:
        var actualResult = mapper.map(List.of(user1, user2));

        // Then:
        assertEquals(2, actualResult.size());
    }

    @Test
    void shouldMapFromUserToUserCreateEditDto() {
        // Given:
        var username = "username";
        var firstname = "firstname";
        var secondName = "secondName";
        var email = "email";
        var imageName = "imageName";
        var rawPassword = "rawPassword";
        var encodePassword = "encodePassword";
        var subscription = mock(Subscription.class);
        var image = mock(MultipartFile.class);

        var userCreateEditDto = mock(UserCreateEditDto.class);

        doReturn(username).when(userCreateEditDto).getUsername();
        doReturn(firstname).when(userCreateEditDto).getFirstName();
        doReturn(secondName).when(userCreateEditDto).getSecondName();
        doReturn(email).when(userCreateEditDto).getEmail();
        doReturn(image).when(userCreateEditDto).getImage();
        doReturn(imageName).when(image).getOriginalFilename();
        doReturn(rawPassword).when(userCreateEditDto).getRawPassword();
        doReturn(encodePassword).when(passwordEncoder).encode(rawPassword);

        //When:
        var actualResult = mapper.map(userCreateEditDto, subscription);

        //Then:
        assertEquals(username, actualResult.getUsername());
        assertEquals(firstname, actualResult.getFirstName());
        assertEquals(secondName, actualResult.getSecondName());
        assertEquals(email, actualResult.getEmail());
        assertEquals(imageName, actualResult.getImage());
        assertEquals(encodePassword, actualResult.getPassword());
    }

    @Test
    void shouldMapUserByUserCreateEditDtoAndUser() {
        // Given:
        var id = 123L;
        var username = "username";
        var firstname = "firstname";
        var secondName = "secondName";
        var email = "email";
        var originalFileName = "originalFileName";

        var subscription = mock(Subscription.class);
        var user = mock(User.class);
        var multipartFile = mock(MultipartFile.class);

        var userCreateEditDto = mock(UserCreateEditDto.class);

        doReturn(username).when(userCreateEditDto).getUsername();
        doReturn(firstname).when(userCreateEditDto).getFirstName();
        doReturn(secondName).when(userCreateEditDto).getSecondName();
        doReturn(email).when(userCreateEditDto).getEmail();
        doReturn(subscription).when(user).getSubscription();
        doReturn(id).when(user).getId();
        doReturn(multipartFile).when(userCreateEditDto).getImage();
        doReturn(originalFileName).when(multipartFile).getOriginalFilename();

        //When:
        var actualResult = mapper.map(userCreateEditDto, user);

        //Then:
        assertEquals(username, actualResult.getUsername());
        assertEquals(firstname, actualResult.getFirstName());
        assertEquals(secondName, actualResult.getSecondName());
        assertEquals(email, actualResult.getEmail());
        assertEquals(subscription, actualResult.getSubscription());
        assertEquals(id, actualResult.getId());
        assertEquals(originalFileName, actualResult.getImage());
    }
}