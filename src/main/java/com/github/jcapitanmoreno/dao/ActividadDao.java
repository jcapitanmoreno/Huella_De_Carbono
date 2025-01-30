package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ActividadDao {

    public List<Actividad> getAllActividades() {
        Session session = Connection.getInstance().getSessionFactory();;
        List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
        session.close();
        return actividades;
    }
}
