package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.services.HuellaService;
import com.github.jcapitanmoreno.utils.ChangeScene;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.Map;

public class ComparacionHuellaController {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private ComboBox<String> timePeriodComboBox;
    @FXML
    private TableView<Impacto> impactTable;
    @FXML
    private TableColumn<Impacto, String> actividadColumn;
    @FXML
    private TableColumn<Impacto, BigDecimal> impactoColumn;
    @FXML
    private Button switchViewButton;
    @FXML
    private ImageView flechaIzquierda;

    private HuellaService huellaService;
    private boolean isBarChartVisible = true;

    public void initialize() {
        huellaService = new HuellaService();
        ObservableList<String> timePeriods = FXCollections.observableArrayList("Semana", "Mes", "Año");
        timePeriodComboBox.setItems(timePeriods);
        timePeriodComboBox.setValue("Semana");
        timePeriodComboBox.setOnAction(event -> loadComparacion());
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

        barChart.getData().clear();
        XYChart.Series<String, Number> seriesUsuario = new XYChart.Series<>();
        seriesUsuario.setName("Tu Huella");

        XYChart.Series<String, Number> seriesMedia = new XYChart.Series<>();
        seriesMedia.setName("Media");

        ObservableList<Impacto> impactos = FXCollections.observableArrayList();

        for (String categoria : huellaUsuarioPorCategoria.keySet()) {
            BigDecimal huellaUsuario = huellaUsuarioPorCategoria.get(categoria);
            BigDecimal mediaHuella = mediaHuellasPorCategoria.get(categoria);

            seriesUsuario.getData().add(new XYChart.Data<>(categoria, huellaUsuario));
            seriesMedia.getData().add(new XYChart.Data<>(categoria, mediaHuella));

            impactos.add(new Impacto(categoria, huellaUsuario));
        }

        barChart.getData().addAll(seriesUsuario, seriesMedia);
        impactTable.setItems(impactos);
    }

    @FXML
    private void switchView() {
        isBarChartVisible = !isBarChartVisible;
        barChart.setVisible(isBarChartVisible);
        impactTable.setVisible(!isBarChartVisible);
    }

    @FXML
    private void switchToInicioView() {
        Stage stage = (Stage) flechaIzquierda.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/InicioView.fxml");
    }

    public static class Impacto {
        private final String actividad;
        private final BigDecimal impacto;

        public Impacto(String actividad, BigDecimal impacto) {
            this.actividad = actividad;
            this.impacto = impacto;
        }

        public String getActividad() {
            return actividad;
        }

        public BigDecimal getImpacto() {
            return impacto;
        }
    }
}
