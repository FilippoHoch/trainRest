package main;

import java.util.Date;
import java.util.List;

public class Path {
    private int pathNumber;
    private String name;
    private Date departureTime;
    private Date arrivalTime;
    private int seats;
    private List<Station> stations;
    private List<Link> links;
    private int sizeLinks;

    public Path(int pathNumber, String name, Date departureTime, Date arrivalTime, int seats) {
        this.pathNumber = pathNumber;
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = seats;
    }

    public Path() {
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        setSizeLinks(links.size());
        this.links = links;
    }


    public int getSizeLinks() {
        return sizeLinks;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setSizeLinks(int sizeLinks) {
        this.sizeLinks = sizeLinks;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public int getPathNumber() {
        return pathNumber;
    }

    public void setPathNumber(int pathNumber) {
        this.pathNumber = pathNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Path{" +
                "pathNumber=" + pathNumber +
                ", name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", seats=" + seats +
                ", stations=" + stations +
                ", links=" + links +
                ", sizeLinks=" + sizeLinks +
                '}';
    }
}
