package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditDatabaseController {


    // TODO: 10/12/2021 non vengono aggiunti i biglietti
    // TODO: 10/12/2021 il put dei biglietti non aggiorna la data
    // TODO: 10/12/2021 add station non funziona
    // TODO: 10/12/2021 path update non funziona
    // TODO: 10/12/2021 add path non funziona


    @FXML // fx:id="editDatabaseLabel"
    private Label editDatabaseLabel; // Value injected by FXMLLoader

    @FXML
    void editClass(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("classDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Classi");
        stage.setScene(sceneSearch);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editLink(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("linkDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Collegamenti");
        stage.setScene(sceneSearch);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editPath(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("pathDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Percorsi");
        stage.setScene(sceneSearch);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editStation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("stationDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Stazioni");
        stage.setScene(sceneSearch);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void editTicket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("ticketDatabase.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 496, 259);
        stage.setTitle("Modifica Biglietto");
        stage.setScene(sceneSearch);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) editDatabaseLabel.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}
