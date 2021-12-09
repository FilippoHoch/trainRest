package main;

import main.managers.LinkManager;
import main.managers.PathFinder;
import main.managers.StationManager;

import java.util.List;

public class Main {

    public static StationManager stationManager = new StationManager();
    public static LinkManager linkManager = new LinkManager();

    public static void initialize() {
        // initialize stations
        stationManager.addStation(new Station(0, "A"));
        stationManager.addStation(new Station(1, "B"));
        stationManager.addStation(new Station(2, "C"));
        stationManager.addStation(new Station(3, "D"));
//        stationManager.addStation(new Station(4, "E"));

        // initialize links

        // from A
        linkManager.addLink(new Link(0, 1, 0, 1));
        linkManager.addLink(new Link(0, 2, 1, 1));
        linkManager.addLink(new Link(0, 3, 2, 1));

        // from B
        linkManager.addLink(new Link(1, 2, 0, 1));

        // from C
        linkManager.addLink(new Link(2, 0, 0, 1));
        linkManager.addLink(new Link(2, 0, 2, 1));
        linkManager.addLink(new Link(2, 3, 1, 1));

        // from D
        linkManager.addLink(new Link(3, 0, 1, 1));
        linkManager.addLink(new Link(3, 2, 2, 1));

    }

    public static void main(String[] args) {
        initialize();

        PathFinder pathFinder = new PathFinder();
        int start = 2, end = 0, pathNumber = 0;
        Station startStation = stationManager.getStation(start);
        Station endStation = stationManager.getStation(end);
//        List<Station> stations = pathFinder.getPath(start, end, pathNumber, stationManager.getAllStations(), linkManager.getAllLinks());
//
//        System.out.print("Path from " + startStation.getName() +" to " + endStation.getName());
//
//        if (stations.size() == 0) {
//            System.out.print(" not found");
//            return;
//        }
//
//        System.out.print(": \n");
//        for (Station station : stations) {
//            System.out.print(station.getName() + "\n");
//        }

        List<Integer> numbers = pathFinder.getPathNumbersThroughStations(start, end, stationManager.getAllStations(), linkManager.getAllLinks());
        if (numbers.size() == 0) {
            System.out.print("no paths available");
        } else {
            for (int a : numbers) {
                System.out.print("path number " + a + " available from " + startStation.getName() + " to " + endStation.getName() + "\n");
            }
        }
    }

}
