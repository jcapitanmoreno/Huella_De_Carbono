package com.github.jcapitanmoreno.services;

import com.github.jcapitanmoreno.dao.ActividadDao;
import com.github.jcapitanmoreno.entities.Actividad;

import java.util.List;

public class ActividadService {
    private ActividadDao actividadDao;

    public ActividadService() {
        actividadDao = new ActividadDao();
    }

    public List<Actividad> getAllActividades() {

        return  actividadDao.getAllActividades();
    }
}
