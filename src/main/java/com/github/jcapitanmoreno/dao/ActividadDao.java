package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ActividadDao {
    public void addActividad(Actividad actividad) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(actividad);
        transaction.commit();
        session.close();
    }

    public Actividad getActividad(int id) {
        Session session = Connection.getInstance().getSessionFactory();;
        Actividad actividad = session.get(Actividad.class, id);
        session.close();
        return actividad;
    }

    public List<Actividad> getAllActividades() {
        Session session = Connection.getInstance().getSessionFactory();;
        List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
        session.close();
        return actividades;
    }

    public void updateActividad(Actividad actividad) {
        Session session = Connection.getInstance().getSessionFactory();;
        Transaction transaction = session.beginTransaction();
        session.update(actividad);
        transaction.commit();
        session.close();
    }

    public void deleteActividad(int id) {
        Session session = Connection.getInstance().getSessionFactory();;
        Transaction transaction = session.beginTransaction();
        Actividad actividad = session.get(Actividad.class, id);
        if (actividad != null) {
            session.delete(actividad);
        }
        transaction.commit();
        session.close();
    }
}
