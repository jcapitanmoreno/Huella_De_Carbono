package com.github.jcapitanmoreno.views;

import com.github.jcapitanmoreno.entities.Recomendacion;
import com.github.jcapitanmoreno.services.HabitoService;
import com.github.jcapitanmoreno.utils.UsuarioSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionesController {

    @FXML
    private ListView<String> recomendacionesList;

    private HabitoService habitoService;

    public void initialize() {
        habitoService = new HabitoService();
        loadRecomendaciones();
    }

    private void loadRecomendaciones() {
        int usuarioId = UsuarioSingleton.get_Instance().getPlayerLoged().getId();
        List<Recomendacion> recomendaciones = habitoService.getRecomendacionesByUsuario(usuarioId);

        ObservableList<String> items = FXCollections.observableArrayList(
                recomendaciones.stream().map(Recomendacion::getDescripcion).collect(Collectors.toList())
        );
        recomendacionesList.setItems(items);
    }
}