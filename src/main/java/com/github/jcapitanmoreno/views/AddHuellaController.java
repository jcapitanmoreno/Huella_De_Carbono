package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Actividad;
import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.ActividadService;
import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.services.UsuarioService;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class AddHuellaController {
    @FXML
    private TextField valorField;

    @FXML
    private TextField unidadField;

    @FXML
    private ComboBox<Usuario> usuarioComboBox;

    @FXML
    private ComboBox<Actividad> actividadComboBox;

    private HuellaService huellaService;
    private UsuarioService usuarioService;
    private ActividadService actividadService;

    public AddHuellaController() {
        huellaService = new HuellaService();
        usuarioService = new UsuarioService();
        actividadService = new ActividadService();
    }

    @FXML
    public void initialize() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        usuarioComboBox.setItems(FXCollections.observableArrayList(usuarios));

        List<Actividad> actividades = actividadService.getAllActividades();
        actividadComboBox.setItems(FXCollections.observableArrayList(actividades));
    }

    @FXML
    private void handleAddHuella() {
        try {
            BigDecimal valor = new BigDecimal(valorField.getText());
            String unidad = unidadField.getText();
            Usuario usuario = usuarioComboBox.getValue();
            Actividad actividad = actividadComboBox.getValue();

            if (usuario == null || actividad == null) {
                showAlert("Error", "Usuario y Actividad son obligatorios.");
                return;
            }

            Huella nuevaHuella = new Huella();
            nuevaHuella.setValor(valor);
            nuevaHuella.setUnidad(unidad);
            nuevaHuella.setFecha(Instant.now());
            nuevaHuella.setIdUsuario(UsuarioSingleton.get_Instance().getPlayerLoged());
            nuevaHuella.setIdActividad(actividad);

            huellaService.addHuella(nuevaHuella);
            showAlert("Éxito", "Huella añadida correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo añadir la huella.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
