package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.HabitoService;
import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.services.UsuarioService;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DoubleStringConverter;
import org.hibernate.mapping.Value;

import java.math.BigDecimal;

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
}
