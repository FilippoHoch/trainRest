package main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kong.unirest.Unirest;
import main.Station;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class StationManagerController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    List<Station> stations = new ArrayList<>();

    @FXML
    private Button deleteStationButton;

    @FXML
    private Label labelStationName;

    @FXML
    private Button saveStationButton;

    @FXML
    private TextField stationName;

    @FXML
    private ChoiceBox<String> viewStation;

    @FXML
    void cancelEditStation(ActionEvent event) {

        Stage stage = (Stage) stationName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteStation(ActionEvent event) {
        Unirest.delete("http://localhost:8090/removeStation?elementNumber=" + viewStation.getSelectionModel().getSelectedIndex()).asString().getBody();

        Stage stage = (Stage) stationName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void newStation(ActionEvent event) {
        stationName.setDisable(false);
        deleteStationButton.setDisable(true);
        labelStationName.setDisable(false);
        saveStationButton.setDisable(false);
    }

    @FXML
    void saveStation(ActionEvent event) {
        if (deleteStationButton.isDisable()) {
            if (Objects.equals(stationName.getText(), ""))
                return;
            String url = String.format("http://localhost:8090/addStation?stationName=%s", stationName.getText());
            Unirest.post(url).asString().getBody();
        } else {
            String url = "http://localhost:8090/updateStation?elementNumber=" + viewStation.getSelectionModel().getSelectedIndex();
            if (!stationName.getText().equalsIgnoreCase(String.valueOf(stations.get(viewStation.getSelectionModel().getSelectedIndex()).getName())))
                url = url.concat("&stationName=" + stationName.getText());
            Unirest.put(url).asString().getBody();
        }

        Stage stage = (Stage) stationName.getScene().getWindow();
        stage.close();
    }

    void editPath() {
        stationName.setDisable(false);
        deleteStationButton.setDisable(false);
        labelStationName.setDisable(false);
        saveStationButton.setDisable(false);
        stationName.setText(stations.get(viewStation.getSelectionModel().getSelectedIndex()).getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonStations = Unirest.get("http://localhost:8090/stations").asString().getBody();

        try {
            stations = om.readerForListOf(Station.class).readValue(jsonStations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert stations != null;
        for (Station station : stations) {
            viewStation.getItems().add(station.getName());
        }

        viewStation.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> editPath());

    }
}
