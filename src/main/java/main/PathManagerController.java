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
        Stage stage = (Stage) pathArriveTimeHour.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deletePath(ActionEvent event) {
        Unirest.delete("http://localhost:8090/removePath?elementNumber=" + viewPath.getSelectionModel().getSelectedIndex()).asString().getBody();
        Stage stage = (Stage) pathArriveTimeHour.getScene().getWindow();
        stage.close();

    }

    @FXML
    void newPath(ActionEvent event) {
        pathName.setDisable(false);
        pathArriveTimeHour.setDisable(false);
        pathSeats.setDisable(false);
        pathArriveTimeMinutes.setDisable(false);
        pathDepartureTimeHour.setDisable(false);
        deletePathButton.setDisable(true);
        labelArrivingPath.setDisable(false);
        pathDepartureTimeMinutes.setDisable(false);
        labelStartingPath.setDisable(false);
        savePathButton.setDisable(false);
        labelPathName.setDisable(false);
        labelPathSeats.setDisable(false);
        labelArrivingPathHours.setDisable(false);
        labelArrivingPathMinutes.setDisable(false);
        labelStartingPathHours.setDisable(false);
        labelStartingPathMinutes.setDisable(false);
        labelTwoPoints1.setDisable(false);
        labelTwoPoints2.setDisable(false);
    }

    @FXML
    void savePath(ActionEvent event) {
        checkParamets();
        if (deletePathButton.isDisable()) {
            if (pathSeats.getText() == "")
                return;
            if (pathName.getText() == "")
                return;

            Long departureTime = Utility.convertTime(Integer.parseInt(pathDepartureTimeHour.getText()), Integer.parseInt(pathDepartureTimeMinutes.getText())).getTime();
            String url = String.format("http://localhost:8090/addPath?pathName=%s&startDate=%s&endDate=%s&seats=%s", pathName.getText(), departureTime, Utility.convertTime(Integer.parseInt(pathArriveTimeHour.getText()), Integer.parseInt(pathArriveTimeMinutes.getText())).getTime(), pathSeats.getText());
            Unirest.post(url).asString().getBody();
        } else {
            List<String> tempDepartureTime = Utility.dateToList(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getDepartureTime());
            List<String> tempArriveTime = Utility.dateToList(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getArrivalTime());
            String url = "http://localhost:8090/updatePath?elementNumber=" + viewPath.getSelectionModel().getSelectedIndex();
            if (!pathName.getText().equalsIgnoreCase(String.valueOf(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getName())))
                url = url.concat("&pathName=" + pathName.getText());
            if (!pathArriveTimeMinutes.getText().equalsIgnoreCase(tempArriveTime.get(4)) || !pathArriveTimeHour.getText().equalsIgnoreCase(tempArriveTime.get(3)))
                url = url.concat("&endDate=" + Utility.convertTime(Integer.parseInt(pathArriveTimeHour.getText()), Integer.parseInt(pathArriveTimeMinutes.getText())).getTime());
            if (!pathDepartureTimeMinutes.getText().equalsIgnoreCase(tempDepartureTime.get(4)) || !pathDepartureTimeHour.getText().equalsIgnoreCase(tempDepartureTime.get(3)))
                url = url.concat("&startDate=" + Utility.convertTime(Integer.parseInt(pathDepartureTimeHour.getText()), Integer.parseInt(pathDepartureTimeMinutes.getText())).getTime());
            if (!pathSeats.getText().equalsIgnoreCase(String.valueOf(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getSeats())))
                url = url.concat("&seats=" + pathSeats.getText());
            Unirest.put(url).asString().getBody();
        }
        Stage stage = (Stage) pathArriveTimeHour.getScene().getWindow();
        stage.close();

    }

    void editPath() {
        List<String> tempDepartureTime = Utility.dateToList(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getDepartureTime());
        List<String> tempArriveTime = Utility.dateToList(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getArrivalTime());
        pathName.setDisable(false);
        pathArriveTimeHour.setDisable(false);
        pathSeats.setDisable(false);
        pathArriveTimeMinutes.setDisable(false);
        pathDepartureTimeHour.setDisable(false);
        deletePathButton.setDisable(false);
        labelArrivingPath.setDisable(false);
        pathDepartureTimeMinutes.setDisable(false);
        labelStartingPath.setDisable(false);
        savePathButton.setDisable(false);
        labelPathName.setDisable(false);
        labelPathSeats.setDisable(false);
        labelArrivingPathHours.setDisable(false);
        labelArrivingPathMinutes.setDisable(false);
        labelStartingPathHours.setDisable(false);
        labelStartingPathMinutes.setDisable(false);
        labelTwoPoints1.setDisable(false);
        labelTwoPoints2.setDisable(false);
        pathName.setText(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getName());
        pathSeats.setText(String.valueOf(paths.get(viewPath.getSelectionModel().getSelectedIndex()).getSeats()));
        pathDepartureTimeHour.setText(tempDepartureTime.get(3));
        pathDepartureTimeMinutes.setText(tempDepartureTime.get(4));
        pathArriveTimeHour.setText(tempArriveTime.get(3));
        pathArriveTimeMinutes.setText(tempArriveTime.get(4));
    }

    boolean checkParamets() {
        if (pathDepartureTimeHour.getText().equals("")) {
            pathDepartureTimeHour.setText("00");
        }
        if (pathDepartureTimeMinutes.getText().equals("")) {
            pathDepartureTimeMinutes.setText("00");
        }
        if (pathArriveTimeHour.getText().equals("")) {
            pathArriveTimeHour.setText("00");
        }
        if (pathArriveTimeMinutes.getText().equals("")) {
            pathArriveTimeMinutes.setText("00");
        }
        if (pathDepartureTimeHour.getText().length() < 2) {
            pathDepartureTimeHour.setText("0" + pathDepartureTimeHour.getText());
        }
        if (pathDepartureTimeMinutes.getText().length() < 2) {
            pathDepartureTimeMinutes.setText("0" + pathDepartureTimeMinutes.getText());
        }
        if (pathArriveTimeHour.getText().length() < 2) {
            pathArriveTimeHour.setText("0" + pathArriveTimeHour.getText());
        }
        if (pathArriveTimeMinutes.getText().length() < 2) {
            pathArriveTimeMinutes.setText("0" + pathArriveTimeMinutes.getText());
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonPaths = Unirest.get("http://localhost:8090/paths").asString().getBody();

        try {
            paths = om.readerForListOf(Path.class).readValue(jsonPaths);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert paths != null;
        for (Path path : paths) {
            viewPath.getItems().add(path.getPathNumber() + ": " + path.getName());
        }

        viewPath.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                editPath();
            }
        });

    }
}
