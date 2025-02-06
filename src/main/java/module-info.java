module com.github.jcapitanmoreno {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires kernel;
    requires layout;
    requires java.mail;

    opens com.github.jcapitanmoreno to javafx.fxml;
    exports com.github.jcapitanmoreno;
    exports com.github.jcapitanmoreno.views;
    opens com.github.jcapitanmoreno.views to javafx.fxml;
    opens com.github.jcapitanmoreno.entities to org.hibernate.orm.core;

}
