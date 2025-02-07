package com.github.jcapitanmoreno.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {

    /**
     * Cambia la escena de la aplicación.
     *
     * @param stage el escenario principal de la aplicación.
     * @param fxml el archivo FXML que define la nueva escena.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public static void changeScene(Stage stage, String fxml) {
        try {
            Parent root = FXMLLoader.load(ChangeScene.class.getResource(fxml));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
