package com.mentor.dmdev.configuration;

import com.mentor.dmdev.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.mentor.dmdev")
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public Session session(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                ((proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args)));
    }

    @PreDestroy
    public void clearAll() {
        sessionFactory().close();
    }
}