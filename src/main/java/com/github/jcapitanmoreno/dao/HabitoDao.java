package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.HabitoId;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HabitoDao {
    public void addHabito(Habito habito) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(habito);
        transaction.commit();
        session.close();
    }

    public Habito getHabito(HabitoId id) {
        Session session = Connection.getInstance().getSessionFactory();
        Habito habito = session.get(Habito.class, id);
        session.close();
        return habito;
    }

    public List<Habito> getAllHabitos() {
        Session session = Connection.getInstance().getSessionFactory();
        List<Habito> habitos = session.createQuery("from Habito", Habito.class).list();
        session.close();
        return habitos;
    }

    public void updateHabito(Habito habito) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(habito);
        transaction.commit();
        session.close();
    }

    public void deleteHabito(HabitoId id) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        Habito habito = session.get(Habito.class, id);
        if (habito != null) {
            session.delete(habito);
        }
        transaction.commit();
        session.close();
    }

}
