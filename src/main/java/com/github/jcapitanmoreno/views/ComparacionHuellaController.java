package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.utils.ChangeScene;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Map;

public class ComparacionHuellaController {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private TableView<Map.Entry<String, BigDecimal>> impactTable;
    @FXML
    private TableColumn<Map.Entry<String, BigDecimal>, String> categoryColumn;
    @FXML
    private TableColumn<Map.Entry<String, BigDecimal>, BigDecimal> impactColumn;
    @FXML
    private ComboBox<String> timePeriodComboBox;
    @FXML
    private Button toggleViewButton;

    @FXML
    private ImageView flechaIzquierda;

    private HuellaService huellaService;
    private boolean showingBarChart = true;


    public void initialize() {
        huellaService = new HuellaService();
        ObservableList<String> timePeriods = FXCollections.observableArrayList("Semana", "Mes", "Año");
        timePeriodComboBox.setItems(timePeriods);
        timePeriodComboBox.setValue("Semana");
        timePeriodComboBox.setOnAction(event -> loadComparacion());

        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        impactColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        loadComparacion();

        actividadColumn.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        impactoColumn.setCellValueFactory(new PropertyValueFactory<>("impacto"));
    }

    private void loadComparacion() {
        int usuarioId = UsuarioSingleton.get_Instance().getPlayerLoged().getId();
        String selectedPeriod = timePeriodComboBox.getValue();
        Map<String, BigDecimal> huellaUsuarioPorCategoria;
        Map<String, BigDecimal> mediaHuellasPorCategoria;

        try {
            huellaUsuarioPorCategoria = huellaService.getHuellaUsuarioPorCategoria(usuarioId, selectedPeriod);
            mediaHuellasPorCategoria = huellaService.getMediaHuellasPorCategoria(selectedPeriod);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (showingBarChart) {
            barChart.getData().clear();
            XYChart.Series<String, Number> seriesUsuario = new XYChart.Series<>();
            seriesUsuario.setName("Tu Huella");

            XYChart.Series<String, Number> seriesMedia = new XYChart.Series<>();
            seriesMedia.setName("Media");

            for (String categoria : huellaUsuarioPorCategoria.keySet()) {
                BigDecimal huellaUsuario = huellaUsuarioPorCategoria.get(categoria);
                BigDecimal mediaHuella = mediaHuellasPorCategoria.get(categoria);

                seriesUsuario.getData().add(new XYChart.Data<>(categoria, huellaUsuario));
                seriesMedia.getData().add(new XYChart.Data<>(categoria, mediaHuella));
            }

            barChart.getData().addAll(seriesUsuario, seriesMedia);
        } else {
            ObservableList<Map.Entry<String, BigDecimal>> impactData = FXCollections.observableArrayList();
            for (String categoria : huellaUsuarioPorCategoria.keySet()) {
                BigDecimal impacto = huellaUsuarioPorCategoria.get(categoria);
                impactData.add(new AbstractMap.SimpleEntry<>(categoria, impacto));
            }
            impactTable.setItems(impactData);
        }
    }


    @FXML
    private void toggleView() {
        showingBarChart = !showingBarChart;
        barChart.setVisible(showingBarChart);
        impactTable.setVisible(!showingBarChart);
        loadComparacion();
    }

    @FXML
    private void switchToInicioView() {
        Stage stage = (Stage) flechaIzquierda.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/InicioView.fxml");
    }
}