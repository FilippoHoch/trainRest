package main.managers;

import main.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationManager {
    public final Map<Integer, Station> stations = new HashMap<>();

    public Station getStation(int id) {
        return stations.get(id);
    }

    public void addStation(Station station) {
        stations.put(station.getId(), station);
    }

    public void addAllStations(List<Station> stations) {
        for (Station station : stations) {
            addStation(station);
        }
    }

    public List<Station> getAllStations() {
        return new ArrayList<>(stations.values());
    }

    public Station removeStation(int id) {
        return stations.remove(id);
    }

    public boolean isEmpty() {
        return stations.isEmpty();
    }

    public Station getMin() {
        Station minStation = null;
        int minCost = Integer.MAX_VALUE;
        for (Station station : getAllStations()) {
            if (station.getPathCost() <= minCost) {
                minStation = station;
                minCost = minStation.getPathCost();
            }
        }
        return minStation;
    }
}
