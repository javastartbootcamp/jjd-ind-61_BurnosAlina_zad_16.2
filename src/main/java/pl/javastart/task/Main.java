package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        ZonedDateTime polandTime = ZonedDateTime.of(dateAndTime, ZoneId.of("Europe/Warsaw"));
        ZonedDateTime utcTime = polandTime.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime londonTime = polandTime.withZoneSameInstant(ZoneId.of("Europe/London"));
        ZonedDateTime laTime = polandTime.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
        ZonedDateTime sydneyTime = polandTime.withZoneSameInstant(ZoneId.of("Australia/Sydney"));
        System.out.println("UTC: " + utcTime.format(formatter));
        System.out.println("Londyn: " + londonTime.format(formatter));
        System.out.println("Los Angeles: " + laTime.format(formatter));
        System.out.println("Sydney: " + sydneyTime.format(formatter));
    }

    private LocalDateTime getDateAndTime(Scanner scanner) {
        LocalDateTime dateTime;
        System.out.println("Podaj datę:");
        String dateAndTime = scanner.nextLine();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        try {
            dateTime = LocalDateTime.parse(dateAndTime, formatter1);
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(dateAndTime, formatter2);
                LocalTime time = LocalTime.of(00, 00, 00);
                dateTime = LocalDateTime.of(date, time);
            } catch (DateTimeParseException f) {
                dateTime = LocalDateTime.parse(dateAndTime, formatter3);
            }
        }
        return dateTime;
    }
}
