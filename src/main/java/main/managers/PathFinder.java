package main.managers;

import main.Link;
import main.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PathFinder {
    StationManager exploredStations;
    StationManager visitedStations;
    StationManager newStations;
    LinkManager links;

    public List<Station> getPath(int startId, int endId, int pathNumber, List<Station> stations, List<Link> links) {
        // initialize search
        {
            // initialize stations
            for (Station station : stations) {
                station.clear();
            }

            exploredStations = new StationManager();
            visitedStations = new StationManager();
            newStations = new StationManager();
            newStations.addAllStations(stations);

            // initialize links
            this.links = new LinkManager();
            this.links.addAllLinks(LinkManager.filterLinks(links, pathNumber));
        }

        // build tree structure
        {
            visitedStations.addStation(newStations.removeStation(startId));

            // continues while the stations are all visited and explored
            Station minStation = visitedStations.getMin();
            while (minStation != null) {
                exploreStation(minStation);
                minStation = visitedStations.getMin();
            }
        }

        // get the best path from start to destination
        {
            List<Station> path = new ArrayList<>();
            Station destination = exploredStations.getStation(endId);

            // path not found
            if (destination == null)
                return path;

            path.add(destination);
            do {
                destination = exploredStations.getStation(destination.getPathParent());
                path.add(destination);
            } while (destination.getPathParent() != -1);

            Collections.reverse(path);
            return path;
        }
    }

    public List<Integer> getPathNumbersThroughStations(int startId, int endId, List<Station> stations, List<Link> links) {
        List<Integer> pathNumbersThroughStations = new ArrayList<>();
        Set<Integer> pathNumbers = LinkManager.getPathNumbers(links);
        for (int pathNumber : pathNumbers) {
            if (getPath(startId, endId, pathNumber, stations, links).size() != 0) {
                pathNumbersThroughStations.add(pathNumber);
            }

        }
        return pathNumbersThroughStations;
    }

    private void exploreStation(Station station) {
        // add station to explored stations after removing it from visited
        exploredStations.addStation(station);
        visitedStations.removeStation(station.getId());

        // get links from station
        List<Link> stationLinks = links.getLinks(station);

        if (stationLinks == null) return;

        Station nextStation = null;
        for (Link link : stationLinks) {
            nextStation = newStations.getStation(link.getEndStation());
            if (nextStation == null) {
                nextStation = visitedStations.getStation(link.getEndStation());
            }
            if (nextStation != null) {
                nextStation.updatePathCost(station, link);
                visitedStations.addStation(nextStation);
                newStations.removeStation(nextStation.getId());
            }
        }
    }


}
