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
    public void addHuella(Huella huella) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(huella);
        transaction.commit();
        session.close();
    }

    public void updateHuella(Huella huella) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(huella);
        transaction.commit();
        session.close();
    }

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

    public List<Huella> getHuellasByUsuario(int usuarioId) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery("FROM Huella WHERE idUsuario.id = :usuarioId", Huella.class)
                    .setParameter("usuarioId", usuarioId)
                    .list();
        }
    }


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

    public List<Object[]> getHuellasConImpactoByUsuario(int usuarioId) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery(
                            "SELECT h.idActividad.nombre, h.valor, h.unidad, (h.valor * h.idActividad.idCategoria.factorEmision) " +
                                    "FROM Huella h WHERE h.idUsuario.id = :usuarioId", Object[].class)
                    .setParameter("usuarioId", usuarioId)
                    .list();
        }
    }

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
