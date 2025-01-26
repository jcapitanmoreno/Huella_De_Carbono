package com.github.jcapitanmoreno.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class conexion_test {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if (session!=null) {
            System.out.println("Conexión establecida");
        }else {
            System.out.println("Conexión no establecida");
        }
    }
}
