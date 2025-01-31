package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.utils.Alertas;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.math.BigDecimal;

public class HuellaMenuController {

    @FXML
    private TableView<Huella> footprintTable;

    @FXML
    private TableColumn<Huella, String> usernameColumn;

    @FXML
    private TableColumn<Huella, String> activityColumn;

    @FXML
    private TableColumn<Huella, Double> valueColumn;

    @FXML
    private ImageView deleteImageView;

    @FXML
    private TableColumn<Huella, String> unitColumn;

    private HuellaService huellaService;

    public void initialize() {
        huellaService = new HuellaService();

        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario().getNombre()));
        activityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre()));
        valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor().doubleValue()));
        unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidad()));



        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        valueColumn.setOnEditCommit(event -> {
            Huella huella = event.getRowValue();
            try {
                BigDecimal nuevoValor = BigDecimal.valueOf(event.getNewValue());
                huella.setValor(nuevoValor);
                huellaService.updateHuella(huella);
                System.out.println("Valor actualizado a: " + nuevoValor);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el valor a BigDecimal: " + event.getNewValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        loadFootprintData();
    }

    private void loadFootprintData() {
        Usuario usuario = UsuarioSingleton.get_Instance().getPlayerLoged();
        if (usuario != null) {
            ObservableList<Huella> huellas = FXCollections.observableArrayList(huellaService.getHuellasByUsuario(usuario.getId()));
            footprintTable.setItems(huellas);
        }
    }

    @FXML
    private void handleDelete() {
        Huella selectedHuella = footprintTable.getSelectionModel().getSelectedItem();
        if (selectedHuella != null) {
            footprintTable.getItems().remove(selectedHuella);
            try {
                huellaService.deleteHuella(selectedHuella.getId());
                Alertas.showInfoAlert("Huella eliminada", "Huella eliminada correctamente", "Huella eliminada con éxito.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
