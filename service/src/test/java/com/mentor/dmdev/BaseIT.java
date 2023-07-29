package com.mentor.dmdev;

import com.mentor.dmdev.configuration.TestApplicationConfiguration;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class BaseIT {

    protected static AnnotationConfigApplicationContext context;
    protected static Session session;

    @BeforeAll
    static void prepareAll() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfiguration.class);
        session = context.getBean(Session.class);
    }

    @BeforeEach
    void prepareEach() {
        session.beginTransaction();
    }

    @AfterEach
    void clear() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void clearAll() {
        context.close();
    }
}