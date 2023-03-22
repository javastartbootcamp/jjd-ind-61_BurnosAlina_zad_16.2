package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final List<String> DATE_TIME_FORMATTERS = Arrays.asList(
            "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy HH:mm:ss"
    );
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        LocalDateTime dateAndTime = getDateAndTime(scanner);
        printDates(dateAndTime);
    }

    private void printDates(LocalDateTime dateAndTime) {
        List<Zone> zones = Arrays.asList(
                new Zone(ZoneId.systemDefault(), "Czas lokalny"),
                new Zone(ZoneId.of("UTC"), "UTC"),
                new Zone(ZoneId.of("Europe/London"), "Londyn"),
                new Zone(ZoneId.of("America/Los_Angeles"), "Los Angeles"),
                new Zone(ZoneId.of("Australia/Sydney"), "Sydney"));
        ZonedDateTime currentTime = dateAndTime.atZone(ZoneId.systemDefault());
        printTimeInZones(zones, currentTime);
    }

    private LocalDateTime getDateAndTime(Scanner scanner) {
        System.out.println("Podaj datę:");
        String dateAndTime = scanner.nextLine();
        return parseDate(dateAndTime);
    }

    private LocalDateTime parseDate(String dateAndTime) {
        List<DateTimeFormatter> dateFormatters = new ArrayList<>();
        dateFormatters.add(DateTimeFormatter.ofPattern(DATE_PATTERN));
        for (String format : DATE_TIME_FORMATTERS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDateTime.parse(dateAndTime, formatter);
            } catch (DateTimeParseException e) {
                // noop
            }
        }
        for (DateTimeFormatter dateTimeFormatter : dateFormatters) {
            try {
                LocalDate date = LocalDate.parse(dateAndTime, dateTimeFormatter);
                LocalTime time = LocalTime.of(0, 0, 0);
                return LocalDateTime.of(date, time);
            } catch (DateTimeParseException e) {
                // noop
            }
        }
        return null;
    }

    private void printTimeInZones(List<Zone> zones, ZonedDateTime currentTime) {
        for (Zone zone : zones) {
            ZonedDateTime timeAtGivenZone = currentTime.withZoneSameInstant(zone.getZoneId());
            String formattedTime = timeAtGivenZone.format(OUTPUT_FORMATTER);
            System.out.printf("%s: %s\n", zone.getName(), formattedTime);
        }
    }
}
