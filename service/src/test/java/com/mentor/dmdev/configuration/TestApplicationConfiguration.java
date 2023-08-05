package com.mentor.dmdev.configuration;

import com.mentor.dmdev.util.HibernateTestUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(ApplicationConfiguration.class)
@Configuration
public class TestApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateTestUtil.buildSessionFactory();
    }

}