package main;

public class Link {
    private int startStation, endStation;
    private int cost;
    private int pathNumber;



    public int getStartStation() {
        return startStation;
    }

    public int getEndStation() {
        return endStation;
    }

    public int getCost() {
        return cost;
    }

    public int getPathNumber() {
        return pathNumber;
    }

    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPathNumber(int pathNumber) {
        this.pathNumber = pathNumber;
    }

    public Link(int startStation, int endStation, int pathNumber, int time) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.pathNumber = pathNumber;
        this.cost = time;
    }

    public Link() {
    }

    @Override
    public String toString() {
        return "Link{" +
                "startStation=" + startStation +
                ", endStation=" + endStation +
                ", cost=" + cost +
                ", pathNumber=" + pathNumber +
                '}';
    }
}
