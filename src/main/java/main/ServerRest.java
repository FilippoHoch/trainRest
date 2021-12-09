package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.managers.LinkManager;
import main.managers.PathFinder;
import main.managers.StationManager;

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


    // Da fare prima di venerdì
    // TODO: 08/12/2021 create a new MainApplication to launch editDatabase
    // TODO: 08/12/2021 change style of the fxml
    // TODO: 08/12/2021 check javaDoc and insert comments line
    // TODO: 08/12/2021 Optimize the code (Disable almost all warnings)

    // Da fare dopo venerdì
    // TODO: 08/12/2021 finish the Postman configuration
    // TODO: 08/12/2021 create mb (MarkDown) file for readme
    // TODO: 08/12/2021 create presentation

    public static void initialize() {
        // TODO: 08/12/2021 Add more elements to all list

        // initialize stations
        stationManager.addStation(new Station(0, "A"));
        stationManager.addStation(new Station(1, "B"));
        stationManager.addStation(new Station(2, "C"));
        stationManager.addStation(new Station(3, "D"));

        // initialize links
        paths.add(new Path(0, "Temp1", Utility.convertTime(8, 30), Utility.convertTime(9, 15), 100));
        paths.add(new Path(1, "Temp2", Utility.convertTime(8, 30), Utility.convertTime(9, 15), 150));
        paths.add(new Path(2, "Temp3", Utility.convertTime(8, 30), Utility.convertTime(9, 15), 350));

        // from A
        linkManager.addLink(new Link(0, 1, 0, 1));
        linkManager.addLink(new Link(0, 2, 1, 2));
        linkManager.addLink(new Link(0, 3, 2, 3));

        // from B
        linkManager.addLink(new Link(1, 2, 0, 4));

        // from C
        linkManager.addLink(new Link(2, 0, 0, 5));
        linkManager.addLink(new Link(2, 0, 2, 6));
        linkManager.addLink(new Link(2, 3, 1, 7));

        // from D
        linkManager.addLink(new Link(3, 0, 1, 8));
        linkManager.addLink(new Link(3, 2, 2, 9));

        // Classes
        classes.add(new Class(1, 0.7));
        classes.add(new Class(2, 0.3));
        classes.add(new Class(3, 0.1));

        // Tickets
        for (int i = 0; i < 30; i++) {
            tickets.add(new Ticket(i, currentDate, classes.get(0)));
            tickets.add(new Ticket(i, currentDate, classes.get(1)));
            tickets.add(new Ticket(i, currentDate, classes.get(2)));

        }
        for (int i = 0; i < 30; i++) {
            tickets.add(new Ticket(i, nextDate, classes.get(0)));
            tickets.add(new Ticket(i, nextDate, classes.get(1)));
            tickets.add(new Ticket(i, nextDate, classes.get(2)));
        }


    }

    public void run() {
        initialize();

        // Start embedded server at this port
        port(8090);

        put("/updateTicket", (((request, response) -> {
            if (!request.queryParams().contains("elementNumber"))
                return "Missing element number";
            if (request.queryParams().contains("roadPath"))
                tickets.get(Integer.parseInt(request.queryParams("elementNumber"))).setRoadPath(Integer.parseInt(request.queryParams("roadPath")));
            if (request.queryParams().contains("day"))
                tickets.get(Integer.parseInt(request.queryParams("elementNumber"))).setDay(Utility.stringToOnlyDate(request.queryParams("day")));
            if (request.queryParams().contains("classNumber"))
                tickets.get(Integer.parseInt(request.queryParams("elementNumber"))).setaClass(classes.get(Integer.parseInt(request.queryParams("classNumber"))));
            return "Ticket Updated";
        })));

        post("/addTicket", (((request, response) -> {
            tickets.add(new Ticket(Integer.parseInt(request.queryParams("roadPath")), Utility.stringToDate(request.queryParams("day")), classes.get(Integer.parseInt(request.queryParams("classNumber")))));
            return "Ticket Added";
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
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setDepartureTime(Utility.stringToDate(request.queryParams("startDate")));
            if (request.queryParams().contains("endDate"))
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setArrivalTime(Utility.stringToDate(request.queryParams("endDate")));
            if (request.queryParams().contains("seats"))
                paths.get(Integer.parseInt(request.queryParams("elementNumber"))).setSeats(Integer.parseInt(request.queryParams("seats")));
            return "Path Updated";
        })));

        post("/addPath", (((request, response) -> {
            paths.add(new Path(paths.size(), request.queryParams("pathName"), Utility.stringToDate(request.queryParams("startDate")), Utility.stringToDate(request.queryParams("endDate")), Integer.parseInt(request.queryParams("seats"))));
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
                linkManager.getAllLinks().remove(Integer.parseInt(request.queryParams("elementNumber")));
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
            stationManager.addStation(new Station(stationManager.stations.size(), request.queryParams("stationName")));
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
            if (request.queryParams().contains("multiplayer"))
                classes.get(Integer.parseInt(request.queryParams("elementNumber"))).setMultiplier(Integer.parseInt(request.queryParams("multiplayer")));
            return "Station Updated";
        })));

        post("/addClass", (((request, response) -> {
            classes.add(new Class(Integer.parseInt(request.queryParams("classNumber")), Integer.parseInt(request.queryParams("multiplayer"))));
            return "Class Added";
        })));

        delete("/removeClass", (((request, response) -> {
            if (request.queryParams().contains("elementNumber")) {
                classes.remove(Integer.parseInt(request.queryParams("elementNumber")));
                return "Class Removed";
            }
            return "Class not found";
        })));

        get("/classes", ((request, response) -> {
            return om.writeValueAsString(classes);
        }));

        get("/stations", ((request, response) -> {
            return om.writeValueAsString(stationManager.getAllStations());
        }));

        get("/paths", ((request, response) -> {
            return om.writeValueAsString(paths);
        }));

        get("/links", ((request, response) -> {
            return om.writeValueAsString(linkManager.getAllLinks());
        }));


        get("/all", ((request, response) -> {
            return om.writeValueAsString(tickets);
        }));

        get("/tickets", ((request, response) -> {


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
                    for (Path path : paths) {
                        if (path.getPathNumber() == a) {
                            path.setStations(pathFinder.getPath(start, end, a, stationManager.getAllStations(), linkManager.getAllLinks()));
                            path.setLinks(LinkManager.filterLinks(linkManager.getAllLinks(), a));
                        }
                    }
                    for (Ticket ticket : tickets) {
                        ticket.setTotalCost(LinkManager.filterLinks(linkManager.getAllLinks(), a));
                        System.out.println("a: " + a + " start: " + start + " end: " + end);

                        startingTime = startingStationTime(a, start, ticket.getDay());


                        destinationTime = destinationStationTime(a, ticket.getDay());
                        System.out.println("Starting time: " + startingTime + "\nDestination time: " + destinationTime);
                        if (ticket.getRoadPath() == a && ticket.getaClass().getClassNumber().toString().equalsIgnoreCase(chosenClass) &&
                                ticket.getTotalCost() <= Double.parseDouble(disponibilityPrice) && startingTime.getTime() >= Utility.stringToDateTime(departureTime).getTime()
                                && destinationTime.getTime() <= Utility.stringToDateTime(arriveTime).getTime() && paths.get(a).getSeats() > 0) {
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
        }));

    }


    // "dd/MM/yyyy-hh:mm"
    // " dow mon dd hh:mm:ss zzz yyyy"

    // TODO: 08/12/2021 resolve the date problem (departure and arrive are inverted) and the date isn't right

    /**
     * based on the first and the last station of a path the client decides, this function calculates the time it takes to travel that path
     *
     * @param path
     * @param startingStation
     * @param destinationStation
     * @return returns the time of when the train will arrive at the last station
     */
    public Date DestinationStationTime(int path, int startingStation, int destinationStation) {
        List<Link> linkList = LinkManager.filterLinks(linkManager.getAllLinks(), path);
        int destinationTime = 0;
        boolean isStartingStationBefore = false;
        if (linkList.get(0).getStartStation() == startingStation)
            isStartingStationBefore = true;
        for (Link link : linkList) {
            destinationTime += link.getCost();
            if (isStartingStationBefore) {
                if (link.getEndStation() == destinationStation)
                    break;
            } else if (link.getEndStation() == startingStation)
                isStartingStationBefore = true;
        }
        return new Date(paths.get(path).getDepartureTime().getTime() + destinationTime * 60000L);
    }

    /**
     * based on the destinationTime, the function used before, the path and the first and last stations, this function calculates the time of when the train leaves the first station
     *
     * @param path
     * @param startingStation
     * @param destinationStation
     * @param destinationTime
     * @return returns the time of when the train leaves the first station
     */
    public Date StartingStationTime(int path, int startingStation, int destinationStation, Date destinationTime) {
        List<Link> linkList = LinkManager.filterLinks(linkManager.getAllLinks(), path);
        int startingTime = 0;
        boolean isStartingStationBefore = false;
        int id = 0;
        Date result = new Date();
        if (linkList.get(0).getStartStation() == startingStation)
            isStartingStationBefore = true;
        for (Link link : linkList) {
            if (isStartingStationBefore) {
                if (link.getEndStation() == destinationStation) {
                    id = linkList.indexOf(link);
                    break;
                }
            } else if (link.getEndStation() == startingStation)
                isStartingStationBefore = true;
        }
        for (int i = id; i >= 0; i--) {
            if (linkList.get(i).getEndStation() == startingStation) {
                result = new Date(destinationTime.getTime() - (new Date(startingTime * 60000L).getTime()));
                break;
            } else if (linkList.get(i).getStartStation() == startingStation) {
                return new Date(paths.get(path).getDepartureTime().getTime());
            }
            startingTime += linkList.get(i).getCost();
        }
        return result;
    }

    public static void main(String[] args) {
        new ServerRest().run();
    }
}
