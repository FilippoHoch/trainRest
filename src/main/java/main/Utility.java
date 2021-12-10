package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        return Date.from(LocalDateTime.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm")).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * transform minutes and hours into milliseconds
     *
     * @param hours
     * @param minutes
     * @return returns the time in milliseconds
     */
    public static Date convertTime(int hours, int minutes) {
        long hoursLong = (hours) * 3600000L;
        long minutesLong = minutes * 60000L;
        long time = hoursLong + minutesLong;
        return new Date(time);
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


    public static List<String> dateToList(Date date) {
        List<String> dateList = new ArrayList<>();
        dateList.add(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd")));
        dateList.add(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("MM")));
        dateList.add(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy")));
        dateList.add(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("HH")));
        dateList.add(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("mm")));
        return dateList;
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date intToDate(Long num) {
        return new Date(num);
    }

    public static List<Ticket> sortTicketsByDepartureDate(List<Ticket> tickets) {
        for (int i = 0; i < tickets.size() - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < tickets.size(); j++) {
                if (tickets.get(minimo).getDepartureDate().compareTo(tickets.get(j).getDepartureDate()) > 0) {
                    minimo = j;
                }
            }
            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if (minimo != i) {
                Ticket k = tickets.get(minimo);
                tickets.set(minimo, tickets.get(i));
                tickets.set(i, k);
            }
        }
        return tickets;
    }

    public static List<Ticket> sortTicketsByArriveDate(List<Ticket> tickets) {
        for (int i = 0; i < tickets.size() - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < tickets.size(); j++) {
                if (tickets.get(minimo).getArriveDate().compareTo(tickets.get(j).getArriveDate()) > 0) {
                    minimo = j;
                }
            }
            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if (minimo != i) {
                Ticket k = tickets.get(minimo);
                tickets.set(minimo, tickets.get(i));
                tickets.set(i, k);
            }
        }
        return tickets;
    }

    public static List<Ticket> sortTicketsByRoadPath(List<Ticket> tickets) {
        for (int i = 0; i < tickets.size() - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < tickets.size(); j++) {
                if (tickets.get(minimo).getRoadPath() > tickets.get(j).getRoadPath()) {
                    minimo = j;
                }
            }
            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if (minimo != i) {
                Ticket k = tickets.get(minimo);
                tickets.set(minimo, tickets.get(i));
                tickets.set(i, k);
            }
        }
        return tickets;
    }

    public static List<Ticket> sortTicketsByTotalCost(List<Ticket> tickets) {
        for (int i = 0; i < tickets.size() - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < tickets.size(); j++) {
                if (tickets.get(minimo).getTotalCost() > tickets.get(j).getTotalCost()) {
                    minimo = j;
                }
            }
            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if (minimo != i) {
                Ticket k = tickets.get(minimo);
                tickets.set(minimo, tickets.get(i));
                tickets.set(i, k);
            }
        }
        return tickets;
    }

    public static List<Path> sortTicketsByPathName(List<Path> paths) {
        for (int i = 0; i < paths.size() - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < paths.size(); j++) {
                if (paths.get(minimo).getName().compareTo(paths.get(j).getName()) < 0) {
                    minimo = j;
                }
            }
            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if (minimo != i) {
                Path k = paths.get(minimo);
                paths.set(minimo, paths.get(i));
                paths.set(i, k);
            }
        }
        return paths;
    }

    public static List<Path> sortTicketsByPathLinkNumber(List<Path> paths) {
//        for(int i = 0; i < paths.size()-1; i++) {
//            int minimo = i;
//            for(int j = i+1; j < paths.size(); j++) {
//                if(paths.get(minimo).getSizeLinks() < paths.get(j).getSizeLinks()) {
//                    minimo = j;
//                }
//            }
//            //Se minimo e diverso dall' elemento di partenza
//            //allora avviene lo scambio
//            if(minimo!=i) {
//                Path k = paths.get(minimo);
//                paths.set(minimo, paths.get(i));
//                paths.set(i, k);
//            }
//        }
        return paths;
    }

    public static List<Path> sortTicketsByPathNumber(List<Path> paths) {
        for (int i = 0; i < paths.size() - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < paths.size(); j++) {
                if (paths.get(minimo).getPathNumber() > paths.get(j).getPathNumber()) {
                    minimo = j;
                }
            }
            //Se minimo e diverso dall' elemento di partenza
            //allora avviene lo scambio
            if (minimo != i) {
                Path k = paths.get(minimo);
                paths.set(minimo, paths.get(i));
                paths.set(i, k);
            }
        }
        return paths;
    }
}
