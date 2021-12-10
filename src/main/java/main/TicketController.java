/**
 * Sample Skeleton for 'ticket.fxml' Controller Class
 */

package main;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import main.managers.LinkManager;
import main.managers.StationManager;

import java.net.URL;
import java.util.*;

public class TicketController implements Initializable {

    List<Path> pathList = new ArrayList<>();
    ObjectMapper om = new ObjectMapper();
    Path selectedPath;

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="listViewStation"
    private ListView<String> listViewStation; // Value injected by FXMLLoader

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
    public void setParameters(Ticket ticket, Path path) {
        selectedPath = path;
        int time = 0;
        Date startingDate = new Date(path.getDepartureTime().getTime() - 3600000L);

        LinkManager linkManager = new LinkManager();
        StationManager stationManager = new StationManager();
        String jsonLinks = Unirest.get("http://localhost:8090/links").asString().getBody();
        String jsonStation = Unirest.get("http://localhost:8090/stations").asString().getBody();


        try {
            linkManager.addAllLinks(om.readerForListOf(Link.class).readValue(jsonLinks));
            stationManager.addAllStations(om.readerForListOf(Station.class).readValue(jsonStation));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Link> linkList = linkManager.getLinks(path.getPathNumber());
        listViewStation.getItems().add(stationManager.getAllStations().get(0).getName().concat(" - " + Utility.timeToString(startingDate)));
        price.setText(String.valueOf(ticket.getTotalCost()));
        ticketArriveAt.setText(Utility.dateToString(ticket.getArriveDate()));
        ticketClass.setText(String.valueOf(ticket.getaClass().getClassNumber()));
        ticketDepartureAt.setText(Utility.dateToString(ticket.getDepartureDate()));


        for (int i = 0; i < linkList.size(); i++){
            int id = 0;
            for (Station station : stationManager.getAllStations()) {
                if (linkList.get(i).getEndStation() == station.getId()) {
                    id = station.getId();
                    break;
                }
            }
            time += linkList.get(i).getCost();
            Date nextStop = new Date(startingDate.getTime() + (time * 60000L));
            listViewStation.getItems().add(stationManager.getAllStations().get(id).getName().concat(" - " + Utility.timeToString(nextStop)));
            price.setText(String.valueOf(ticket.getTotalCost()));
            ticketArriveAt.setText(Utility.dateToString(ticket.getArriveDate()));
            ticketClass.setText(String.valueOf(ticket.getaClass().getClassNumber()));
            ticketDepartureAt.setText(Utility.dateToString(ticket.getDepartureDate()));
            if (i == linkList.size() - 1)
                return;

        }

            

/*
                if (station.getId() == path.getLinks().get(i).getStartStation()) {
                    System.out.println(station);
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
 */
            
        

    }

    @FXML
    void cancelTicketSelection(ActionEvent event) {
        
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void reserveTicket(ActionEvent event) {
        if (selectedPath.getSeats() > 0) {
            Unirest.put("http://localhost:8090/updatePath?elementNumber=" + pathList.indexOf(selectedPath) + "&seats=" + (selectedPath.getSeats() - 1)).asString().getBody();
            
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();

        }
    }

}
