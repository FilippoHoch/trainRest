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
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
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
    void cancelEditClass(ActionEvent event) {
        // TODO: 08/12/2021 change to better self stage declaration
        Stage stage = (Stage) classMultiplier.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteClass(ActionEvent event) {

    }

    @FXML
    void newClass(ActionEvent event) {
        className.setDisable(false);
        classMultiplier.setDisable(false);
        viewClass.setDisable(true);
    }

    @FXML
    void editClass(ActionEvent event){
        className.setDisable(false);
        classMultiplier.setDisable(false);
        saveClassButton.setDisable(false);
        deleteClassButton.setDisable(false);
        className.setText(viewClass.getValue());
        classMultiplier.setText(String.valueOf(classes.get(Integer.parseInt(viewClass.getValue())).getClassNumber()));
    }


    @FXML
    void saveClass(ActionEvent event) {
        String url = "http://localhost:8090/updateClass?elementNumber=" + viewClass.getId();
//        if()
        Unirest.put(url).asString().getBody();
        // TODO: 08/12/2021 change to better self stage declaration
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
    }
}
