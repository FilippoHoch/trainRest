/**
 * Sample Skeleton for 'ticket.fxml' Controller Class
 */

package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TicketController implements Initializable {

    List<Path> pathList = new ArrayList<>();
    ObjectMapper om = new ObjectMapper();
    Path selectedPath;

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="listViewStation"
    private ListView<String> listViewStation; // Value injected by FXMLLoader

    // TODO: 08/12/2021 Add logo of the campaign of the train
    @FXML // fx:id="logo"
    private ImageView logo; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private Label price; // Value injected by FXMLLoader

    @FXML // fx:id="reserve"
    private Button reserve; // Value injected by FXMLLoader

    @FXML // fx:id="ticketArriveAt"
    private Label ticketArriveAt; // Value injected by FXMLLoader

    @FXML // fx:id="ticketClass"
    private Label ticketClass; // Value injected by FXMLLoader

    @FXML // fx:id="ticketDepartureAt"
    private Label ticketDepartureAt; // Value injected by FXMLLoader


    private static TicketController instance;

    public static TicketController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

    }

    @FXML
    public void setParameters(Ticket ticket, Path path, List<Path> paths) {
        pathList = paths;
        selectedPath = path;
        boolean first = true;
        int time = 0;
        Date startingDate = ticket.getDepartureDate();

        for (int i = 0; i < path.getLinks().size(); i++) {
            for (Station station : path.getStations()) {

                if (station.getId() == path.getLinks().get(i).getStartStation()) {
                    if (first) {
                        listViewStation.getItems().add(station.getName().concat(" - " + Utility.timeToString(startingDate)));
                        first = false;
                        time += path.getLinks().get(i).getCost();
                    } else {
                        time += path.getLinks().get(i).getCost();
                        Date nextStop = new Date(startingDate.getTime() + time * 60000L);
                        listViewStation.getItems().add(station.getName().concat(" - " + Utility.timeToString(nextStop)));
                    }

                    price.setText(String.valueOf(ticket.getTotalCost()));
                    ticketArriveAt.setText(Utility.dateToString(ticket.getArriveDate()));
                    ticketClass.setText(String.valueOf(ticket.getaClass().getClassNumber()));
                    ticketDepartureAt.setText(Utility.dateToString(ticket.getDepartureDate()));
                }
            }

        }

    }

    @FXML
    void cancelTicketSelection(ActionEvent event) {
        // TODO: 08/12/2021 change to better self stage declaration
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    // TODO: 08/12/2021 decrement number of seats
    @FXML
    void reserveTicket(ActionEvent event) {
        if (selectedPath.getSeats() > 0) {
            Unirest.put("http://localhost:8090/updatePath?elementNumber=" + pathList.indexOf(selectedPath) + "&seats=" + (selectedPath.getSeats() - 1)).asString().getBody();
            // TODO: 08/12/2021 change to better self stage declaration
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();

        }
    }

}
