package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.enums.Rating;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class FeedBackRepositoryTest extends BaseIT {

    private static final Long FEEDBACK_ID = 1L;

    private final FeedBackRepository feedBackRepository;

    @Test
    void shouldGetFeedback() {
        Optional<FeedBack> actualResult = feedBackRepository.findById(FEEDBACK_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("comment", actualResult.get().getComment());
        assertEquals(Rating.EXCELLENT, actualResult.get().getRating());
    }

    @Test
    void shouldUpdateFeedback() {
        FeedBack feedBack = feedBackRepository.findById(FEEDBACK_ID).get();
        feedBack.setComment("new Comment");
        feedBack.setRating(Rating.BAD);
        feedBackRepository.update(feedBack);
        session.flush();
        session.clear();
        Optional<FeedBack> actualResult = feedBackRepository.findById(FEEDBACK_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("new Comment", actualResult.get().getComment());
        assertEquals(Rating.BAD, actualResult.get().getRating());
    }

    @Test
    void shouldDeleteFeedback() {
        FeedBack feedBack = feedBackRepository.findById(FEEDBACK_ID).get();
        feedBackRepository.delete(feedBack);
        Optional<FeedBack> actualResult = feedBackRepository.findById(FEEDBACK_ID);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllFeedback() {
        List<FeedBack> actualResult = feedBackRepository.findAll();

        assertEquals(3, actualResult.size());
    }
}