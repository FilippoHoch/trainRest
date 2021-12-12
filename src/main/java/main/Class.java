package main;

import java.util.Objects;

public class Class {

    private Integer classNumber;
    private double multiplier;

    public Class(Integer classNumber, double multiplier) {
        this.classNumber = classNumber;
        this.multiplier = multiplier;
    }

    public Class() {
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classNumber=" + classNumber +
                ", multiplier=" + multiplier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return Double.compare(aClass.multiplier, multiplier) == 0 && Objects.equals(classNumber, aClass.classNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classNumber, multiplier);
    }
}
