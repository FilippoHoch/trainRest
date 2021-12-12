package main;

import java.util.Date;

public class Path {
    private int pathNumber;
    private String name;
    private Date departureTime;
    private Date arrivalTime;
    private int seats;

    public Path(int pathNumber, String name, Date departureTime, Date arrivalTime, int seats) {
        this.pathNumber = pathNumber;
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = seats;
    }

    public Path() {
    }

    public void setPathNumber(int pathNumber) {
        this.pathNumber = pathNumber;
    }

    public int getPathNumber() {
        return pathNumber;
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
                '}';
    }
}
