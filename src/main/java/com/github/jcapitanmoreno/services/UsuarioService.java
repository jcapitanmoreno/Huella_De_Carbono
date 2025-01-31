package com.github.jcapitanmoreno.services;


import com.github.jcapitanmoreno.dao.UsuarioDao;
import com.github.jcapitanmoreno.entities.Usuario;


import java.util.List;

public class UsuarioService {
    private UsuarioDao usuarioDao;

    public UsuarioService() {
        usuarioDao = new UsuarioDao();
    }

    public void addUsuario(Usuario usuario) throws Exception {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new Exception("El nombre del usuario no puede estar vacío");
        }
        usuarioDao.addUsuario(usuario);
    }

    public Usuario getUsuario(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        return usuarioDao.getUsuario(id);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioDao.getAllUsuarios();
    }

    public void updateUsuario(Usuario usuario) throws Exception {
        if (usuario.getId() <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        usuarioDao.updateUsuario(usuario);
    }

    public void deleteUsuario(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        usuarioDao.deleteUsuario(id);
    }

    public Usuario getUsuarioByEmailAndPassword(String email, String password) throws Exception {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new Exception("El correo y la contraseña no pueden estar vacíos");
        }
        return usuarioDao.getUsuarioByEmailAndPassword(email, password);
    }
}
