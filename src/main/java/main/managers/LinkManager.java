package main.managers;

import main.Link;
import main.Station;

import java.util.*;

public class LinkManager {
    private final Map<Integer, List<Link>> links = new HashMap<>();

    public List<Link> getLinks(Station station, int pathNumber) {
        return filterLinks(links.get(station.getId()), pathNumber);
    }

    public List<Link> getLinks(int pathNumber) {
        return filterLinks(getAllLinks(), pathNumber);
    }

    public List<Link> getLinks(Station station) {
        return links.get(station.getId());
    }

    public static List<Link> filterLinks(List<Link> links, int pathNumber) {
        List<Link> filteredLinks = new ArrayList<>();
        for (Link link : links) {
            if (link.getPathNumber() == pathNumber) {
                filteredLinks.add(link);
            }
        }
        return filteredLinks;
    }

    public int getNumberOfLinks(int pathNumber) {
        return getLinks(pathNumber).size();
    }

    public void addLink(Link link) {
        int key = link.getStartStation();
        if (!links.containsKey(key)) {
            links.put(key, new ArrayList<>());
        }
        links.get(key).add(link);
    }

    public void addAllLinks(List<Link> links) {
        for (Link link : links) {
            addLink(link);
        }
    }

    public List<Link> getAllLinks() {
        List<Link> allLinks = new ArrayList<>();
        for (List<Link> stationLinks : links.values()) {
            allLinks.addAll(stationLinks);
        }
        return allLinks;
    }

    public static Set<Integer> getPathNumbers(List<Link> links) {
        Set<Integer> pathNumbers = new HashSet<>();
        for (Link link : links) {
            pathNumbers.add(link.getPathNumber());
        }
        return pathNumbers;
    }

    public void removeLink(Link link) {
        int key = link.getStartStation();
        links.get(key).remove(link);
    }
}
