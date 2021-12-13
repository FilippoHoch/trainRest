package main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kong.unirest.Unirest;
import main.*;
import main.managers.LinkManager;
import main.managers.PathFinder;
import main.managers.StationManager;

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

        int startStationId = -1, arriveStationId = -1;

        for (Station station : stationManager.getAllStations()) {
            if (station.getName().equalsIgnoreCase(ticket.getDepartureStation()))
                startStationId = station.getId();
            if (station.getName().equalsIgnoreCase(ticket.getArriveStation()))
                arriveStationId = station.getId();
        }

        PathFinder finder = new PathFinder();
        List<Link> linkList = linkManager.getLinks(path.getPathNumber());
        price.setText(String.valueOf(ticket.getTotalCost()));
        ticketArriveAt.setText(Utility.dateToString(ticket.getArriveDate()));
        ticketClass.setText(String.valueOf(ticket.getaClass().getClassNumber()));
        ticketDepartureAt.setText(Utility.dateToString(ticket.getDepartureDate()));

//        System.out.println("startStation: " + startStationId + ", endStation: " + arriveStationId + ", path: " + path.getPathNumber());
        List<Station> path1 = finder.getPath(startStationId, arriveStationId, path.getPathNumber(), stationManager.getAllStations(), linkManager.getAllLinks());
        Station previousStation = null;
        for (Station station : path1) {
            int cost = 0;
            if (station != path1.get(0)) {
                cost = linkManager.getLinks(previousStation, station, path.getPathNumber()).get(0).getCost();
            }
            time += cost;
            Date nextStop = new Date(startingDate.getTime() + (time * 60000L));
            listViewStation.getItems().add(station.getName().concat(" - " + Utility.timeToString(nextStop)));
            previousStation = station;
        }
        price.setText(String.valueOf(ticket.getTotalCost()));
        ticketArriveAt.setText(Utility.dateToString(ticket.getArriveDate()));
        ticketClass.setText(String.valueOf(ticket.getaClass().getClassNumber()));
        ticketDepartureAt.setText(Utility.dateToString(ticket.getDepartureDate()));

    }

    @FXML
    void cancelTicketSelection() {

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void reserveTicket() {
        if (selectedPath.getSeats() > 0) {
            Unirest.put("http://localhost:8090/updatePath?elementNumber=" + pathList.indexOf(selectedPath) + "&seats=" + (selectedPath.getSeats() - 1)).asString().getBody();

            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();

        }
    }

}
