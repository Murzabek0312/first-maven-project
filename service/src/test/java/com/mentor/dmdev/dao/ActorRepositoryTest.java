package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Actor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class ActorRepositoryTest extends BaseIT {

    private static final Long ACTOR_ID = 1L;

    private final ActorRepository actorRepository;

    @Test
    void shouldGetActor() {
        Optional<Actor> actualResult = actorRepository.findById(ACTOR_ID);
        assertTrue(actualResult.isPresent());
        Actor actor = actualResult.get();
        assertEquals("James", actor.getFirstname());
        assertEquals("Bond", actor.getSecondname());
        assertEquals(LocalDate.of(2012, 5, 13), actor.getBirthDate());
        assertEquals("Agent007", actor.getBiography());
    }

    @Test
    void shouldUpdateActor() {
        Actor actor = actorRepository.findById(ACTOR_ID).get();
        actor.setFirstname("Will");
        actor.setSecondname("Smith");
        actor.setBiography("Good Actor");
        session.update(actor);
        session.flush();
        session.clear();
        Optional<Actor> actualResult = actorRepository.findById(ACTOR_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("Will", actualResult.get().getFirstname());
        assertEquals("Smith", actualResult.get().getSecondname());
        assertEquals("Good Actor", actualResult.get().getBiography());
        assertEquals(LocalDate.of(2012, 5, 13), actualResult.get().getBirthDate());
    }

    @Test
    void shouldDeleteActor() {
        Actor actor = actorRepository.findById(ACTOR_ID).get();
        actorRepository.delete(actor);
        Optional<Actor> actualResult = actorRepository.findById(ACTOR_ID);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllActor() {

        List<Actor> actualResult = actorRepository.findAll();
        assertEquals(7, actualResult.size());
    }
}