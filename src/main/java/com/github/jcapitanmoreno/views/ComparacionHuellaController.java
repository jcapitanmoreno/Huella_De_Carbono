package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComparacionHuellaController {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private ComboBox<String> timePeriodComboBox;

    private HuellaService huellaService;

    public void initialize() {
        huellaService = new HuellaService();
        ObservableList<String> timePeriods = FXCollections.observableArrayList("Semana", "Mes", "Año");
        timePeriodComboBox.setItems(timePeriods);
        timePeriodComboBox.setValue("Mes"); // Valor por defecto
        timePeriodComboBox.setOnAction(event -> loadComparacion());
        loadComparacion();
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
    }
}
