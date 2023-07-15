package com.mentor.dmdev.util;

import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(MoviesActor.class);
        configuration.addAnnotatedClass(Subscription.class);
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(FeedBack.class);
        return configuration;
    }
}