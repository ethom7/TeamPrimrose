package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;



/**
 * Created by evanpthompson on 11/1/2016.
 */

@XmlRootElement(name = "user")
public class User implements Comparable<User> {

    private int id;
    private Object _id;
    private String givenName;
    private String userName;
    private String password;
    private String emailAddress;
    private Date passwordExpiration;
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
    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
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
        this.setPasswordExpiration();
    }

    @XmlElement
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @XmlElement
    public Date getPasswordExpiration() {
        return passwordExpiration;
    }

    public void setPasswordExpiration() {
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("America/Chicago");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(now, zoneId);
        ZonedDateTime zdtLater = zdt.plusDays(60);
        Date utilDate = Date.from( zdtLater.toInstant() );
        this.passwordExpiration = utilDate;
    }

    public Date passwordExpiration() {
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("America/Chicago");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(now, zoneId);
        ZonedDateTime zdtLater = zdt.plusDays(60);
        Date utilDate = Date.from( zdtLater.toInstant() );
        return utilDate;
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


    /* Requirement 1.0.4 */
    public String generateUserName(String givenName) {
        String[] names = givenName.split(" ");  // separate out the names, last name defined as last word in string
        String user = "";
        int minLength = 8;

		/*  to implement unique ids a query to the db will have to be run  */

        // acount for names that are too short

        // if the given name is not 8 letters in length, add "a" to the end
        if (givenName.length() < 8) {
            user = givenName;
            while (user.length() < 8) {
                user += "a";
            }



            return user;
        }

        // if first name is less than 2 add more last name
        if (names[0].length() < 2) {

            int len = names[0].length();  // get length of last name

            user += names[0] + names[names.length-1].substring(0, (8-len));

            return user;
        }

        // if last name is less than 6 add more first name
        if (names[names.length-1].length() < 7) {
            int len = names[names.length-1].length();  // get length of last name

            user += names[0].substring(0, (8-len)) + names[names.length-1];

            return user;
        }

        user += names[0].substring(0, 2) + names[names.length-1].substring(0, 6);

        return user.toLowerCase();
    }

    /*  method to generate an email address from userName  */
    public static String generateEmailAddress(String userName) {
        String user = userName + "@companyA.com";

        return user.toLowerCase();
    }

    @Override
    public java.lang.String toString() {
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