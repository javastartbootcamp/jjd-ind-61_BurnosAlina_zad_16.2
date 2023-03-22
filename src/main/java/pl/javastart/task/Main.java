package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String UTC_ID = "UTC";
    private static final String LONDON_ID = "Europe/London";
    private static final String LOS_ANGELES_ID = "America/Los_Angeles";
    private static final String SYDNEY_ID = "Australia/Sydney";

    private static final String DATE_TIME_PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_PATTERN2 = "dd.MM.yyyy HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        LocalDateTime dateAndTime = getDateAndTime(scanner);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN1);
        System.out.println("Czas lokalny: " + dateAndTime.format(formatter));
        List<Zone> zones = new ArrayList<>();
        ZonedDateTime actualZoneTime = dateAndTime.atZone(ZoneId.systemDefault());
        zones.add(new Zone(UTC_ID, "UTC"));
        zones.add(new Zone(LONDON_ID, "Londyn"));
        zones.add(new Zone(LOS_ANGELES_ID, "Los Angeles"));
        zones.add(new Zone(SYDNEY_ID, "Sydney"));
        printTimeInZones(zones, formatter, actualZoneTime);
    }

    private LocalDateTime getDateAndTime(Scanner scanner) {
        System.out.println("Podaj datę:");
        String dateAndTime = scanner.nextLine();
        List<DateTimeFormatter> dateTimeFormatters = new ArrayList<>();
        List<DateTimeFormatter> dateFormatters = new ArrayList<>();
        dateTimeFormatters.add(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN1));
        dateFormatters.add(DateTimeFormatter.ofPattern(DATE_PATTERN));
        dateTimeFormatters.add(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN2));
        for (DateTimeFormatter formatter : dateTimeFormatters) {
            try {
                return LocalDateTime.parse(dateAndTime, formatter);
            } catch (DateTimeParseException e) {
//                noop
            }
        }
        for (DateTimeFormatter dateTimeFormatter : dateFormatters) {
            try {
                LocalDate date = LocalDate.parse(dateAndTime, dateTimeFormatter);
                LocalTime time = LocalTime.of(00, 00, 00);
                return LocalDateTime.of(date, time);
            } catch (DateTimeParseException e) {
//                noop
            }
        }
        return null;
    }

    private void printTimeInZones(List<Zone> zones, DateTimeFormatter formatter, ZonedDateTime actualTime) {
        for (Zone zone : zones) {
            System.out.println(zone.getName() + ": " +
                    actualTime.withZoneSameInstant(ZoneId.of(zone.getId())).format(formatter));
        }
    }
}
