package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Huella;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HuellaDao {
    public void addHuella(Huella huella) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(huella);
        transaction.commit();
        Connection.getInstance().close();
    }

    public Huella getHuella(int id) {
        Session session = Connection.getInstance().getSessionFactory();
        Huella huella = session.get(Huella.class, id);
        Connection.getInstance().close();
        return huella;
    }

    public List<Huella> getAllHuellas() {
        Session session = Connection.getInstance().getSessionFactory();
        List<Huella> huellas = session.createQuery("from Huella", Huella.class).list();
        Connection.getInstance().close();
        return huellas;
    }

    public void updateHuella(Huella huella) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(huella);
        transaction.commit();
        Connection.getInstance().close();
    }

    public void deleteHuella(int id) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        Huella huella = session.get(Huella.class, id);
        if (huella != null) {
            session.delete(huella);
        }
        transaction.commit();
        Connection.getInstance().close();
    }
}
