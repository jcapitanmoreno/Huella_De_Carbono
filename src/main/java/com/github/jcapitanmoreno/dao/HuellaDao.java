package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Huella;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HuellaDao {


    /**
     * Agrega una nueva huella a la base de datos.
     *
     * @param huella la huella a agregar.
     */
    public void addHuella(Huella huella) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(huella);
        transaction.commit();
        session.close();
    }

    /**
     * Actualiza una huella existente en la base de datos.
     *
     * @param huella la huella a actualizar.
     */
    public void updateHuella(Huella huella) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(huella);
        transaction.commit();
        session.close();
    }

    /**
     * Elimina una huella de la base de datos.
     *
     * @param id el identificador de la huella a eliminar.
     */
    public void deleteHuella(int id) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        Huella huella = session.get(Huella.class, id);
        if (huella != null) {
            session.delete(huella);
        }
        transaction.commit();
        session.close();
    }

    /**
     * Obtiene todas las huellas de la base de datos por usuario.
     *
     * @return una lista de todas las huellas.
     */
    public List<Huella> getHuellasByUsuario(int usuarioId) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery("FROM Huella WHERE idUsuario.id = :usuarioId", Huella.class)
                    .setParameter("usuarioId", usuarioId)
                    .list();
        }
    }


    /**
     * Obtiene la huella de un usuario por categoría.
     * (para carlcular el impacto de huella de carbono en 1 usuario)
     *
     * @param usuarioId el identificador del usuario.
     * @param period    el periodo de tiempo a considerar.
     * @return un mapa con el nombre de la categoría y la huella total.
     */
    public Map<String, BigDecimal> getHuellaUsuarioPorCategoria(int usuarioId, String period) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            String query = "SELECT a.idCategoria.nombre, SUM(h.valor * a.idCategoria.factorEmision) " +
                    "FROM Huella h JOIN h.idActividad a " +
                    "WHERE h.idUsuario.id = :usuarioId AND h.fecha >= :startDate " +
                    "GROUP BY a.idCategoria.nombre";
            List<Object[]> results = session.createQuery(query, Object[].class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("startDate", getStartDate(period))
                    .list();

            return results.stream().collect(Collectors.toMap(
                    result -> (String) result[0],
                    result -> (BigDecimal) result[1]
            ));
        }
    }

    /**
     * Obtiene la media de huellas por categoría.
     * (para calcular el impacto de huella de carbono en todos los usuarios)
     *
     * @param period el periodo de tiempo a considerar.
     * @return un mapa con el nombre de la categoría y la media de huellas.
     */
    public Map<String, BigDecimal> getMediaHuellasPorCategoria(String period) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            String query = "SELECT a.idCategoria.nombre, AVG(h.valor * a.idCategoria.factorEmision) " +
                    "FROM Huella h JOIN h.idActividad a " +
                    "WHERE h.fecha >= :startDate " +
                    "GROUP BY a.idCategoria.nombre";
            List<Object[]> results = session.createQuery(query, Object[].class)
                    .setParameter("startDate", getStartDate(period))
                    .list();

            return results.stream().collect(Collectors.toMap(
                    result -> (String) result[0],
                    result -> BigDecimal.valueOf((Double) result[1])
            ));
        }
    }

    /**
     * Obtiene las huellas con impacto de un usuario.
     *
     * @param usuarioId el identificador del usuario.
     * @return una lista de huellas con impacto.
     */
    public List<Object[]> getHuellasConImpactoByUsuario(int usuarioId) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery(
                            "SELECT h.idActividad.nombre, h.valor, h.unidad, (h.valor * h.idActividad.idCategoria.factorEmision) " +
                                    "FROM Huella h WHERE h.idUsuario.id = :usuarioId", Object[].class)
                    .setParameter("usuarioId", usuarioId)
                    .list();
        }
    }

    /**
     * Obtiene la fecha de inicio basada en el periodo.
     *
     * @param period el periodo de tiempo (Semana, Mes, Año).
     * @return la fecha de inicio como un objeto Instant.
     */
    private Instant getStartDate(String period) {
        Calendar calendar = Calendar.getInstance();
        switch (period) {
            case "Semana":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case "Mes":
                calendar.add(Calendar.MONTH, -1);
                break;
            case "Año":
                calendar.add(Calendar.YEAR, -1);
                break;
        }
        Date date = calendar.getTime();
        return date.toInstant();
    }
}
