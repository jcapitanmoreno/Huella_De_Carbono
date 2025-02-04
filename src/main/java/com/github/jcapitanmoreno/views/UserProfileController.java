package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.HabitoService;
import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.services.UsuarioService;
import com.github.jcapitanmoreno.utils.Alertas;
import com.github.jcapitanmoreno.utils.ChangeScene;
import com.github.jcapitanmoreno.utils.InformeUtils;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserProfileController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView<Object> footprintTable;

    @FXML
    private TableColumn<Object, String> usernameColumn;

    @FXML
    private TableColumn<Object, String> activityColumn;

    @FXML
    private TableColumn<Object, String> valueColumn;

    @FXML
    private TableColumn<Object, String> unitColumn;

    private UsuarioService usuarioService;
    private HuellaService huellaService;
    private HabitoService habitoService;

    private boolean showingHuellas = true;

    public void initialize() {
        usuarioService = new UsuarioService();
        huellaService = new HuellaService();
        habitoService = new HabitoService();

        footprintTable.setEditable(true);

        usernameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Huella) {
                Huella huella = (Huella) cellData.getValue();
                return new SimpleStringProperty(huella.getIdUsuario().getNombre());
            } else {
                Habito habito = (Habito) cellData.getValue();
                return new SimpleStringProperty(habito.getIdUsuario().getNombre());
            }
        });

        activityColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Huella) {
                Huella huella = (Huella) cellData.getValue();
                return new SimpleStringProperty(huella.getIdActividad().getNombre());
            } else {
                Habito habito = (Habito) cellData.getValue();
                return new SimpleStringProperty(habito.getIdActividad().getNombre());
            }
        });

        valueColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Huella) {
                Huella huella = (Huella) cellData.getValue();
                return new SimpleStringProperty(huella.getValor().toString());
            } else {
                Habito habito = (Habito) cellData.getValue();
                return new SimpleStringProperty(String.valueOf(habito.getFrecuencia()));
            }
        });

        unitColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Huella) {
                Huella huella = (Huella) cellData.getValue();
                return new SimpleStringProperty(huella.getUnidad());
            } else {
                Habito habito = (Habito) cellData.getValue();
                return new SimpleStringProperty(habito.getTipo());
            }
        });

        loadUserData();
        loadFootprintData();
    }

    private void loadUserData() {
        Usuario usuario = UsuarioSingleton.get_Instance().getPlayerLoged();
        if (usuario != null) {
            nameField.setText(usuario.getNombre());
            emailField.setText(usuario.getEmail());
        }
    }

    private void loadFootprintData() {
        Usuario usuario = UsuarioSingleton.get_Instance().getPlayerLoged();
        if (usuario != null) {
            if (showingHuellas) {
                footprintTable.getItems().setAll(huellaService.getHuellasByUsuario(usuario.getId()));
            } else {
                footprintTable.getItems().setAll(habitoService.getHabitosByUsuario(usuario.getId()));
            }
        }
    }

    @FXML
    private void handleChangeView() {
        showingHuellas = !showingHuellas;
        loadFootprintData();
    }

    @FXML
    private void updateName() {
        Usuario usuario = UsuarioSingleton.get_Instance().getPlayerLoged();
        if (usuario != null) {
            usuario.setNombre(nameField.getText());
            try {
                usuarioService.updateUsuario(usuario);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDownloadReport() {
        Usuario usuario = UsuarioSingleton.get_Instance().getPlayerLoged();
        if (usuario != null) {
            InformeUtils informeUtils = new InformeUtils();
            try {
                informeUtils.generarInforme(usuario, (Stage) nameField.getScene().getWindow());
                Alertas.showInfoAlert("Informe Generado", "El informe se ha generado correctamente.", "El informe se ha guardado en la ubicación seleccionada.");
            } catch (IOException e) {
                e.printStackTrace();
                Alertas.showErrorAlert("Error", "No se pudo generar el informe.", e.getMessage());
            }
        }
    }

    @FXML
    private void switchToInicioView() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/InicioView.fxml");
    }


    @FXML
    private void showInfoAlert() {
        Alertas.showInfoAlert("Información", "Información General",
                "Esta es la vista de perfil de usuario,\n" +
                        "aquí puedes ver tus datos personales,\n" +
                        "tus huellas y hábitos.\n" +
                        "Puedes cambiar entre huellas y hábitos\n" +
                        "con el botón de la parte inferior derecha.");
    }

    @FXML
    private void showInfoTable() {
        Alertas.showInfoAlert("Información", "Información de la tabla",
                "En esta tabla puedes ver tus huellas y hábitos.\n" +
                        "Puedes cambiar entre huellas y hábitos\n" +
                        "con el botón de la parte inferior derecha.");
    }

    @FXML
    private void showInfoUpdate() {
        Alertas.showInfoAlert("Información", "Información de la actualización",
                "En esta sección puedes actualizar tu nombre.\n" +
                        "Para ello, escribe tu nuevo nombre en el campo de texto\n" +
                        "y pulsa la tecla 'ENTER'.");
    }
}
