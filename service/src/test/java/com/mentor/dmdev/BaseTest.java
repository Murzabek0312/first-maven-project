package com.mentor.dmdev;

import com.mentor.dmdev.util.HibernateTestUtil;
import com.mentor.dmdev.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static SessionFactory sessionFactory;
    protected static Session session;

    @BeforeAll
    static void prepareAll() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        session = HibernateUtil.getSessionByProxy(sessionFactory);
    }

    @AfterEach
    void clear() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void clearAll() {
        sessionFactory.close();
    }
}