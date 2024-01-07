package com.hibernate.app.config;

import com.hibernate.app.entity.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
import java.util.PropertyPermission;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactoryFromXml() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactoryNotWorking() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(Person.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                Metadata metadata = new MetadataSources(serviceRegistry).addAnnotatedClass(Person.class)
                        .getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getLegacyFactory() {
        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/demo");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "Shree@123");
        properties.put(Environment.SHOW_SQL, "true");

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Person.class);

        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
        Configuration configuration = new Configuration();
//        configuration.setProperty("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/demo");
//        configuration.setProperty("jakarta.persistence.jdbc.user", "root");
//        configuration.setProperty("jakarta.persistence.jdbc.password", "Shree@123");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/demo");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "Shree@123");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
//            try {
//                sessionFactory = new MetadataSources(registry)
//                        .addAnnotatedClass(Person.class)
//                        .buildMetadata().buildSessionFactory();
//
//            } catch (Exception exception) {
//                StandardServiceRegistryBuilder.destroy(registry);
//            }
//        }
        return new MetadataSources(registry)
                .addAnnotatedClass(Person.class)
                .buildMetadata().buildSessionFactory();
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/demo");
        properties.put(Environment.JAKARTA_JDBC_USER, "root");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "Shree@123");
        properties.put(Environment.SHOW_SQL, "true");

        configuration.setProperties(properties);
        return configuration;
    }

    ;


}
