package com.github.jcapitanmoreno.services;

import com.github.jcapitanmoreno.dao.ActividadDao;
import com.github.jcapitanmoreno.entities.Actividad;

import java.util.List;

public class ActividadService {
    private ActividadDao actividadDao;

    public ActividadService() {
        actividadDao = new ActividadDao();
    }

    public void addActividad(Actividad actividad) throws Exception {
        if (actividad.getNombre() == null || actividad.getNombre().isEmpty()) {
            throw new Exception("El nombre de la actividad no puede estar vacío");
        }
        actividadDao.addActividad(actividad);
    }

    public Actividad getActividad(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la actividad no es válido");
        }
        return actividadDao.getActividad(id);
    }

    public List<Actividad> getAllActividades() {

        return  actividadDao.getAllActividades();
    }

    public void updateActividad(Actividad actividad) throws Exception {
        if (actividad.getId() <= 0) {
            throw new Exception("El ID de la actividad no es válido");
        }
        actividadDao.updateActividad(actividad);
    }

    public void deleteActividad(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la actividad no es válido");
        }
        actividadDao.deleteActividad(id);
    }
}
