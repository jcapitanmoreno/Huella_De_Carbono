package com.github.jcapitanmoreno.utils;

import com.github.jcapitanmoreno.entities.Usuario;

public class UsuarioSingleton {
    private static UsuarioSingleton _instance;
    private static Usuario playerLoged;

    /**
     * Obtiene la única instancia del singleton.
     * Si la instancia aún no ha sido creada, la crea.
     *
     * @return La instancia única de UsuarioSingleton.
     */
    public static UsuarioSingleton get_Instance() {
        if (_instance == null) {
            _instance = new UsuarioSingleton();
        }
        return _instance;
    }

    /**
     * Realiza el login de un usuario, almacenando la instancia del usuario logueado.
     *
     * @param usuarios El objeto del usuario que se está logueando.
     */
    public void login(Usuario usuarios) {
        playerLoged = usuarios;
    }

    /**
     * Obtiene el usuario que está actualmente logueado.
     *
     * @return El objeto del usuario logueado.
     */
    public Usuario getPlayerLoged() {
        return playerLoged;
    }

    /**
     * Realiza el logout del usuario, estableciendo la variable playerLoged como null.
     */
    public void logout() {
        playerLoged = null;
    }
}
