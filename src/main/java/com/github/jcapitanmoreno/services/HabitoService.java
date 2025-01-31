package com.github.jcapitanmoreno.services;

import com.github.jcapitanmoreno.dao.HabitoDao;
import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.HabitoId;

import java.util.List;

public class HabitoService {

    private HabitoDao habitoDao;

    public HabitoService() {
        habitoDao = new HabitoDao();
    }

    public void addHabito(Habito habito) throws Exception {
        if (habito.getTipo() == null || habito.getTipo().isEmpty()) {
            throw new Exception("El tipo del hábito no puede estar vacío");
        }
        habitoDao.addHabito(habito);
    }

    public Habito getHabito(HabitoId id) throws Exception {
        if (id == null) {
            throw new Exception("El ID del hábito no es válido");
        }
        return habitoDao.getHabito(id);
    }

    public List<Habito> getAllHabitos() {
        return habitoDao.getAllHabitos();
    }

    public void updateHabito(Habito habito) throws Exception {
        if (habito.getId() == null) {
            throw new Exception("El ID del hábito no es válido");
        }
        habitoDao.updateHabito(habito);
    }

    public void deleteHabito(HabitoId id) throws Exception {
        if (id == null) {
            throw new Exception("El ID del hábito no es válido");
        }
        habitoDao.deleteHabito(id);
    }

    public List<Habito> getHabitosByUsuario(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido");
        }
        return habitoDao.getHabitosByUsuario(usuarioId);
    }

}


