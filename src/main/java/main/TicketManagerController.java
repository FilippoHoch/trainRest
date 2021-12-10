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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketManagerController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    List<Ticket> tickets = new ArrayList<>();
    List<Class> classes = new ArrayList<>();
    List<Path> paths = new ArrayList<>();

    @FXML
    private Button deleteTicketButton;

    @FXML
    private Label labelTicketClass;

    @FXML
    private Label labelTicketDay;

    @FXML
    private Label labelRoadPath;

    @FXML
    private Button saveTicketButton;

    @FXML
    private ChoiceBox<String> ticketClass;

    @FXML
    private DatePicker ticketDay;

    @FXML
    private ChoiceBox<String> ticketRoadPath;

    @FXML
    private ChoiceBox<String> viewTicket;

    @FXML
    void cancelEditTicket(ActionEvent event) {
        
        Stage stage = (Stage) labelRoadPath.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteTicket(ActionEvent event) {
        Unirest.delete("http://localhost:8090/removeTicket?elementNumber=" + viewTicket.getSelectionModel().getSelectedIndex()).asString().getBody();
        
        Stage stage = (Stage) labelRoadPath.getScene().getWindow();
        stage.close();
    }

    @FXML
    void newTicket(ActionEvent event) {
        ticketRoadPath.setDisable(false);
        ticketClass.setDisable(false);
        ticketDay.setDisable(false);
        deleteTicketButton.setDisable(true);
        saveTicketButton.setDisable(false);
        labelTicketClass.setDisable(false);
        labelTicketDay.setDisable(false);
        labelRoadPath.setDisable(false);
    }

    @FXML
    void saveTicket(ActionEvent event) {
        if (deleteTicketButton.isDisable()) {
            if (ticketDay.getValue() == null)
                return;
            if (ticketClass.getValue() == null)
                return;
            if (ticketRoadPath.getValue() == null)
                return;
            String url = String.format("http://localhost:8090/addTicket?roadPath=%s&day=%s&classNumber=%s", paths.get(ticketRoadPath.getSelectionModel().getSelectedIndex()).getPathNumber(), Utility.stringToDateTime(ticketDay.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "-00:00").getTime(), classes.get(ticketClass.getSelectionModel().getSelectedIndex()).getClassNumber() - 1);
            Unirest.post(url).asString().getBody();
        } else {
            String url = "http://localhost:8090/updateTicket?elementNumber=" + viewTicket.getSelectionModel().getSelectedIndex();
            if (paths.get(ticketRoadPath.getSelectionModel().getSelectedIndex()).getPathNumber() != tickets.get(viewTicket.getSelectionModel().getSelectedIndex()).getRoadPath())
                url = url.concat("&roadPath=" + paths.get(ticketRoadPath.getSelectionModel().getSelectedIndex()).getPathNumber());
            if ((Utility.stringToDateTime(ticketDay.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "-00:00")).getTime() != tickets.get(viewTicket.getSelectionModel().getSelectedIndex()).getDay().getTime())
                url = url.concat("&day=" + Utility.stringToDateTime(ticketDay.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "-00:00").getTime());
            if (classes.get(ticketClass.getSelectionModel().getSelectedIndex()).getClassNumber() != tickets.get(viewTicket.getSelectionModel().getSelectedIndex()).getaClass().getClassNumber())
                url = url.concat("&classNumber=" + classes.get(ticketClass.getSelectionModel().getSelectedIndex()).getClassNumber());
            Unirest.put(url).asString().getBody();
        }


        
        Stage stage = (Stage) labelRoadPath.getScene().getWindow();
        stage.close();
    }

    void editPath() {
        ticketRoadPath.setDisable(false);
        ticketClass.setDisable(false);
        ticketDay.setDisable(false);
        deleteTicketButton.setDisable(false);
        saveTicketButton.setDisable(false);
        labelTicketClass.setDisable(false);
        labelTicketDay.setDisable(false);
        labelRoadPath.setDisable(false);
        ticketClass.setValue(String.valueOf(tickets.get(viewTicket.getSelectionModel().getSelectedIndex()).getaClass().getClassNumber()));
        ticketRoadPath.setValue(String.valueOf(tickets.get(viewTicket.getSelectionModel().getSelectedIndex()).getRoadPath()));
        ticketDay.setValue(Utility.convertToLocalDateViaInstant(tickets.get(viewTicket.getSelectionModel().getSelectedIndex()).getDay()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String jsonTickets = Unirest.get("http://localhost:8090/tickets").asString().getBody();
        String jsonClasses = Unirest.get("http://localhost:8090/classes").asString().getBody();
        String jsonPaths = Unirest.get("http://localhost:8090/paths").asString().getBody();

        try {
            tickets = om.readerForListOf(Ticket.class).readValue(jsonTickets);
            classes = om.readerForListOf(Class.class).readValue(jsonClasses);
            paths = om.readerForListOf(Path.class).readValue(jsonPaths);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert tickets != null;
        for (Ticket ticket : tickets) {
            List<String> day = Utility.dateToList(ticket.getDay());
            viewTicket.getItems().add("linea: " + ticket.getRoadPath() + ", Classe:" + ticket.getaClass().getClassNumber() + ", del " + day.get(0) + "/" + day.get(1) + "/" + day.get(2));
        }

        assert classes != null;
        for (Class aClass : classes) {
            ticketClass.getItems().add(String.valueOf(aClass.getClassNumber()));
        }

        assert paths != null;
        for (Path path : paths) {
            ticketRoadPath.getItems().add(String.valueOf(path.getPathNumber()));
        }

        viewTicket.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                editPath();
            }
        });

    }
}
