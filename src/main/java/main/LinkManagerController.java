package main;

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
        Stage stage = (Stage) linkCost.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteLink(ActionEvent event) {
        Unirest.delete("http://localhost:8090/removeLink?elementNumber=" + viewLink.getSelectionModel().getSelectedIndex()).asString().getBody();
        Stage stage = (Stage) linkCost.getScene().getWindow();
        stage.close();
    }

    @FXML
    void newLink(ActionEvent event) {
        linkCost.setDisable(false);
        pathNumberLink.setDisable(false);
        arriveStationLink.setDisable(false);
        startingStationLink.setDisable(false);
        deleteLinkButton.setDisable(true);
        saveLinkButton.setDisable(false);
        labelLinkCost.setDisable(false);
        labelArriveStation.setDisable(false);
        labelPathNumber.setDisable(false);
        labelStartingStation.setDisable(false);
    }

    @FXML
    void saveLink(ActionEvent event) {
        if (deleteLinkButton.isDisable()) {
            if (linkCost.getText() == "")
                return;
            if (pathNumberLink.getValue() == null)
                return;
            if (startingStationLink.getValue() == null)
                return;
            if (arriveStationLink.getValue() == null)
                return;
            String url = String.format("http://localhost:8090/addLink?cost=%s&startStation=%s&endStation=%s&pathNumber=%s", linkCost.getText(), stations.get(startingStationLink.getSelectionModel().getSelectedIndex()).getId(), stations.get(arriveStationLink.getSelectionModel().getSelectedIndex()).getId(), paths.get(pathNumberLink.getSelectionModel().getSelectedIndex()).getPathNumber());
            Unirest.post(url).asString().getBody();
        } else {
            String url = "http://localhost:8090/updateLink?elementNumber=" + viewLink.getSelectionModel().getSelectedIndex();
            if (!linkCost.getText().equalsIgnoreCase(String.valueOf(stations.get(viewLink.getSelectionModel().getSelectedIndex()).getName())))
                url = url.concat("&cost=" + linkCost.getText());
            if (stations.get(startingStationLink.getSelectionModel().getSelectedIndex()).getId() != links.get(viewLink.getSelectionModel().getSelectedIndex()).getStartStation())
                url = url.concat("&startStation=" + stations.get(startingStationLink.getSelectionModel().getSelectedIndex()).getId());
            if (stations.get(arriveStationLink.getSelectionModel().getSelectedIndex()).getId() != links.get(viewLink.getSelectionModel().getSelectedIndex()).getEndStation())
                url = url.concat("&endStation=" + stations.get(arriveStationLink.getSelectionModel().getSelectedIndex()).getId());
            if (paths.get(pathNumberLink.getSelectionModel().getSelectedIndex()).getPathNumber() != links.get(viewLink.getSelectionModel().getSelectedIndex()).getPathNumber())
                url = url.concat("&pathNumber=" + paths.get(pathNumberLink.getSelectionModel().getSelectedIndex()).getPathNumber());
            Unirest.put(url).asString().getBody();
        }
        Stage stage = (Stage) linkCost.getScene().getWindow();
        stage.close();
    }

    void editPath() {
        linkCost.setDisable(false);
        pathNumberLink.setDisable(false);
        arriveStationLink.setDisable(false);
        startingStationLink.setDisable(false);
        deleteLinkButton.setDisable(false);
        saveLinkButton.setDisable(false);
        labelLinkCost.setDisable(false);
        labelArriveStation.setDisable(false);
        labelPathNumber.setDisable(false);
        labelStartingStation.setDisable(false);
        linkCost.setText(String.valueOf(links.get(viewLink.getSelectionModel().getSelectedIndex()).getCost()));
        pathNumberLink.setValue(String.valueOf(links.get(viewLink.getSelectionModel().getSelectedIndex()).getPathNumber()));
        startingStationLink.setValue(String.valueOf(links.get(viewLink.getSelectionModel().getSelectedIndex()).getStartStation()));
        arriveStationLink.setValue(String.valueOf(links.get(viewLink.getSelectionModel().getSelectedIndex()).getEndStation()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonLinks = Unirest.get("http://localhost:8090/links").asString().getBody();
        String jsonPaths = Unirest.get("http://localhost:8090/paths").asString().getBody();
        String jsonStations = Unirest.get("http://localhost:8090/stations").asString().getBody();

        try {
            links = om.readerForListOf(Link.class).readValue(jsonLinks);
            paths = om.readerForListOf(Path.class).readValue(jsonPaths);
            stations = om.readerForListOf(Station.class).readValue(jsonStations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        assert links != null;
        for (Link link : links) {
            viewLink.getItems().add("Da " + link.getStartStation() + " a " + link.getEndStation() + " - " + link.getPathNumber());
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

        viewLink.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                editPath();
            }
        });
    }
}
