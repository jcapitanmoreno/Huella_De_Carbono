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

    public void addHuella(Huella huella) throws Exception {
        if (huella.getValor() == null) {
            throw new Exception("El valor de la huella no puede estar vacío");
        }
        huellaDao.addHuella(huella);
    }

    public Huella getHuella(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la huella no es válido");
        }
        return huellaDao.getHuella(id);
    }

    public List<Huella> getAllHuellas() {
        return huellaDao.getAllHuellas();
    }

    public void updateHuella(Huella huella) throws Exception {
        if (huella.getId() <= 0) {
            throw new Exception("El ID de la huella no es válido");
        }
        huellaDao.updateHuella(huella);
    }

    public void deleteHuella(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la huella no es válido");
        }
        huellaDao.deleteHuella(id);
    }

    public List<Huella> getHuellasByUsuario(int usuarioId) {
        return huellaDao.getHuellasByUsuario(usuarioId);
    }

    public Map<String, BigDecimal> getHuellaUsuarioPorCategoria(int usuarioId, String period) throws Exception {
        if (usuarioId <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        return huellaDao.getHuellaUsuarioPorCategoria(usuarioId, period);
    }

    public Map<String, BigDecimal> getMediaHuellasPorCategoria(String period) {
        return huellaDao.getMediaHuellasPorCategoria(period);
    }
}
