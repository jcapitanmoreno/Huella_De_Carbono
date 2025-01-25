module com.github.jcapitanmoreno {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.jcapitanmoreno to javafx.fxml;
    exports com.github.jcapitanmoreno;
    exports com.github.jcapitanmoreno.views;
    opens com.github.jcapitanmoreno.views to javafx.fxml;
}
