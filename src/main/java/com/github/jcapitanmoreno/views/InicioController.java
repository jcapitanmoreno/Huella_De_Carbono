package com.github.jcapitanmoreno.views;

import java.io.IOException;

import com.github.jcapitanmoreno.App;
import com.github.jcapitanmoreno.utils.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private Button usuarioLogIn;

    @FXML
    private void switchToUserProfileView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/UserProfileView.fxml");
    }

    @FXML
    private void switchToHuellaMenuView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/HuellaMenuView.fxml");
    }

    @FXML
    private void switchToHabitoMenuView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/HabitoMenuView.fxml");
    }

    @FXML
    private void switchToRecomendacionesView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/RecomendacionesView.fxml");
    }

    @FXML
    private void switchToComparacionHuellaView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/ComparacionHuellaView.fxml");
    }
    @FXML
    private void switchToLogInView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/LogInView.fxml");
    }
    @FXML
    private void switchToAyudaView() {
        Stage stage = (Stage) usuarioLogIn.getScene().getWindow();
        ChangeScene.changeScene(stage, "/com/github/jcapitanmoreno/views/EnviarCorreoView.fxml");
    }
}