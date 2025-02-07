package com.github.jcapitanmoreno.services;

import com.github.jcapitanmoreno.dao.HabitoDao;
import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.HabitoId;
import com.github.jcapitanmoreno.entities.Recomendacion;

import java.util.List;

public class HabitoService {

    private HabitoDao habitoDao;

    public HabitoService() {
        habitoDao = new HabitoDao();
    }

    /**
     * Agrega un nuevo hábito.
     *
     * @param habito el hábito a agregar.
     */
    public void addHabito(Habito habito) throws Exception {
        if (habito.getTipo() == null || habito.getTipo().isEmpty()) {
            throw new Exception("El tipo del hábito no puede estar vacío");
        }
        habitoDao.addHabito(habito);
    }

    /**
     * Actualiza un hábito.
     *
     * @param habito el hábito a actualizar.
     */
    public void updateHabito(Habito habito) throws Exception {
        if (habito.getId() == null) {
            throw new Exception("El ID del hábito no es válido");
        }
        habitoDao.updateHabito(habito);
    }

    /**
     * Elimina un hábito.
     *
     * @param id el ID del hábito a eliminar.
     */
    public void deleteHabito(HabitoId id) throws Exception {
        if (id == null) {
            throw new Exception("El ID del hábito no es válido");
        }
        habitoDao.deleteHabito(id);
    }

    /**
     * Obtiene todos los hábitos de un usuario.
     *
     * @param usuarioId el ID del usuario.
     * @return una lista con todos los hábitos del usuario.
     */
    public List<Habito> getHabitosByUsuario(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido");
        }
        return habitoDao.getHabitosByUsuario(usuarioId);
    }

    /**
     * Obtiene todas las recomendaciones de un usuario.
     *
     * @param usuarioId el ID del usuario.
     * @return una lista con todas las recomendaciones del usuario.
     */
    public List<Recomendacion> getRecomendacionesByUsuario(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido");
        }
        return habitoDao.getRecomendacionesByUsuario(usuarioId);
    }

}


