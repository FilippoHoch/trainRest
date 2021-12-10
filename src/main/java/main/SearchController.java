package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class SearchController implements Initializable {

    ObjectMapper om = new ObjectMapper();
    List<Station> stations = new ArrayList<>();
    List<Class> classes = new ArrayList<>();


    @FXML // fx:id="arriveTimeHour"
    private TextField arriveTimeHour; // Value injected by FXMLLoader

    @FXML // fx:id="currentPrice"
    private Label currentPrice; // Value injected by FXMLLoader

    @FXML // fx:id="arriveTimeMinutes"
    private TextField arriveTimeMinutes; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="chosenClass"
    private ChoiceBox chosenClass; // Value injected by FXMLLoader

    @FXML // fx:id="travelDate"
    private DatePicker travelDate; // Value injected by FXMLLoader

    @FXML // fx:id="departureTimeHour"
    private TextField departureTimeHour; // Value injected by FXMLLoader

    @FXML // fx:id="departureTimeMinutes"
    private TextField departureTimeMinutes; // Value injected by FXMLLoader

    @FXML // fx:id="destinationStation"
    private ChoiceBox destinationStation; // Value injected by FXMLLoader

    @FXML // fx:id="disponibilityPrice"
    private Slider disponibilityPrice; // Value injected by FXMLLoader

    @FXML // fx:id="errorMessage"
    private Label errorMessage; // Value injected by FXMLLoader

    @FXML // fx:id="find"
    private Button find; // Value injected by FXMLLoader

    @FXML // fx:id="startingStation"
    private ChoiceBox startingStation; // Value injected by FXMLLoader


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        disponibilityPrice.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                currentPrice.setText(Double.toString(Utility.round(disponibilityPrice.getValue(), 2)));
            }
        });

        String jsonStations = Unirest.get("http://localhost:8090/stations").asString().getBody();
        String jsonClasses = Unirest.get("http://localhost:8090/classes").asString().getBody();


        try {
            stations = om.readerForListOf(Station.class).readValue(jsonStations);
            classes = om.readerForListOf(Class.class).readValue(jsonClasses);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert stations != null;
        for (Station station : stations) {
            startingStation.getItems().add(station.getName());
            destinationStation.getItems().add(station.getName());
        }

        assert classes != null;
        for (Class aClass : classes) {
            chosenClass.getItems().add(aClass.getClassNumber());
        }

    }

    @FXML
    void cancelSearchTickets(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void findTickets(ActionEvent event) {
        if (checkParameters()) {
            Stage stage = (Stage) cancel.getScene().getWindow();

            MainController.getInstance().setSearch(true);
            MainController.getInstance().setSelectedArriveTime(Utility.stringToDate(travelDate.getValue() + " " + arriveTimeHour.getText() + ":" + arriveTimeMinutes.getText()));
            MainController.getInstance().setSelectedDisponibilityPrice(Utility.round(disponibilityPrice.getValue(), 2));
            MainController.getInstance().setSelectedChosenClass((Integer) chosenClass.getValue());
            MainController.getInstance().setSelectedDepartureTime(Utility.stringToDate(travelDate.getValue() + " " + departureTimeHour.getText() + ":" + departureTimeMinutes.getText()));
            for (Station station : stations) {
                if (station.getName().equalsIgnoreCase((String) destinationStation.getValue())) {
                    MainController.getInstance().setSelectedDestinationStation(stations.indexOf(station));
                    break;
                }
            }
            for (Station station : stations) {
                if (station.getName().equalsIgnoreCase((String) startingStation.getValue())) {
                    MainController.getInstance().setSelectedStartingStation(stations.indexOf(station));
                    break;
                }
            }
            stage.close();
        }

    }

    /**
     * this function checks if all the argument have been inserted correctly, if not, it changes them or completes them
     *
     * @return returns all the argument correctly
     */
    public boolean checkParameters() {
        errorMessage.setText("");
        if (travelDate.getValue() == null) {
            errorMessage.setText("Missing argument");
            return false;
        }
        if (chosenClass.getValue() == null) {
            errorMessage.setText("Missing argument");
            return false;
        }
        if (disponibilityPrice.getValue() == 0) {
            disponibilityPrice.setValue(100.0);
        }
        if (departureTimeHour.getText().equals("")) {
            departureTimeHour.setText("00");
        }
        if (departureTimeMinutes.getText().equals("")) {
            departureTimeMinutes.setText("00");
        }
        if (arriveTimeMinutes.getText().equals("")) {
            arriveTimeMinutes.setText("00");
        }
        if (arriveTimeHour.getText().equals("")) {
            arriveTimeHour.setText("00");
        }
        if (departureTimeHour.getText().length() < 2) {
            departureTimeHour.setText("0" + departureTimeHour.getText());
        }
        if (arriveTimeHour.getText().length() < 2) {
            arriveTimeHour.setText("0" + arriveTimeHour.getText());
        }
        if (departureTimeMinutes.getText().length() < 2) {
            departureTimeMinutes.setText("0" + departureTimeMinutes.getText());
        }
        if (arriveTimeMinutes.getText().length() < 2) {
            arriveTimeMinutes.setText("0" + arriveTimeMinutes.getText());
        }
        if (startingStation.getValue() == null)
            return false;
        if (destinationStation.getValue() == null)
            return false;
        if (chosenClass.getValue() == null)
            return false;
        return true;
    }
}
