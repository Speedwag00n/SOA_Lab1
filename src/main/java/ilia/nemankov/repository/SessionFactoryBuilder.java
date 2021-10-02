package ilia.nemankov.repository;

import ilia.nemankov.entity.Coordinates;
import ilia.nemankov.entity.Movie;
import ilia.nemankov.entity.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class SessionFactoryBuilder {

    private static final SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.URL, System.getenv("DATABASE_URL"));
        properties.put(Environment.USER, System.getenv("DATABASE_USER"));
        properties.put(Environment.PASS, System.getenv("DATABASE_PASSWORD"));
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.SHOW_SQL, true);

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Coordinates.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
