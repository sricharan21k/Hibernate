package com.hibernate.app;


import com.hibernate.app.config.HibernateUtil;
import com.hibernate.app.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Person person = session.get(Person.class, 1);
        System.out.println(person);


    }


}
