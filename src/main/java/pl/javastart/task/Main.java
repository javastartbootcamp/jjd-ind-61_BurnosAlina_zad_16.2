package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        LocalDateTime dateAndTime = getDateAndTime(scanner);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Czas lokalny: " + dateAndTime.format(formatter));
        List<ZonedDateTime> zones = new ArrayList<>();
        ZonedDateTime polandTime = ZonedDateTime.of(dateAndTime, ZoneId.of("Europe/Warsaw"));
        zones.add(polandTime);
        ZonedDateTime utcTime = polandTime.withZoneSameInstant(ZoneId.of("UTC"));
        zones.add(utcTime);
        ZonedDateTime londonTime = polandTime.withZoneSameInstant(ZoneId.of("Europe/London"));
        zones.add(londonTime);
        ZonedDateTime laTime = polandTime.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
        zones.add(laTime);
        ZonedDateTime sydneyTime = polandTime.withZoneSameInstant(ZoneId.of("Australia/Sydney"));
        zones.add(sydneyTime);
        printTimeInZones(zones, formatter);
    }

    private LocalDateTime getDateAndTime(Scanner scanner) {
        LocalDateTime dateTime = null;
        System.out.println("Podaj datę:");
        String dateAndTime = scanner.nextLine();
        List<DateTimeFormatter> dateTimeFormatters = new ArrayList<>();
        List<DateTimeFormatter> dateFormatters = new ArrayList<>();
        dateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateTimeFormatters.add(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        for (DateTimeFormatter formatter : dateTimeFormatters) {
            try {
                dateTime = LocalDateTime.parse(dateAndTime, formatter);
                break;
            } catch (DateTimeParseException e) {
            }
            for (DateTimeFormatter dateTimeFormatter : dateFormatters) {
                try {
                    LocalDate date = LocalDate.parse(dateAndTime, dateTimeFormatter);
                    LocalTime time = LocalTime.of(00, 00, 00);
                    dateTime = LocalDateTime.of(date, time);
                    break;
                } catch (DateTimeParseException e) {
                }
            }
        }
        return dateTime;
    }

    private void printTimeInZones(List<ZonedDateTime> zones, DateTimeFormatter formatter) {
        for (ZonedDateTime zone : zones) {
            System.out.println(zone.getZone() + ": " + zone.format(formatter));
        }
    }
}
