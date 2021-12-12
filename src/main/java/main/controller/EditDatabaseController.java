package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApplication;

import java.io.IOException;

public class EditDatabaseController {


    @FXML // fx:id="editDatabaseLabel"
    private Label editDatabaseLabel; // Value injected by FXMLLoader

    @FXML
    void editClass() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("classDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Classi");
        stage.setScene(sceneSearch);
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editLink() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("linkDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Collegamenti");
        stage.setScene(sceneSearch);
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editPath() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("pathDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Percorsi");
        stage.setScene(sceneSearch);
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editStation() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("stationDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Stazioni");
        stage.setScene(sceneSearch);
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editTicket() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("ticketDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Biglietto");
        stage.setScene(sceneSearch);
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
