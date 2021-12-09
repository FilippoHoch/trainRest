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

public class LinkManagerController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    List<Path> paths = new ArrayList<>();
    List<Station> stations = new ArrayList<>();
    List<Link> links = new ArrayList<>();

    @FXML
    private Button deleteLinkButton;

    @FXML
    private Label labelArriveStation;

    @FXML
    private Label labelLinkCost;

    @FXML
    private Label labelPathNumber;

    @FXML
    private Label labelStartingStation;

    @FXML
    private TextField linkCost;

    @FXML
    private Button saveLinkButton;

    @FXML
    private ChoiceBox<String> viewLink;

    @FXML
    private ChoiceBox<String> startingStationLink;

    @FXML
    private ChoiceBox<String> pathNumberLink;

    @FXML
    private ChoiceBox<String> arriveStationLink;

    @FXML
    void cancelEditLink(ActionEvent event) {
        // TODO: 08/12/2021 change to better self stage declaration
        Stage stage = (Stage) linkCost.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteLink(ActionEvent event) {

    }

    @FXML
    void newLink(ActionEvent event) {

    }

    @FXML
    void saveLink(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonLinks = Unirest.get("http://localhost:8090/links").asString().getBody();
        String jsonPaths = Unirest.get("http://localhost:8090/paths").asString().getBody();
        String jsonStations = Unirest.get("http://localhost:8090/stations").asString().getBody();

        try {
            links = om.readerForListOf(Class.class).readValue(jsonLinks);
            paths = om.readerForListOf(Class.class).readValue(jsonPaths);
            stations = om.readerForListOf(Class.class).readValue(jsonStations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        assert links != null;
        for (Link link : links) {
            viewLink.getItems().add("Da " + link.getStartStation() + " a " + link.getEndStation());
        }

        assert paths != null;
        for (Path path : paths) {
            pathNumberLink.getItems().add(path.getPathNumber() + ": " + path.getName());
        }

        assert stations != null;
        for (Station station : stations) {
            startingStationLink.getItems().add(station.getName());
            arriveStationLink.getItems().add(station.getName());
        }
    }
}
