package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.UsuarioService;
import com.github.jcapitanmoreno.utils.Alertas;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.Instant;

public class LogInController {

    @FXML
    private TextField usuarioLogIn;
    @FXML
    private PasswordField passwordLogIn;
    @FXML
    private TextField usuarioRegister;
    @FXML
    private TextField correoRegister;
    @FXML
    private PasswordField passwordRegister;

    private UsuarioService usuarioService;

    public LogInController() {
        usuarioService = new UsuarioService();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String nombre = usuarioRegister.getText();
        String email = correoRegister.getText();
        String password = passwordRegister.getText();

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContraseña(password);
        nuevoUsuario.setFechaRegistro(Instant.now());

        try {
            usuarioService.addUsuario(nuevoUsuario);
            Alertas.showInfoAlert("Registro Exitoso", "Usuario registrado correctamente.", "Usuario registrado correctamente.");
        } catch (Exception e) {
            Alertas.showErrorAlert("Error de Registro", "Error 505", e.getMessage());
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = usuarioLogIn.getText();
        String password = passwordLogIn.getText();

        try {
            Usuario usuario = usuarioService.getUsuarioByEmailAndPassword(email, password);
            if (usuario != null) {
                UsuarioSingleton.get_Instance().login(usuario);
                Alertas.showInfoAlert("Login Exitoso", "Una EcoBienvenida", "Bienvenido " + usuario.getNombre());
                changeScene("UserProfileView.fxml");
            } else {
                Alertas.showErrorAlert("Error de Login", "Correo o contraseña incorrectos.", "Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            Alertas.showErrorAlert("Error de Login", "Error 707", e.getMessage());
        }
    }

    private void changeScene(String fxml) {
        try {
            Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}