package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PathManagerController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    List<Path> paths = new ArrayList<>();

    @FXML
    private Button deletePathButton;

    @FXML
    private Label labelArrivingPath;

    @FXML
    private Label labelArrivingPathHours;

    @FXML
    private Label labelArrivingPathMinutes;

    @FXML
    private Label labelPathName;

    @FXML
    private Label labelPathSeats;

    @FXML
    private Label labelStartingPath;

    @FXML
    private Label labelStartingPathHours;

    @FXML
    private Label labelStartingPathMinutes;

    @FXML
    private Label labelTwoPoints1;

    @FXML
    private Label labelTwoPoints2;

    @FXML
    private TextField pathArriveTimeHour;

    @FXML
    private TextField pathArriveTimeMinutes;

    @FXML
    private TextField pathDepartureTimeHour;

    @FXML
    private TextField pathDepartureTimeMinutes;

    @FXML
    private TextField pathName;

    @FXML
    private TextField pathSeats;

    @FXML
    private Button savePathButton;

    @FXML
    private ChoiceBox<String> viewPath;

    @FXML
    void cancelEditPath(ActionEvent event) {
        // TODO: 08/12/2021 change to better self stage declaration
        Stage stage = (Stage) pathArriveTimeHour.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deletePath(ActionEvent event) {

    }

    @FXML
    void newPath(ActionEvent event) {

    }

    @FXML
    void savePath(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonPaths = Unirest.get("http://localhost:8090/paths").asString().getBody();

        try {
            paths = om.readerForListOf(Class.class).readValue(jsonPaths);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert paths != null;
        for (Path path : paths) {
            viewPath.getItems().add(path.getPathNumber() + ": " + path.getName());
        }

    }
}
