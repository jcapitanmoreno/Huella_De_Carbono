package com.github.jcapitanmoreno.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {


    public static void changeScene(Stage stage, String fxml) {
        try {
            Parent root = FXMLLoader.load(ChangeScene.class.getResource(fxml));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
