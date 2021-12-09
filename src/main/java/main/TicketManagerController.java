package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class TicketManagerController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    List<Ticket> tickets = new ArrayList<>();
    List<Class> classes = new ArrayList<>();

    @FXML
    private Button deleteTicketButton;

    @FXML
    private Label labelTicketClass;

    @FXML
    private Label labelTicketDay;

    @FXML
    private Label labelTicketName;

    @FXML
    private Button saveTicketButton;

    @FXML
    private ChoiceBox<String> ticketClass;

    @FXML
    private DatePicker ticketDay;

    @FXML
    private TextField ticketName;

    @FXML
    private ChoiceBox<String> viewTicket;

    @FXML
    void cancelEditTicket(ActionEvent event) {
        // TODO: 08/12/2021 change to better self stage declaration
        Stage stage = (Stage) ticketName.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteTicket(ActionEvent event) {

    }

    @FXML
    void newTicket(ActionEvent event) {

    }

    @FXML
    void saveTicket(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonTickets = Unirest.get("http://localhost:8090/tickets").asString().getBody();
        String jsonClasses = Unirest.get("http://localhost:8090/classes").asString().getBody();

        try {
            tickets = om.readerForListOf(Class.class).readValue(jsonTickets);
            classes = om.readerForListOf(Class.class).readValue(jsonClasses);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert tickets != null;
        for (Ticket ticket : tickets) {
            viewTicket.getItems().add("linea: " + ticket.getRoadPath() + " alle " + ticket.getDepartureDate() +" da " + ticket.getDepartureStation());
        }

        assert classes != null;
        for (Class aClass : classes) {
            ticketClass.getItems().add(String.valueOf(aClass.getClassNumber()));
        }

    }
}
