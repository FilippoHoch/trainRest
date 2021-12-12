package main;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Ticket {
    private int roadPath;
    private Date day;
    private Class aClass;
    private double totalCost;
    private Date departureDate;
    private Date arriveDate;
    private String departureStation;
    private String arriveStation;


    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(String arriveStation) {
        this.arriveStation = arriveStation;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getRoadPath() {
        return roadPath;
    }

    public Date getDay() {
        return day;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setRoadPath(int roadPath) {
        this.roadPath = roadPath;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public Ticket(int roadPath, Date day, Class aClass) {
        this.roadPath = roadPath;
        this.day = day;
        this.aClass = aClass;
    }

    public Ticket() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return roadPath == ticket.roadPath && Objects.equals(day, ticket.day) && Objects.equals(aClass, ticket.aClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadPath, day, aClass);
    }

    /**
     * this function calculates the cost of the ticket based on the class and the cost of that singular ticket
     *
     */
    public void setTotalCost(List<Link> linkList) {
        totalCost = 0;
        for (Link link : linkList)
            totalCost += link.getCost();
        totalCost *= aClass.getMultiplier();
        totalCost = Utility.round(totalCost, 2);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "roadPath=" + roadPath +
                ", day=" + day +
                ", aClass=" + aClass +
                '}';
    }
}
