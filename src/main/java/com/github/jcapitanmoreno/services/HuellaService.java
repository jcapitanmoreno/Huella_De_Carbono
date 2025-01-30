package com.github.jcapitanmoreno.services;

import com.github.jcapitanmoreno.dao.HuellaDao;
import com.github.jcapitanmoreno.entities.Huella;

import java.util.List;

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
}
