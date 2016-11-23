package companyA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Created by evanpthompson on 11/22/2016.
 * Generate a date within a specified range.
 */
public class DateGenerator {

    private LocalDate min;
    private LocalDate max;
    private Random random;


    public DateGenerator(LocalDate min, LocalDate max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    public LocalDate date() {
        int intMin = (int) min.toEpochDay();
        int intMax = (int) max.toEpochDay();
        long randomDay = intMin + random.nextInt(intMax - intMin);
        return LocalDate.ofEpochDay(randomDay);
    }

    public String generateRandomDate() {
        LocalDate randomDate = this.date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
        String text = randomDate.format(formatter);
        String out = String.format("%s", text);
        return out;

    }

    @Override
    public String toString() {

        return "DateGenerator: [ " + " min: " + min + " max: " + max;
    }
}
