package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDao {

    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param usuario el usuario a agregar.
     */
    public void addUsuario(Usuario usuario) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(usuario);
        transaction.commit();
        session.close();
    }

    /**
     * Obtiene un usuario de la base de datos por su ID.
     *
     * @param id el ID del usuario.
     * @return el usuario con el ID especificado.
     */
    public Usuario getUsuario(int id) {
        Session session = Connection.getInstance().getSessionFactory();
        Usuario usuario = session.get(Usuario.class, id);
        session.close();
        return usuario;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return una lista de todos los usuarios.
     */
    public List<Usuario> getAllUsuarios() {
        Session session = Connection.getInstance().getSessionFactory();
        List<Usuario> usuarios = session.createQuery("from Usuario", Usuario.class).list();
        session.close();
        return usuarios;
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param usuario el usuario a actualizar.
     */
    public void updateUsuario(Usuario usuario) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(usuario);
        transaction.commit();
        session.close();
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id el identificador del usuario a eliminar.
     */
    public void deleteUsuario(int id) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        Usuario usuario = session.get(Usuario.class, id);
        if (usuario != null) {
            session.delete(usuario);
        }
        transaction.commit();
        session.close();
    }

    /**
     * Obtiene un usuario de la base de datos por su email.
     *
     * @param email el email del usuario.
     * @return el usuario con el email especificado.
     */
    public Usuario findUsuarioByEmail(String email) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery("FROM Usuario WHERE email = :email", Usuario.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }


}
