package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.HabitoService;
import com.github.jcapitanmoreno.utils.ChangeScene;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HabitoMenuController {
    @FXML
    private TableView<Habito> habitoTable;

    @FXML
    private TableColumn<Habito, String> usernameColumn;

    @FXML
    private TableColumn<Habito, String> activityColumn;

    @FXML
    private TableColumn<Habito, String> frequencyColumn;

    @FXML
    private TableColumn<Habito, String> typeColumn;

    @FXML
    private ImageView deleteImageView;

    @FXML
    private ImageView flechaIzquierda;

    @FXML
    private ImageView agregarArchivo;

    private HabitoService habitoService;

    public void initialize() {
        habitoService = new HabitoService();

        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario().getNombre()));
        activityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre()));
        frequencyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFrecuencia().toString()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));

        loadHabitoData();
    }

    private void loadHabitoData() {
        Usuario usuario = UsuarioSingleton.get_Instance().getPlayerLoged();
        if (usuario != null) {
            ObservableList<Habito> habitos = FXCollections.observableArrayList(habitoService.getHabitosByUsuario(usuario.getId()));
            habitoTable.setItems(habitos);
        }
    }

    @FXML
    private void handleDelete() {
        Habito selectedHabito = habitoTable.getSelectionModel().getSelectedItem();
        if (selectedHabito != null) {
            habitoTable.getItems().remove(selectedHabito);
            try {
                habitoService.deleteHabito(selectedHabito.getId());
                System.out.println("Hábito eliminado: " + selectedHabito);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToInicioView() {
        Stage stage = (Stage) flechaIzquierda.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/InicioView.fxml");
    }

    @FXML
    private void switchToAddHabitoView() {
        Stage stage = (Stage) agregarArchivo.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/AddHabitoView.fxml");
    }
}
