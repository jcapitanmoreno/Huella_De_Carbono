package com.github.jcapitanmoreno.views;

import java.io.IOException;

import com.github.jcapitanmoreno.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("views/secondary");
    }
}
