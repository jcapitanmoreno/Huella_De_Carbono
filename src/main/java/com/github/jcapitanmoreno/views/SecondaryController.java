package com.github.jcapitanmoreno.views;

import java.io.IOException;

import com.github.jcapitanmoreno.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("views/primary");
    }
}