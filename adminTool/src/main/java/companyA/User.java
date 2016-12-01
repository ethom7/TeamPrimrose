package companyA;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by evanpthompson on 11/1/2016.
 * Dates are contained as Strings to ensure proper serialization and deserialization
 * of the ISODate object as stored in the mongodb because jackson does not support the format.
 */

@XmlRootElement(name = "user")
public class User implements Comparable<User> {

    /*  Seek to next implement list of prior passwords used in prior set amount of time. (18 months)  */

    private int id;
    private String givenName;
    private String userName;
    private String password;
    private String emailAddress;
    private String passwordExpiration;
    private boolean activeUser;

    public User() {  }

    public User(String givenName, String password) {

        this.givenName = givenName;
        this.userName = generateUserName(givenName);
        this.password = password;
        this.emailAddress = generateEmailAddress(userName);
        this.passwordExpiration = passwordExpiration();

    }


    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @XmlElement
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.setPasswordExpiration(passwordExpiration());
    }

    @XmlElement
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @XmlElement
    public String getPasswordExpiration() {
        return passwordExpiration;
    }

    public void setPasswordExpiration(String passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }

    public String passwordExpiration() {
        // Create a ISODateTimeFormat string for insertion into the db. For timestamp, format "yyyy-mm-ddThh:mm:ss.nnnZ"
        DateTime now = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String timestamp = formatter.print(now);

        Timestamp ts = new Timestamp(timestamp);

        // test of password expiration set

        return ts.setDaysAhead(60);
    }

    public boolean isPasswordExpired(String passwordExpiration) {

        // Create a ISODateTimeFormat string for insertion into the db. For timestamp, format "yyyy-mm-ddThh:mm:ss.nnnZ"
        DateTime now = new DateTime(DateTimeZone.UTC);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String timestamp = formatter.print(now);

        // passwordExpiration has not been set and needs to be
        if (passwordExpiration == null) {
            return true;
        }

        Timestamp ts = new Timestamp(timestamp);
        Timestamp pe = new Timestamp(passwordExpiration);

        if (pe.compareTo(ts) > 0) {
            return true;
        }
        else {
            return false;
        }

    }

    @XmlElement
    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }

    public int compareTo(User other) {
        return this.id - other.id;
    }


    /* User Data Requirement 1.4.0 */
    // To ensure unique username generation a 4 digit alphanumeric string will be appended.
    // This will
    public String generateUserName(String givenName) {
        String lower = givenName.toLowerCase();
        String[] names = lower.split(" ");  // separate out the names, last name defined as last word in string
        String user = "";
        int minLength = 8;
        String alNum = alphaNumericStringGenerator(4).toLowerCase();

		/*  To implement truly unique ids a query to the db will have to be run and running data structure of the
		  * generated usernames to check as each user is created. Since this is computationally intense it is not
		  * implemented.
		  **/

        // account for names that are too short

        // if the given name is not 8 letters in length, add "a" to the end
        if (givenName.length() < 8) {
            user = givenName;
            while (user.length() < 8) {
                user += "a";
            }

            return user+alNum;
        }

        // if the first and last name are insufficient given name is userName
        if ((names[0].length() + names[names.length-1].length()) < 8 ) {
            user += names[0] + names[1] + names[2];

            return user+alNum;
        }

        // if first name is less than 2 add more last name
        else if (names[0].length() < 2) {

            int len = names[0].length();  // get length of last name

            user += names[0] + names[names.length-1].substring(0, (8-len));

            return user+alNum;
        }

        // if last name is less than 6 add more first name
        else if (names[names.length-1].length() < 7) {
            int len = names[names.length-1].length();  // get length of last name

            user += names[0].substring(0, (8-len)) + names[names.length-1];

            return user+alNum;
        }

        user += names[0].substring(0, 2) + names[names.length-1].substring(0, 6);

        return user+alNum;
    }

    // method to generate an email address from userName
    public static String generateEmailAddress(String userName) {
        String user = userName + "@companyA.com";

        return user.toLowerCase();
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

    /*  Utility methods  */

    public String alphaNumericStringGenerator(int length) {
        String outputString = "";

        for (int i = 0; i < length; i++) {

            // letter or number
            if (intInRange(1,2) == 1) {

                // get number 48 thru 57

                char c = (char) intInRange(48,57);

                outputString += c;

            }
            else {

                char c = (char) intInRange(65,90);
                // get letter 65 thru 90
                outputString += c;
            }
        }

        return outputString;
    }

    // Generate an integer within the range input
    public static int intInRange(int min, int max) {
        int outInt = 0;

        outInt = min + (int)(Math.random() * ((max - min) + 1));

        return outInt;
    }

    @Override
    public String toString() {
        return "User : {" +
                "id : " + id +
                ", givenName : " + givenName +
                ", userName : " + userName +
                ", password : " + password +
                ", emailAddress : " + emailAddress +
                ", passwordExpiration : " + passwordExpiration +
                ", activeUser=" + activeUser +
                " }";
    }
}