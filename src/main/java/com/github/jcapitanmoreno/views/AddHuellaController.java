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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AddHuellaController {
    @FXML
    private TextField valorField;

    @FXML
    private TextField unidadField;

    @FXML
    private ComboBox<Actividad> actividadComboBox;

    @FXML
    private DatePicker fechaPicker;

    private HuellaService huellaService;
    private ActividadService actividadService;

    public AddHuellaController() {
        huellaService = new HuellaService();
        actividadService = new ActividadService();
    }

    @FXML
    public void initialize() {
        List<Actividad> actividades = actividadService.getAllActividades();
        actividadComboBox.setItems(FXCollections.observableArrayList(actividades));
    }

    @FXML
    private void handleActividadChange() {
        Actividad actividad = actividadComboBox.getValue();
        if (actividad != null) {
            unidadField.setText(actividad.getIdCategoria().getUnidad());
        } else {
            unidadField.clear();
        }
    }

    @FXML
    private void handleAddHuella() {
        try {
            String valorText = valorField.getText();
            if (valorText == null || valorText.trim().isEmpty()) {
                showAlert("Error", "El valor es obligatorio.");
                return;
            }

            BigDecimal valor;
            try {
                valor = new BigDecimal(valorText);
            } catch (NumberFormatException e) {
                showAlert("Error", "El valor debe ser un número válido.");
                return;
            }

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert("Error", "El valor debe ser mayor que cero.");
                return;
            }

            String unidad = unidadField.getText();
            if (unidad == null || unidad.trim().isEmpty()) {
                showAlert("Error", "La unidad es obligatoria.");
                return;
            }

            Actividad actividad = actividadComboBox.getValue();
            if (actividad == null) {
                showAlert("Error", "Actividad es obligatoria.");
                return;
            }

            LocalDate fecha = fechaPicker.getValue();
            if (fecha == null) {
                showAlert("Error", "Fecha es obligatoria.");
                return;
            }

            if (fecha.isAfter(LocalDate.now())) {
                showAlert("Error", "La fecha no puede ser posterior a la fecha actual.");
                return;
            }

            Instant fechaInstant = fecha.atStartOfDay(ZoneId.systemDefault()).toInstant();

            Huella nuevaHuella = new Huella();
            nuevaHuella.setValor(valor);
            nuevaHuella.setUnidad(unidad);
            nuevaHuella.setFecha(fechaInstant);
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
