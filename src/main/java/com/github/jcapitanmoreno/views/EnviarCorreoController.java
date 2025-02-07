package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.services.EmailService;
import com.github.jcapitanmoreno.utils.Alertas;
import com.github.jcapitanmoreno.utils.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnviarCorreoController {
    @FXML
    private TextField correoField;

    @FXML
    private TextField asuntoField;

    @FXML
    private TextArea mensajeArea;

    private EmailService emailService = new EmailService();

    /**
     * Maneja el envío de un correo electrónico a la administración.
     */
    @FXML
    private void handleEnviarCorreo() {
        String correo = correoField.getText();
        String asunto = asuntoField.getText();
        String mensaje = mensajeArea.getText();

        if (correo == null || correo.trim().isEmpty()) {
            Alertas.showErrorAlert("Error", "Error 000", "El correo electrónico es obligatorio.");
            return;
        }

        if (asunto == null || asunto.trim().isEmpty()) {
            Alertas.showErrorAlert("Error", "Error 001", "El asunto es obligatorio.");
            return;
        }

        if (mensaje == null || mensaje.trim().isEmpty()) {
            Alertas.showErrorAlert("Error", "Error 002", "El mensaje es obligatorio.");
            return;
        }

        boolean correoEnviado = emailService.enviarCorreo(correo, asunto, mensaje);
        if (correoEnviado) {
            Alertas.showInfoAlert("Éxito", "Correo enviado correctamente.", "Tu correo ha sido enviado a la administración.");
        } else {
            Alertas.showErrorAlert("Error", "Error 003", "No se pudo enviar el correo.");
        }
    }

    @FXML
    private void switchToInicioView() {
        Stage stage = (Stage) correoField.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/InicioView.fxml");
    }

}
