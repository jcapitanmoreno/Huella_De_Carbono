package com.github.jcapitanmoreno.dao;

import com.github.jcapitanmoreno.connection.Connection;
import com.github.jcapitanmoreno.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDao {
    public void addUsuario(Usuario usuario) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.save(usuario);
        transaction.commit();
        session.close();
    }
    public Usuario getUsuario(int id) {
        Session session = Connection.getInstance().getSessionFactory();
        Usuario usuario = session.get(Usuario.class, id);
        session.close();
        return usuario;
    }
    public List<Usuario> getAllUsuarios() {
        Session session = Connection.getInstance().getSessionFactory();
        List<Usuario> usuarios = session.createQuery("from Usuario", Usuario.class).list();
        session.close();
        return usuarios;
    }
    public void updateUsuario(Usuario usuario) {
        Session session = Connection.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.update(usuario);
        transaction.commit();
        session.close();
    }
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

    public Usuario getUsuarioByEmailAndPassword(String email, String password) {
        try (Session session = Connection.getInstance().getSessionFactory()) {
            return session.createQuery("FROM Usuario WHERE email = :email AND contraseña = :password", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        }
    }


}
