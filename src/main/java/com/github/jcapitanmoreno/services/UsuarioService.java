package com.github.jcapitanmoreno.services;


import com.github.jcapitanmoreno.dao.UsuarioDao;
import com.github.jcapitanmoreno.entities.Usuario;


import java.util.List;

public class UsuarioService {
    private UsuarioDao usuarioDao;

    public UsuarioService() {
        usuarioDao = new UsuarioDao();
    }

    /**
     * Agrega un nuevo usuario.
     *
     * @param usuario el usuario a agregar.
     */
    public void addUsuario(Usuario usuario) throws Exception {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new Exception("El nombre del usuario no puede estar vacío");
        }
        usuarioDao.addUsuario(usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el ID del usuario a obtener.
     * @return el usuario con el ID especificado.
     */
    public Usuario getUsuario(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        return usuarioDao.getUsuario(id);
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return una lista con todos los usuarios.
     */
    public List<Usuario> getAllUsuarios() {
        return usuarioDao.getAllUsuarios();
    }

    /**
     * Actualiza un usuario.
     *
     * @param usuario el usuario a actualizar.
     */
    public void updateUsuario(Usuario usuario) throws Exception {
        if (usuario.getId() <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        usuarioDao.updateUsuario(usuario);
    }

    /**
     * Elimina un usuario.
     *
     * @param id el ID del usuario a eliminar.
     */
    public void deleteUsuario(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del usuario no es válido");
        }
        usuarioDao.deleteUsuario(id);
    }

    /**
     * Obtiene un usuario por su correo.
     *
     * @param email el correo del usuario a obtener.
     * @return el usuario con el correo especificado.
     */
    public Usuario findUsuarioByEmail(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("El correo no puede estar vacío");
        }
        return usuarioDao.findUsuarioByEmail(email);
    }
}
