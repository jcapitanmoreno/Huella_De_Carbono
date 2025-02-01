package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.HabitoId;
import com.github.jcapitanmoreno.entities.Recomendacion;
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

    public List<Habito> getHabitosByUsuario(int usuarioId) {
        Transaction transaction = null;
        List<Habito> habitos = null;
        try (Session session = Connection.getInstance().getSessionFactory()) {
            transaction = session.beginTransaction();
            habitos = session.createQuery("FROM Habito WHERE idUsuario.id = :usuarioId", Habito.class)
                    .setParameter("usuarioId", usuarioId)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return habitos;
    }

    public List<Recomendacion> getRecomendacionesByUsuario(int usuarioId) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery(
                            "SELECT r FROM Habito h " +
                                    "JOIN h.idActividad a " +
                                    "JOIN a.idCategoria c " +
                                    "JOIN c.recomendacions r " +
                                    "WHERE h.idUsuario.id = :usuarioId", Recomendacion.class)
                    .setParameter("usuarioId", usuarioId)
                    .list();
        }
    }

}
