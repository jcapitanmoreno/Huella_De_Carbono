package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Actividad;
import org.hibernate.Session;

import java.util.List;

public class ActividadDao {

    /**
     * Obtiene todas las actividades de la base de datos.
     *
     * @return una lista de todas las actividades.
     */
    public List<Actividad> getAllActividades() {
        Session session = Connection.getInstance().getSessionFactory();;
        List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
        session.close();
        return actividades;
    }
}
