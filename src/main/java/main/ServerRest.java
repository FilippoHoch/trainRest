package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.managers.LinkManager;
import main.managers.PathFinder;
import main.managers.StationManager;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;

public class ServerRest {

    ObjectMapper om = new ObjectMapper();

    public static StationManager stationManager = new StationManager();
    public static LinkManager linkManager = new LinkManager();
    public static List<Ticket> tickets = new ArrayList<>();
    public static List<Path> paths = new ArrayList<>();
    public static List<Class> classes = new ArrayList<>();
    public static Date currentDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    public static Date nextDate = new Date(currentDate.getTime() + 86400000);

    public static Date startingTime;
    public static Date destinationTime;


    public static void initialize() {


        // initialize stations
        stationManager.addStation(new Station(0, "Deposito"));
        stationManager.addStation(new Station(1, "Ponte"));
        stationManager.addStation(new Station(2, "Zona Industriale"));
        stationManager.addStation(new Station(3, "Ranch"));
        stationManager.addStation(new Station(4, "Villaggio"));
        stationManager.addStation(new Station(5, "Bivio"));

        // path number 0
        paths.add(new Path(0, "Percorso 1 Mattina", Utility.convertTime(7, 30), Utility.convertTime(8, 15), 130));
        linkManager.addLink(new Link(0, 1, 0, 10));
        linkManager.addLink(new Link(1, 4, 0, 15));
        linkManager.addLink(new Link(4, 3, 0, 20));

        // path number 1
        paths.add(new Path(1, "Percorso 2 Mattina", Utility.convertTime(6, 30), Utility.convertTime(7, 05), 50));
        linkManager.addLink(new Link(0, 1, 1, 10));
        linkManager.addLink(new Link(1, 2, 1, 25));

        // path number 2
        paths.add(new Path(2, "Percorso 3 Mattina", Utility.convertTime(8, 30), Utility.convertTime(8, 55), 350));
        linkManager.addLink(new Link(0, 1, 2, 10));
        linkManager.addLink(new Link(1, 4, 2, 15));

        // path number 3
        paths.add(new Path(3, "Percorso 4 Mattina", Utility.convertTime(9, 20), Utility.convertTime(10, 10), 300));
        linkManager.addLink(new Link(0, 1, 3, 10));
        linkManager.addLink(new Link(1, 4, 3, 15));
        linkManager.addLink(new Link(4, 3, 3, 20));
        linkManager.addLink(new Link(3, 1, 3, 5));

        // path number 4
        paths.add(new Path(4, "Percorso 5 Mattina", Utility.convertTime(10, 20), Utility.convertTime(10, 50), 200));
        linkManager.addLink(new Link(1, 3, 4, 5));
        linkManager.addLink(new Link(3, 4, 4, 25));

        // path number 5
        paths.add(new Path(5, "Percorso 6 Mattina", Utility.convertTime(11, 30), Utility.convertTime(12, 15), 250));
        linkManager.addLink(new Link(1, 3, 5, 5));
        linkManager.addLink(new Link(3, 4, 5, 25));
        linkManager.addLink(new Link(4, 5, 5, 15));

        // path number 6
        paths.add(new Path(6, "Percorso 1 Pomeriggio", Utility.convertTime(14, 30), Utility.convertTime(15, 10), 100));
        linkManager.addLink(new Link(2, 1, 6, 25));
        linkManager.addLink(new Link(1, 4, 6, 15));

        // path number 7
        paths.add(new Path(7, "Percorso 2 Pomeriggio", Utility.convertTime(13, 30), Utility.convertTime(14, 35), 200));
        linkManager.addLink(new Link(1, 4, 7, 15));
        linkManager.addLink(new Link(4, 3, 7, 20));
        linkManager.addLink(new Link(3, 1, 7, 5));
        linkManager.addLink(new Link(1, 2, 7, 25));

        // path number 8
        paths.add(new Path(8, "Percorso 3 Pomeriggio", Utility.convertTime(15, 00), Utility.convertTime(15, 30), 250));
        linkManager.addLink(new Link(3, 1, 8, 5));
        linkManager.addLink(new Link(1, 2, 8, 25));

        // path number 9
        paths.add(new Path(9, "Percorso 4 Pomeriggio", Utility.convertTime(18, 20), Utility.convertTime(19, 00), 100));
        linkManager.addLink(new Link(3, 4, 9, 25));
        linkManager.addLink(new Link(4, 5, 9, 15));

        // path number 10
        paths.add(new Path(10, "Percorso 5 Pomeriggio", Utility.convertTime(16, 10), Utility.convertTime(16, 50), 150));
        linkManager.addLink(new Link(4, 5, 10, 15));
        linkManager.addLink(new Link(5, 2, 10, 25));

        // path number 11
        paths.add(new Path(11, "Percorso 6 Pomeriggio", Utility.convertTime(17, 15), Utility.convertTime(17, 55), 300));
        linkManager.addLink(new Link(5, 4, 11, 15));
        linkManager.addLink(new Link(4, 3, 11, 20));
        linkManager.addLink(new Link(3, 1, 11, 5));

        // Classes
        classes.add(new Class(1, 1.2));
        classes.add(new Class(2, 0.7));
        classes.add(new Class(3, 0.5));

        // Tickets
        for (int i = 0; i < paths.size(); i++) {
            for (Class aClass : classes) {
                tickets.add(new Ticket(i, currentDate, aClass));
                tickets.add(new Ticket(i, nextDate, aClass));
            }
        }

    }

    public void run() {
        initialize();

        // Start embedded server at this port
        port(8090);

        get("/help", ((request, response) -> {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/FilippoHoch/trainRest/blob/master/README.md").toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return "Redirect";
        }));

        put("/updateTicket", (((request, response) -> {

            if (!request.queryParams().contains("elementNumber"))
                return "Missing element number";
            if (request.queryParams().contains("roadPath"))
                tickets.get(Integer.parseInt(request.queryParams("elementNumber"))).setRoadPath(Integer.parseInt(request.queryParams("roadPath")));
            if (request.queryParams().contains("day"))
                tickets.get(Integer.parseInt(request.queryParams("elementNumber"))).setDay(Utility.intToDate(Long.parseLong(request.queryParams("day"))));
            if (request.queryParams().contains("classNumber"))
                tickets.get(Integer.parseInt(request.queryParams("elementNumber"))).setaClass(classes.get(Integer.parseInt(request.queryParams("classNumber"))));
            return "Ticket Updated";
        })));

        post("/addTicket", (((request, response) -> {
            tickets.add(new Ticket(Integer.parseInt(request.queryParams("roadPath")), Utility.intToDate(Long.parseLong(request.queryParams("day"))), classes.get(Integer.parseInt(request.queryParams("classNumber")))));
            return "Ticket added";
        })));

        delete("/removeTicket", (((request, response) -> {

            if (request.queryParams().contains("elementNumber")) {
                tickets.remove(Integer.parseInt(request.queryParams("elementNumber")));
                return "Ticket Removed";
            }
            return "Ticket not found";
        })));

        put("/updatePath", (((request, response) -> {

            if (!request.queryParams().contains("elementNumber"))
                return "Missing element number";
            if (request.queryParams().contains("pathName"))
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setName(request.queryParams("pathName"));
            if (request.queryParams().contains("startDate"))
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setDepartureTime(Utility.intToDate(Long.parseLong(request.queryParams("startDate"))));
            if (request.queryParams().contains("endDate"))
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setArrivalTime(Utility.intToDate(Long.parseLong(request.queryParams("endDate"))));
            if (request.queryParams().contains("seats"))
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setSeats(Integer.parseInt(request.queryParams("seats")));
            return "Path Updated";
        })));

        post("/addPath", (((request, response) -> {
            paths.add(new Path(paths.size(), request.queryParams("pathName"), Utility.intToDate(Long.parseLong(request.queryParams("startDate"))), Utility.intToDate(Long.parseLong(request.queryParams("endDate"))), Integer.parseInt(request.queryParams("seats"))));
            return "Path Added";
        })));

        delete("/removePath", (((request, response) -> {

            if (request.queryParams().contains("elementNumber")) {
                paths.remove(Integer.parseInt(request.queryParams("elementNumber")));
                return "Path Removed";
            }
            return "Path not found";
        })));

        put("/updateLink", (((request, response) -> {

            if (!request.queryParams().contains("elementNumber"))
                return "Missing element number";
            if (request.queryParams().contains("startStation"))
                linkManager.getAllLinks().get(Integer.parseInt(request.queryParams("elementNumber"))).setStartStation(Integer.parseInt(request.queryParams("startStation")));
            if (request.queryParams().contains("endStation"))
                linkManager.getAllLinks().get(Integer.parseInt(request.queryParams("elementNumber"))).setEndStation(Integer.parseInt(request.queryParams("endStation")));
            if (request.queryParams().contains("pathNumber"))
                linkManager.getAllLinks().get(Integer.parseInt(request.queryParams("elementNumber"))).setPathNumber(Integer.parseInt(request.queryParams("pathNumber")));
            if (request.queryParams().contains("cost"))
                linkManager.getAllLinks().get(Integer.parseInt(request.queryParams("elementNumber"))).setCost(Integer.parseInt(request.queryParams("cost")));
            return "Link Updated";
        })));


        post("/addLink", (((request, response) -> {

            linkManager.addLink(new Link(Integer.parseInt(request.queryParams("startStation")), Integer.parseInt(request.queryParams("endStation")), Integer.parseInt(request.queryParams("pathNumber")), Integer.parseInt(request.queryParams("cost"))));
            return "Link Added";
        })));

        delete("/removeLink", (((request, response) -> {

            if (request.queryParams().contains("elementNumber")) {
                linkManager.removeLink(linkManager.getAllLinks().get(Integer.parseInt(request.queryParams("elementNumber"))));
                return "Link Removed";
            }
            return "Link not found";
        })));

        put("/updateStation", (((request, response) -> {

            if (!request.queryParams().contains("elementNumber"))
                return "Missing element number";
            if (request.queryParams().contains("stationName"))
                stationManager.getStation(Integer.parseInt(request.queryParams("elementNumber"))).setName(request.queryParams("stationName"));
            return "Station Updated";
        })));


        post("/addStation", (((request, response) -> {
            stationManager.addStation(new Station(stationManager.getLastStationNumber(), request.queryParams("stationName")));
            return "Station Added";
        })));

        delete("/removeStation", (((request, response) -> {

            if (request.queryParams().contains("elementNumber")) {
                stationManager.stations.remove(Integer.parseInt(request.queryParams("elementNumber")));
                return "Station Removed";
            }
            return "Station not found";
        })));

        put("/updateClass", (((request, response) -> {

            if (!request.queryParams().contains("elementNumber"))
                return "Missing element number";
            if (request.queryParams().contains("classNumber"))
                classes.get(Integer.parseInt(request.queryParams("elementNumber"))).setMultiplier(Integer.parseInt(request.queryParams("classNumber")));
            if (request.queryParams().contains("multiplier"))
                classes.get(Integer.parseInt(request.queryParams("elementNumber"))).setMultiplier(Double.parseDouble(request.queryParams("multiplier")));
            return "Station Updated";
        })));

        post("/addClass", (((request, response) -> {

            classes.add(new Class(Integer.parseInt(request.queryParams("classNumber")), Integer.parseInt(request.queryParams("multiplier"))));
            return "Class Added";
        })));

        delete("/removeClass", (((request, response) -> {

            if (request.queryParams().contains("elementNumber")) {
                classes.remove(Integer.parseInt(request.queryParams("elementNumber")));
                return "Class Removed";
            }
            return "Class not found";
        })));

        get("/classes", ((request, response) -> om.writeValueAsString(classes)));

        get("/stations", ((request, response) -> om.writeValueAsString(stationManager.getAllStations())));

        get("/paths", ((request, response) -> om.writeValueAsString(paths)));

        get("/links", ((request, response) -> om.writeValueAsString(linkManager.getAllLinks())));

        get("/tickets", ((request, response) -> {


            if (request.queryParams().size() != 0) {


                List<Ticket> filteredTickets = new ArrayList<>();

                if (request.queryParams().size() < 6 || request.queryParams().size() > 6)
                    return "Bad request";

                String destinationStation = request.queryParams("destinationStation");
                String arriveTime = request.queryParams("arriveTime");
                String chosenClass = request.queryParams("chosenClass");
                String departureTime = request.queryParams("departureTime");
                String disponibilityPrice = request.queryParams("disponibilityPrice");
                String startingStation = request.queryParams("startingStation");

//            System.out.println(destinationStation + "\n" + arriveTime + "\n" + chosenClass + "\n" + departureTime + "\n" + disponibilityPrice + "\n" + startingStation);

                PathFinder pathFinder = new PathFinder();
                int start = Integer.parseInt(startingStation), end = Integer.parseInt(destinationStation);

                Station startStation = stationManager.getStation(start);
                Station endStation = stationManager.getStation(end);

                List<Integer> numbers = pathFinder.getPathNumbersThroughStations(start, end, stationManager.getAllStations(), linkManager.getAllLinks());
                if (numbers.size() == 0) {
                    System.out.print("no paths available");
                } else {
                    for (int a : numbers) {
                        System.out.print("path number " + a + " available from " + startStation.getName() + " to " + endStation.getName() + "\n");
                        List<Station> stations = pathFinder.getPath(start, end, a, stationManager.getAllStations(), linkManager.getAllLinks());

                        for (Ticket ticket : tickets) {
                            startingTime = startingStationTime(stations, ticket.getDay(), a, paths);

                            destinationTime = destinationStationTime(stations, ticket.getDay(), a, paths);
                            if (ticket.getRoadPath() == a && ticket.getaClass().getClassNumber().toString().equalsIgnoreCase(chosenClass) &&
                                    ticket.getTotalCost() <= Double.parseDouble(disponibilityPrice) && startingTime.getTime() >= Utility.stringToDateTime(departureTime).getTime()
                                    && destinationTime.getTime() <= Utility.stringToDateTime(arriveTime).getTime() && paths.get(a).getSeats() > 0) {
                                ticket.setTotalCost(LinkManager.filterLinks(linkManager.getAllLinks(), a));
                                ticket.setDepartureDate(startingTime);
                                ticket.setArriveDate(destinationTime);
                                ticket.setDepartureStation(stationManager.getStation(start).getName());
                                ticket.setArriveStation(stationManager.getStation(end).getName());
                                filteredTickets.add(ticket);
                            }
                        }

                    }
                }
                return om.writeValueAsString(filteredTickets);

            }
            return om.writeValueAsString(tickets);
        }));


    }


    // "dd/MM/yyyy-hh:mm"
    // " dow mon dd hh:mm:ss zzz yyyy"

    public static Date destinationStationTime(List<Station> stations, Date day, int pathNumber, List<Path> paths) {
        Station previousStation = null;
        int time = 0;
        for (Station station : stations) {
            int cost = 0;
            if (station != stations.get(0)) {
                cost = linkManager.getLinks(previousStation, station, pathNumber).get(0).getCost();
            }
            time += cost;
            previousStation = station;
        }
        long dateResult = time * 60000L + day.getTime() + paths.get(pathNumber).getDepartureTime().getTime();
        return new Date(dateResult);
    }


    public static Date startingStationTime(List<Station> stations, Date day, int pathNumber, List<Path> paths) {
        Station previousStation = null;
        int time = 0;
        for (Station station : stations) {
            int cost = 0;
            if (station != stations.get(0)) {
                cost = linkManager.getLinks(previousStation, station, pathNumber).get(0).getCost();
            }
            time += cost;
            if (station.getId() == stations.get(0).getId())
                break;
            previousStation = station;

        }
        long dateResult = time * 60000L + day.getTime() + paths.get(pathNumber).getDepartureTime().getTime();
        return new Date(dateResult);
    }


    public static void main(String[] args) {
        new ServerRest().run();
    }
}