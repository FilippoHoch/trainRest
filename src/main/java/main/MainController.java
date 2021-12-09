/**
 * Sample Skeleton for 'main.fxml' Controller Class
 */

package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.*;

public class MainController implements Initializable {
    ObjectMapper om = new ObjectMapper();
    public List<Ticket> tickets = new ArrayList<>();
    public List<Path> paths = new ArrayList<>();

    private Ticket ticketSelected;
    private Path pathSelected;

    private int selectedDestinationStation;
    private Date selectedArriveTime;
    private int selectedChosenClass;
    private Date selectedDepartureTime;
    private Double selectedDisponibilityPrice;
    private int selectedStartingStation;
    private boolean search = false;
    private int selectedTicket;

    public Ticket getTicketSelected() {
        return ticketSelected;
    }

    public void setTicketSelected(Ticket ticketSelected) {
        this.ticketSelected = ticketSelected;
    }

    public Path getPathSelected() {
        return pathSelected;
    }

    public void setPathSelected(Path pathSelected) {
        this.pathSelected = pathSelected;
    }

    public int getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(int selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public void setSelectedDestinationStation(int selectedDestinationStation) {
        this.selectedDestinationStation = selectedDestinationStation;
    }

    public void setSelectedArriveTime(Date selectedArriveTime) {
        this.selectedArriveTime = selectedArriveTime;
    }

    public void setSelectedChosenClass(int selectedChosenClass) {
        this.selectedChosenClass = selectedChosenClass;
    }

    public void setSelectedDepartureTime(Date selectedDepartureTime) {
        this.selectedDepartureTime = selectedDepartureTime;
    }

    public void setSelectedDisponibilityPrice(Double selectedDisponibilityPrice) {
        this.selectedDisponibilityPrice = selectedDisponibilityPrice;
    }

    public void setSelectedStartingStation(int selectedStartingStation) {
        this.selectedStartingStation = selectedStartingStation;
    }

    private static MainController instance;

    public static MainController getInstance() {
        return instance;
    }

    @FXML // fx:id="dropDownFilter"
    private ChoiceBox dropDownFilter; // Value injected by FXMLLoader

    @FXML // fx:id="find"
    private Button find; // Value injected by FXMLLoader

    @FXML // fx:id="guide"
    private Button guide; // Value injected by FXMLLoader

    @FXML // fx:id="listViewTickets"
    private ListView<String> listViewTickets; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        dropDownFilter.getItems().add("costo");
        dropDownFilter.getItems().add("numero tratta");
        dropDownFilter.getItems().add("ordine alfabetico");
        dropDownFilter.getItems().add("orario di partenza");
        dropDownFilter.getItems().add("orario di arrivo");
        dropDownFilter.getItems().add("numero fermate");

        dropDownFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                sortTickets();
            }
        });
    }

    /**
     * after you touch it, it opens a link for the project on your browser
     *
     * @param event
     * @return returns the link for the project on github
     */
    @FXML
    void openGuide(ActionEvent event) {
        try {
            // TODO: 08/12/2021 change and create new github references
            Desktop.getDesktop().browse(new URL("https://github.com/https://github.com/FilippoHoch/trainRest").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * open and closes correctly the fxml to show the window for inserting the informations
     *
     * @return returns a date
     */
    @FXML
    void searchTickets() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("search.fxml"));
        Stage stage = new Stage();
        Scene sceneSearch = new Scene(loader.load(), 300, 375);
        stage.setTitle("Search");
        stage.setScene(sceneSearch);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) find.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
        stage.setOnHiding(event -> {
            if (search) {
                getTickets();
                search = false;
            }
        });
    }


    /**
     * based on the file json, this function returns all the informations in java
     *
     * @return returns all the ticket information
     */
    private void getTickets() {
//        System.out.println(String.format("%s \n %s \n %s \n %s \n %s \n %s", selectedDestinationStation, Utility.dateToString(selectedArriveTime), selectedChosenClass, Utility.dateToString(selectedDepartureTime),  selectedDisponibilityPrice, selectedStartingStation));

        String urlTickets = String.format("http://localhost:8090/tickets?destinationStation=%s&arriveTime=%s&chosenClass=%s&departureTime=%s&disponibilityPrice=%s&startingStation=%s", selectedDestinationStation, Utility.dateToString(selectedArriveTime), selectedChosenClass, Utility.dateToString(selectedDepartureTime), selectedDisponibilityPrice, selectedStartingStation);
        String jsonTickets = Unirest.get(urlTickets).asString().getBody();
        String jsonPaths = Unirest.get("http://localhost:8090/paths").asString().getBody();

        System.out.println(jsonTickets + "\n" + jsonPaths);

        try {
            tickets = om.readerForListOf(Ticket.class).readValue(jsonTickets);
            paths = om.readerForListOf(Path.class).readValue(jsonPaths);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        setListViewTickets();
    }
    // TODO: 08/12/2021 resolve sort problems

    /**
     * after you select one of this filters, all of the tickets will be ordered based on that filter
     *
     * @return returns the tickets ordered in a certain way
     */
    private void sortTickets() {
        if (dropDownFilter.getSelectionModel().getSelectedIndex() == 0)
            tickets.sort(Comparator.comparingDouble(Ticket::getTotalCost));
        if (dropDownFilter.getSelectionModel().getSelectedIndex() == 1)
            tickets.sort(Comparator.comparingInt(Ticket::getRoadPath));
        if (dropDownFilter.getSelectionModel().getSelectedIndex() == 2)
            paths.sort(Comparator.comparing(Path::getName));
        else
            paths.sort(Comparator.comparingInt(Path::getPathNumber));
        // TODO: 08/12/2021 Create a comparing function Date
//        if(dropdownFilter.getSelectionModel().getSelectedIndex() == 3)
//
//        if(dropdownFilter.getSelectionModel().getSelectedIndex() == 4)
//
        if (dropDownFilter.getSelectionModel().getSelectedIndex() == 5)
            paths.sort(Comparator.comparingInt(Path::getSizeLinks));
        else
            paths.sort(Comparator.comparingInt(Path::getPathNumber));

        setListViewTickets();
    }
    // TODO: 08/12/2021 if there are no items disable

    /**
     * this function orders correctly all of the tickets after you give a filter
     *
     * @return returns the correct visualisation of the tickets
     */
    private void setListViewTickets() {
        if (!listViewTickets.getItems().isEmpty())
            listViewTickets.getItems().remove(0, listViewTickets.getItems().size());
        for (Ticket ticket : tickets) {
            for (Path path : paths) {
                if (path.getPathNumber() == ticket.getRoadPath())
                    listViewTickets.getItems().add(String.format("%s: %s, Orario partenza: %s - Orario arrivo: %s, Prezzo: %s ", ticket.getRoadPath(), path.getName(), ticket.getDepartureDate(), ticket.getArriveDate(), ticket.getTotalCost()));
            }
        }
    }

    /**
     * shows the ticket after you click it
     *
     * @return shows you the ticket
     */
    @FXML
    private void selectTicket() throws IOException {
        selectedTicket = listViewTickets.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("ticket.fxml"));
        Stage stage = new Stage();
        Scene sceneTicket = new Scene(loader.load(), 600, 205);

        ticketSelected = tickets.get(selectedTicket);
        for (Path path : paths) {
            if (path.getPathNumber() == ticketSelected.getRoadPath()) {
                stage.setTitle("Ticket " + path.getName());
                setPathSelected(path);
                break;
            }
        }

        stage.setScene(sceneTicket);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        // TODO: 08/12/2021 change to better self stage declaration
        Stage currentStage = (Stage) find.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        TicketController.getInstance().setParameters(ticketSelected, pathSelected, paths);
    }
}
