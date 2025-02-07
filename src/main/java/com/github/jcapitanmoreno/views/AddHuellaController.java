package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Actividad;
import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.services.ActividadService;
import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.utils.Alertas;
import com.github.jcapitanmoreno.utils.ChangeScene;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

    @FXML
    private ImageView flechaIzquierda;

    private HuellaService huellaService;
    private ActividadService actividadService;

    public AddHuellaController() {
        huellaService = new HuellaService();
        actividadService = new ActividadService();
    }

    /**
     * Inicializa el controlador, configurando el ComboBox de actividades.
     */
    @FXML
    public void initialize() {
        List<Actividad> actividades = actividadService.getAllActividades();
        actividadComboBox.setItems(FXCollections.observableArrayList(actividades));
    }


    /**
     * Maneja el cambio de actividad en el ComboBox, actualizando el campo de unidad.
     */
    @FXML
    private void handleActividadChange() {
        Actividad actividad = actividadComboBox.getValue();
        if (actividad != null) {
            unidadField.setText(actividad.getIdCategoria().getUnidad());
        } else {
            unidadField.clear();
        }
    }

    /**
     * Maneja la adición de una nueva huella, validando los campos y mostrando alertas en caso de error.
     */
    @FXML
    private void handleAddHuella() {
        try {
            String valorText = valorField.getText();
            if (valorText == null || valorText.trim().isEmpty()) {
                Alertas.showErrorAlert("Error", "Error 010", "El valor es obligatorio.");

                return;
            }

            BigDecimal valor;
            try {
                valor = new BigDecimal(valorText);
            } catch (NumberFormatException e) {
                Alertas.showErrorAlert("Error", "Error 011", "El valor debe ser un número válido.");
                return;
            }

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                Alertas.showErrorAlert("Error", "Error 012", "El valor debe ser mayor que cero.");
                return;
            }

            String unidad = unidadField.getText();
            if (unidad == null || unidad.trim().isEmpty()) {
                Alertas.showErrorAlert("Error", "Error 013", "La unidad es obligatoria.");
                return;
            }

            Actividad actividad = actividadComboBox.getValue();
            if (actividad == null) {
                Alertas.showErrorAlert("Error", "Error 014", "La actividad es obligatoria.");
                return;
            }

            LocalDate fecha = fechaPicker.getValue();
            if (fecha == null) {
                Alertas.showErrorAlert("Error", "Error 015", "Fecha es obligatoria.");
                return;
            }

            if (fecha.isAfter(LocalDate.now())) {
                Alertas.showErrorAlert("Error", "Error 016", "La fecha no puede ser posterior a la fecha actual.");

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
            Alertas.showInfoAlert("Éxito", "Huella añadida correctamente.", "Huella añadida correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            Alertas.showErrorAlert("Error", "Error 017", "No se pudo añadir la huella.");
        }
    }

    /**
     * Cambia a la vista de menú de huellas.
     */
    @FXML
    private void switchToHuellaMenuView() {
        Stage stage = (Stage) flechaIzquierda.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/HuellaMenuView.fxml");
    }
}
