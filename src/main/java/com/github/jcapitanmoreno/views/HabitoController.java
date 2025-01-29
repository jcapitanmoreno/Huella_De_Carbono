package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Actividad;
import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.HabitoId;
import com.github.jcapitanmoreno.services.ActividadService;
import com.github.jcapitanmoreno.services.HabitoService;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class HabitoController {

    @FXML
    private ComboBox<String> tipoComboBox;

    @FXML
    private TextField frecuenciaField;

    @FXML
    private ComboBox<Actividad> actividadComboBox;

    @FXML
    private DatePicker fechaPicker;

    private HabitoService habitoService;
    private ActividadService actividadService;

    public HabitoController() {
        habitoService = new HabitoService();
        actividadService = new ActividadService();
    }

    @FXML
    public void initialize() {
        tipoComboBox.setItems(FXCollections.observableArrayList("diario", "semanal", "mensual", "anual"));
        List<Actividad> actividades = actividadService.getAllActividades();
        actividadComboBox.setItems(FXCollections.observableArrayList(actividades));
    }

    @FXML
    private void handleAddHabito() {
        try {
            String tipo = tipoComboBox.getValue();
            if (tipo == null || tipo.trim().isEmpty()) {
                showAlert("Error", "El tipo es obligatorio.");
                return;
            }

            String frecuenciaText = frecuenciaField.getText();
            if (frecuenciaText == null || frecuenciaText.trim().isEmpty()) {
                showAlert("Error", "La frecuencia es obligatoria.");
                return;
            }

            int frecuencia;
            try {
                frecuencia = Integer.parseInt(frecuenciaText);
            } catch (NumberFormatException e) {
                showAlert("Error", "La frecuencia debe ser un número válido.");
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

            Habito nuevoHabito = new Habito();
            nuevoHabito.setTipo(tipo);
            nuevoHabito.setFrecuencia(frecuencia);
            nuevoHabito.setUltimaFecha(fechaInstant);
            nuevoHabito.setIdUsuario(UsuarioSingleton.get_Instance().getPlayerLoged());
            nuevoHabito.setIdActividad(actividad);

            HabitoId habitoId = new HabitoId();
            habitoId.setIdUsuario(UsuarioSingleton.get_Instance().getPlayerLoged().getId());
            habitoId.setIdActividad(actividad.getId());
            nuevoHabito.setId(habitoId);

            habitoService.addHabito(nuevoHabito);
            showAlert("Éxito", "Hábito añadido correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo añadir el hábito.");
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
