package com.mentor.dmdev.mappers;

import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.User;
import com.mentor.dmdev.enums.Rating;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class FeedbackMapperTest {

    private final FeedbackMapper mapper = new FeedbackMapperImpl();

    @Test
    void shouldMapFromFeedbackToFeedBackReadDto() {
        // Given:
        var id = 123L;
        var comment = "comment";
        var rating = Rating.EXCELLENT;

        var feedBack = mock(FeedBack.class);
        var movie = mock(Movie.class);
        var user = mock(User.class);

        doReturn(id).when(feedBack).getId();
        doReturn(movie).when(feedBack).getMovie();
        doReturn(user).when(feedBack).getUser();
        doReturn(comment).when(feedBack).getComment();
        doReturn(rating).when(feedBack).getRating();

        // When:
        var actualResult = mapper.map(feedBack);

        // Then:
        assertEquals(id, actualResult.getId());
        assertEquals(comment, actualResult.getComment());
        assertEquals(rating, actualResult.getRating());
        assertEquals(movie, actualResult.getMovie());
        assertEquals(user, actualResult.getUser());
    }

    @Test
    void shouldMapFromListFeedbackToListFeedBackReadDto() {
        // Given:
        var feedBack = mock(FeedBack.class);
        var feedBack2 = mock(FeedBack.class);

        // When:
        var actualResult = mapper.map(List.of(feedBack, feedBack2));

        // Then:
        assertEquals(2, actualResult.size());
    }
}