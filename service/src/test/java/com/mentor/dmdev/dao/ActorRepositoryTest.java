package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActorRepositoryTest extends BaseIT {

    private static ActorRepository actorRepository;
    private static Long actorId;

    @BeforeEach
    void prepare() {
        actorRepository = context.getBean(ActorRepository.class);
        var actor = Actor.builder()
                .firstname("James")
                .secondname("Bond")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("Agent007")
                .build();
        actorId = (long) session.save(actor);
    }

    @Test
    void shouldGetActor() {
        Optional<Actor> actualResult = actorRepository.findById(actorId);
        assertTrue(actualResult.isPresent());
        Actor actor = actualResult.get();
        assertEquals("James", actor.getFirstname());
        assertEquals("Bond", actor.getSecondname());
        assertEquals(LocalDate.of(2012, 5, 13), actor.getBirthDate());
        assertEquals("Agent007", actor.getBiography());
    }

    @Test
    void shouldUpdateActor() {
        Actor actor = actorRepository.findById(actorId).get();
        actor.setFirstname("Will");
        actor.setSecondname("Smith");
        actor.setBiography("Good Actor");
        session.update(actor);
        session.flush();
        session.clear();
        Optional<Actor> actualResult = actorRepository.findById(actorId);
        assertTrue(actualResult.isPresent());
        assertEquals("Will", actualResult.get().getFirstname());
        assertEquals("Smith", actualResult.get().getSecondname());
        assertEquals("Good Actor", actualResult.get().getBiography());
        assertEquals(LocalDate.of(2012, 5, 13), actualResult.get().getBirthDate());
    }

    @Test
    void shouldDeleteActor() {
        Actor actor = actorRepository.findById(actorId).get();
        actorRepository.delete(actor);
        Optional<Actor> actualResult = actorRepository.findById(actorId);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllActor() {
        var actor = Actor.builder()
                .firstname("actorName1")
                .secondname("astorSecondName1")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("actorBiography1")
                .build();

        actorRepository.save(actor);
        session.flush();

        List<Actor> actualResult = actorRepository.findAll();
        assertEquals(2, actualResult.size());
    }
}