package main;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utility {

    /**
     * transform a date value in a string
     *
     * @param date
     * @return returns a string
     */
    public static String dateToString(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));
    }

    /**
     * transform a string containing a date value in a date
     *
     * @param string
     * @return returns a date
     */
    public static Date stringToDate(String string) {
        Date date = Date.from(LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).atZone(ZoneId.systemDefault()).toInstant());
        String updatedString = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));
        return Date.from(LocalDateTime.parse(updatedString, DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm")).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * transform a string containing a date and a time value in a date
     *
     * @param string
     * @return returns a date
     */
    public static Date stringToDateTime(String string) {
        Date temp = Date.from(LocalDateTime.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm")).atZone(ZoneId.systemDefault()).toInstant());
        return temp;
    }

    /**
     * transform minutes and hours into milliseconds
     *
     * @param hours
     * @param minutes
     * @return returns the time in milliseconds
     */
    public static Date convertTime(int hours, int minutes) {
        return new Date(ServerRest.currentDate.getTime() + (hours * 3600000L + minutes * 60000L));
    }

    /**
     * changes a certain value in a double with 2 decimals
     *
     * @param value
     * @param places
     * @return returns the values in double with 2 decimals
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String timeToString(Date time) {
        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static Date stringToOnlyDate(String string) {
        Date temp = Date.from(LocalDateTime.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atZone(ZoneId.systemDefault()).toInstant());
        return temp;
    }


}
