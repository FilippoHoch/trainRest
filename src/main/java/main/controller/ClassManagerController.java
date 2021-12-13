package main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kong.unirest.Unirest;
import main.Class;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClassManagerController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    List<Class> classes = new ArrayList<>();

    @FXML
    private TextField classMultiplier;

    @FXML
    private TextField className;

    @FXML
    private Button deleteClassButton;

    @FXML
    private Label labelClassNumber;

    @FXML
    private Label labelMultiplier;

    @FXML
    private Button saveClassButton;

    @FXML
    private ChoiceBox<String> viewClass;

    @FXML
    void cancelEditClass() {
        Stage stage = (Stage) classMultiplier.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteClass() {
        Unirest.delete("http://localhost:8090/removeClass?elementNumber=" + viewClass.getSelectionModel().getSelectedIndex()).asString().getBody();
        Stage stage = (Stage) classMultiplier.getScene().getWindow();
        stage.close();
    }

    @FXML
    void newClass() {
        className.setDisable(false);
        classMultiplier.setDisable(false);
        saveClassButton.setDisable(false);
        labelMultiplier.setDisable(false);
        labelClassNumber.setDisable(false);
    }


    void editClass() {
        className.setDisable(false);
        classMultiplier.setDisable(false);
        saveClassButton.setDisable(false);
        deleteClassButton.setDisable(false);
        labelClassNumber.setDisable(false);
        labelMultiplier.setDisable(false);
        className.setText(String.valueOf(classes.get(viewClass.getSelectionModel().getSelectedIndex()).getClassNumber()));
        classMultiplier.setText(String.valueOf(classes.get(viewClass.getSelectionModel().getSelectedIndex()).getMultiplier()));

    }


    @FXML
    void saveClass() {
        if (deleteClassButton.isDisable()) {
            if (Objects.equals(className.getText(), ""))
                return;
            if (Objects.equals(classMultiplier.getText(), ""))
                return;
            String url = String.format("http://localhost:8090/addClass?classNumber=%s&multiplier=%s", className.getText(), classMultiplier.getText());
            Unirest.post(url).asString().getBody();
        } else {
            String url = "http://localhost:8090/updateClass?elementNumber=" + viewClass.getSelectionModel().getSelectedIndex();
            if (!className.getText().equalsIgnoreCase(String.valueOf(classes.get(viewClass.getSelectionModel().getSelectedIndex()).getClassNumber())))
                url = url.concat("&className=" + className.getText());
            if (!classMultiplier.getText().equalsIgnoreCase(String.valueOf(classes.get(viewClass.getSelectionModel().getSelectedIndex()).getMultiplier())))
                url = url.concat("&multiplier=" + classMultiplier.getText());
            Unirest.put(url).asString().getBody();
        }
        Stage stage = (Stage) classMultiplier.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonClasses = Unirest.get("http://localhost:8090/classes").asString().getBody();

        try {
            classes = om.readerForListOf(Class.class).readValue(jsonClasses);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert classes != null;
        for (Class aClass : classes) {
            viewClass.getItems().add(String.valueOf(aClass.getClassNumber()));
        }

        viewClass.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> editClass());
    }
}
