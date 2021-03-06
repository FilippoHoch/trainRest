package main;

import java.util.Objects;

public class Station {
    private int id;
    private String name;
    // other attributes

    private int pathParent;
    private int pathCost;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
        this.pathParent = -1;
    }

    public Station() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * override of the variable "path parent" and "path cost"
     */
    public void clear() {
        pathParent = -1;
        pathCost = Integer.MAX_VALUE;
    }

    public int getPathParent() {
        return pathParent;
    }

    public void setPathParent(int pathParent) {
        this.pathParent = pathParent;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    /**
     * based on the cost of the ticket, if this is smaller than a certain value, it will get changed, or it would be too cheap :)
     */
    public void updatePathCost(Station station, Link link) {
        int newCost = station.pathCost + link.getCost();
        if (newCost < pathCost) {
            setPathCost(newCost);
            setPathParent(station.getId());
        }
    }


    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pathParent, pathCost);
    }
}
