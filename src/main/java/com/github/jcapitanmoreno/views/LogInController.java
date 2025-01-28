package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.UsuarioService;
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
            showAlert(Alert.AlertType.INFORMATION, "Registro Exitoso", "Usuario registrado correctamente.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de Registro", e.getMessage());
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
                showAlert(Alert.AlertType.INFORMATION, "Login Exitoso", "Bienvenido " + usuario.getNombre());
                changeScene("InicioView.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error de Login", "Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de Login", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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