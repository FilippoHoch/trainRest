module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires spark.core;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires unirest.java;

    opens main to javafx.fxml;
    exports main;
}