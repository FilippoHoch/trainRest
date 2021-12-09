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

    @FXML // fx:id="editDatabaseLabel"
    private Label editDatabaseLabel; // Value injected by FXMLLoader

    @FXML
    void editClass(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 300, 375);
        stage.setTitle("Search");
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
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 300, 375);
        stage.setTitle("Search");
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
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 300, 375);
        stage.setTitle("Search");
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
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 300, 375);
        stage.setTitle("Search");
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
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 300, 375);
        stage.setTitle("Search");
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
