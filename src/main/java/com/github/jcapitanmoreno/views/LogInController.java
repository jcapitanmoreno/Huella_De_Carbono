package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.UsuarioService;
import com.github.jcapitanmoreno.utils.Alertas;
import com.github.jcapitanmoreno.utils.ChangeScene;
import com.github.jcapitanmoreno.utils.PasswordUtils;
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

    /**
     * Maneja el registro de un nuevo usuario.
     */
    @FXML
    private void handleRegister() {
        String nombre = usuarioRegister.getText();
        String email = correoRegister.getText();
        String password = passwordRegister.getText();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Alertas.showWarningAlert("Validación", null, "Todos los campos son obligatorios para el registro.");
            return;
        }

        if (!isValidEmail(email)) {
            Alertas.showWarningAlert("Validación", null, "El formato del correo electrónico no es válido.");
            return;
        }

        if (nombre.length() < 3 || nombre.length() > 20) {
            Alertas.showWarningAlert("Validación", null, "El nombre de usuario debe tener entre 3 y 20 caracteres.");
            return;
        }

        if (password.length() < 6) {
            Alertas.showWarningAlert("Validación", null, "La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContraseña(hashedPassword);
        nuevoUsuario.setFechaRegistro(Instant.now());

        try {
            usuarioService.addUsuario(nuevoUsuario);
            Alertas.showInfoAlert("Registro Exitoso", "Usuario registrado correctamente.", "Usuario registrado correctamente.");
        } catch (Exception e) {
            Alertas.showErrorAlert("Error de Registro", "Error 505", e.getMessage());
        }
    }

    /**
     * Maneja el inicio de sesión de un usuario.
     */
    @FXML
    private void handleLogin() {
        String email = usuarioLogIn.getText();
        String password = passwordLogIn.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alertas.showWarningAlert("Validación", null, "El correo y la contraseña son obligatorios para iniciar sesión.");
            return;
        }

        if (!isValidEmail(email)) {
            Alertas.showWarningAlert("Validación", null, "El formato del correo electrónico no es válido.");
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);

        try {
            Usuario usuario = usuarioService.findUsuarioByEmail(email);
            if (usuario != null && usuario.getContraseña().equals(hashedPassword)) {
                UsuarioSingleton.get_Instance().login(usuario);
                Alertas.showInfoAlert("Login Exitoso", "Una EcoBienvenida", "Bienvenido " + usuario.getNombre());
                changeScene("InicioView.fxml");
            } else {
                Alertas.showErrorAlert("Error de Login", "Correo o contraseña incorrectos.", "Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            Alertas.showErrorAlert("Error de Login", "Error 707", e.getMessage());
        }
    }

    /**
     * Cambia a la vista de inicio.
     */
    private void changeScene(String fxml) {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/" + fxml);
    }

    /**
     * Valida el formato de un correo electrónico.
     *
     * @param email el correo electrónico a validar.
     * @return true si el correo es válido, false en caso contrario.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}