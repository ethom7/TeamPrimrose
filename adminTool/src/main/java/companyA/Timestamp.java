package companyA;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by evanpthompson on 11/6/2016.
 * Timestamp format is "2016-11-07T04:59:22.120Z" "yyyy-mm-ddThh:mm:ss.nnnZ"
 */
public class Timestamp implements Comparable<Timestamp> {

    private String timestampString;
    private DateTime dateTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    public Timestamp() {}


    public Timestamp(String timestampString) {
        this.timestampString = timestampString;

        int[] parts = parseTimestampString(timestampString);

        this.year = parts[0];
        this.month = parts[1];
        this.day = parts[2];
        this.hour = parts[3];
        this.minute = parts[4];
        this.second = parts[5];
        this.millisecond = parts[6];

        this.dateTime = new DateTime(year, month, day, hour, minute, second, millisecond, DateTimeZone.UTC);

    }

    public Timestamp(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        this.timestampString = buildTimestamp(year, month, day, hour, minute, second, millisecond);
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(int millisecond) {
        this.millisecond = millisecond;
    }


    // method will return a timestamp as a string a set number of days from now. used for passwordExpiration
    public String setDaysAhead(int number) {
        DateTime now = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String timestamp = formatter.print(now.plusDays(number));
        return timestamp;
    }

    // method will return a timestamp as a string a set number of minutes from now. used for sessionAuth expiration
    public String setMinutesAhead(int number) {
        DateTime now = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String timestamp = formatter.print(now.plusMinutes(number));
        return timestamp;
    }

    // method will return a timestamp as a string for a set year, month, and day. used for dob and hiredate
    public String dateTimestamp(int dobYear, int dobMonth, int dobDay) {
        DateTime dob = new DateTime(dobYear, dobMonth, dobDay, 0, 0, DateTimeZone.UTC);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String dobTimestamp = formatter.print(dob);

        // test of dobTimeStamp
        return dobTimestamp;
    }

    private String buildTimestamp(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        return String.format("%s-%s-%sT%s:%s:%s.%sZ", year, month, day, hour, minute, second, millisecond);
    }

    // Timestamp format is "2016-11-07T04:59:22.120Z"
    private int[] parseTimestampString(String timestamp) {
        String[] timedateParts = timestamp.split("T");
        String[] dateParts = timedateParts[0].split("-");
        String[] timeMilliParts = timedateParts[1].split("\\.");
        String[] timeParts = timeMilliParts[0].split(":");
        String[] milliPart = timeMilliParts[1].split("Z");


        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        int second = Integer.parseInt(timeParts[2]);
        int milli = Integer.parseInt(milliPart[0]);

        int[] parts = {year, month, day, hour, minute, second, milli};

        return parts;
    }

    public int[] returnParts() {
        int[] parts = {year, month, day, hour, minute, second, millisecond};

        return parts;
    }

    // this timestamp is before other timestamp return -1,
    // else if this timestamp is after other timestamp return 1,
    // else if this timestamp equals other timestamp return 0
    public int compareTo(Timestamp other) {

        if (this.dateTime.isBefore(other.dateTime)) {
            return -1;
        }
        else if (this.dateTime.isAfter(other.dateTime)) {
            return 1;
        }
        else if (this.dateTime.isEqual(other.dateTime)) {
            return 0;
        }
        else {
            return -2;
        }


    }

    @Override
    public String toString() {
        return "Timestamp { " +
                " month : " + month +
                ", day : " + day +
                ", year : " + year +
                ", hour : " + hour +
                ", minute : " + minute +
                ". second : " + second +
                ", millisecond : " + millisecond +
                " }";
    }
}
