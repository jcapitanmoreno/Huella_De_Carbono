package com.github.jcapitanmoreno.test;

import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.UsuarioService;

import java.time.Instant;
import java.time.LocalDate;

public class Test_Usuarios {
    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();

        try {
            // Crear un nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre("LOLO");
            nuevoUsuario.setContraseña("1234");
            nuevoUsuario.setEmail("LOLO@gmail.com");
            nuevoUsuario.setFechaRegistro(Instant.now());
            usuarioService.addUsuario(nuevoUsuario);
            System.out.println("Usuario añadido: " + nuevoUsuario);

            // Obtener el usuario por ID
            Usuario usuarioObtenido = usuarioService.getUsuario(nuevoUsuario.getId());
            System.out.println("Usuario obtenido: " + usuarioObtenido);

            // Actualizar el usuario
            usuarioObtenido.setNombre("Juan Perez Actualizado");
            usuarioService.updateUsuario(usuarioObtenido);
            System.out.println("Usuario actualizado: " + usuarioObtenido);

            // Obtener todos los usuarios
            System.out.println("Todos los usuarios: " + usuarioService.getAllUsuarios());

            // Eliminar el usuario
           usuarioService.deleteUsuario(usuarioObtenido.getId());
           System.out.println("Usuario eliminado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
