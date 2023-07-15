package com.mentor.dmdev.entity;

import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ActorTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldSaveActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var actor = Actor.builder()
                .firstname("actorName1")
                .secondname("astorSecondName1")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("actorBiography1")
                .build();

        var actorId = session.save(actor);
        session.flush();
        session.clear();

        assertNotNull(actorId);

        session.getTransaction().rollback();
    }

    @Test
    void shouldGetActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var actor = Actor.builder()
                .firstname("actorName1")
                .secondname("astorSecondName1")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("actorBiography1")
                .build();

        var actorId = (long) session.save(actor);
        session.flush();
        session.clear();

        Actor actualResult = session.get(Actor.class, actorId);

        assertEquals("actorName1", actualResult.getFirstname());
        assertEquals("astorSecondName1", actualResult.getSecondname());
        assertEquals(LocalDate.of(2012, 5, 13), actualResult.getBirthDate());
        assertEquals("actorBiography1", actualResult.getBiography());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var actor = Actor.builder()
                .firstname("actorName1")
                .secondname("astorSecondName1")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("actorBiography1")
                .build();

        var actorId = (long) session.save(actor);
        session.flush();
        session.clear();

        Actor currentActor = session.get(Actor.class, actorId);

        currentActor.setBiography("new Biography");
        session.update(currentActor);
        session.flush();
        session.clear();

        Actor actualResult = session.get(Actor.class, actorId);

        assertEquals("actorName1", actualResult.getFirstname());
        assertEquals("astorSecondName1", actualResult.getSecondname());
        assertEquals(LocalDate.of(2012, 5, 13), actualResult.getBirthDate());
        assertEquals("new Biography", actualResult.getBiography());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var actor = Actor.builder()
                .firstname("actorName1")
                .secondname("astorSecondName1")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("actorBiography1")
                .build();

        var actorId = (long) session.save(actor);
        session.flush();
        session.clear();

        session.remove(actor);
        session.flush();

        Actor actualResult = session.get(Actor.class, actorId);
        assertNull(actualResult);

        session.getTransaction().rollback();

    }

}