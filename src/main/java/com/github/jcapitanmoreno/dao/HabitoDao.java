package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.HabitoId;
import com.github.jcapitanmoreno.entities.Recomendacion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HabitoDao {

    /**
     * Agrega un nuevo hábito a la base de datos.
     *
     * @param habito el hábito a agregar.
     */
    public void addHabito(Habito habito) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(habito);
        transaction.commit();
        session.close();
    }

    /**
     * Actualiza un hábito existente en la base de datos.
     *
     * @param habito el hábito a actualizar.
     */
    public void updateHabito(Habito habito) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(habito);
        transaction.commit();
        session.close();
    }

    /**
     * Elimina un hábito de la base de datos.
     *
     * @param id el identificador del hábito a eliminar.
     */
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

    /**
     * Obtiene todos los hábitos de la base de datos.
     *
     * @return una lista de todos los hábitos.
     */
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

    /**
     * Obtiene todas las recomendaciones asociadas a un usuario.
     *
     * @param usuarioId el identificador del usuario.
     * @return una lista de todas las recomendaciones asociadas al usuario.
     */
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
