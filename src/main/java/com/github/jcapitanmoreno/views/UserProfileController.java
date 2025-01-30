package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.entities.Usuario;
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
    private TableView<Huella> footprintTable;

    @FXML
    private TableColumn<Huella, String> usernameColumn;

    @FXML
    private TableColumn<Huella, String> activityColumn;

    @FXML
    private TableColumn<Huella, Double> valueColumn;

    @FXML
    private TableColumn<Huella, String> unitColumn;

    private UsuarioService usuarioService;
    private HuellaService huellaService;

    public void initialize() {
        usuarioService = new UsuarioService();
        huellaService = new HuellaService();

        footprintTable.setEditable(true); // Asegúrate de que la tabla sea editable

        usernameColumn.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            return new SimpleStringProperty(huella.getIdUsuario().getNombre());
        });

        activityColumn.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            return new SimpleStringProperty(huella.getIdActividad().getNombre());
        });

        valueColumn.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            return new SimpleObjectProperty<>(huella.getValor().doubleValue());
        });

        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        valueColumn.setOnEditCommit(event -> {
            Huella huella = event.getRowValue();
            try {
                BigDecimal nuevoValor = new BigDecimal(event.getNewValue().toString());
                huella.setValor(nuevoValor);
                huellaService.updateHuella(huella);
                System.out.println("Valor actualizado a: " + nuevoValor);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el valor a BigDecimal: " + event.getNewValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        unitColumn.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            return new SimpleStringProperty(huella.getUnidad());
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
            footprintTable.getItems().setAll(huellaService.getHuellasByUsuario(usuario.getId()));
        }
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
    private void updateValue() {
        Huella selectedHuella = footprintTable.getSelectionModel().getSelectedItem();
        if (selectedHuella != null) {
            selectedHuella.setValor(new BigDecimal(valueColumn.getText()));
            try {
                huellaService.updateHuella(selectedHuella);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
