package com.github.jcapitanmoreno.services;

import com.github.jcapitanmoreno.dao.HuellaDao;
import com.github.jcapitanmoreno.entities.Huella;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class HuellaService {

    private HuellaDao huellaDao;

    public HuellaService() {
        huellaDao = new HuellaDao();
    }

    /**
     * Agrega una nueva huella.
     *
     * @param huella
     * @throws Exception
     */
    public void addHuella(Huella huella) throws Exception {
        if (huella.getValor() == null) {
            throw new Exception("El valor de la huella no puede estar vacío");
        }
        huellaDao.addHuella(huella);
    }

    /**
     * Actualiza una huella existente.
     *
     * @param huella la huella a actualizar.
     * @throws Exception si el ID de la huella no es válido.
     */
    public void updateHuella(Huella huella) throws Exception {
        if (huella.getId() <= 0) {
            throw new Exception("El ID de la huella no es válido");
        }
        huellaDao.updateHuella(huella);
    }

    /**
     * Elimina una huella.
     *
     * @param id el ID de la huella a eliminar.
     * @throws Exception si el ID de la huella no es válido.
     */
    public void deleteHuella(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la huella no es válido");
        }
        huellaDao.deleteHuella(id);
    }

    /**
     * Obtiene todas las huellas de un usuario específico.
     *
     * @param usuarioId el ID del usuario.
     * @return una lista de todas las huellas del usuario.
     */
    public List<Huella> getHuellasByUsuario(int usuarioId) {
        return huellaDao.getHuellasByUsuario(usuarioId);
    }

    /**
     * Obtiene la huella de carbono de un usuario por categoría en un periodo específico.
     *
     * @param usuarioId el ID del usuario.
     * @param period el periodo de tiempo (Semana, Mes, Año).
     * @return un mapa con la categoría y la suma de la huella de carbono.
     * @throws Exception si el ID del usuario no es válido.
     */
    public Map<String, BigDecimal> getHuellaUsuarioPorCategoria(int usuarioId, String period) throws Exception {
        if (usuarioId <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        return huellaDao.getHuellaUsuarioPorCategoria(usuarioId, period);
    }

    /**
     * Obtiene la media de las huellas de carbono por categoría en un periodo específico.
     *
     * @param period el periodo de tiempo (Semana, Mes, Año).
     * @return un mapa con la categoría y la media de la huella de carbono.
     */
    public Map<String, BigDecimal> getMediaHuellasPorCategoria(String period) {
        return huellaDao.getMediaHuellasPorCategoria(period);
    }

    /**
     * Obtiene las huellas de carbono con impacto de un usuario especifico.
     *
     * @param usuarioId el ID del usuario.
     * @return una lista con las huellas de carbono con impacto del usuario.
     */
    public List<Object[]> getHuellasConImpactoByUsuario(int usuarioId) {
        return huellaDao.getHuellasConImpactoByUsuario(usuarioId);
    }
}
