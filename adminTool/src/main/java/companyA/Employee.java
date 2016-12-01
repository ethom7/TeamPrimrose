package companyA;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 11/1/2016.
 *
 * Dates are contained as Strings to ensure proper serialization and deserialization
 * of the ISODate object as stored in the mongodb because jackson does not support the format.
 */

@XmlRootElement(name = "employee")
public class Employee implements Comparable<Employee> {

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String givenName;
    private String socialSecurityNumber;
    private String dob;
    private PostalAddress postalAddress;
    private String phoneNumber;
    private EmergencyContact emergencyContact;
    private String hireDate;
    private DirectReports directReports;
    private boolean activeEmployee;  // System Requirement 4.1.0

    public Employee() {     }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @XmlElement
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @XmlElement
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @XmlElement
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    @XmlElement
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @XmlElement
    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    @XmlElement
    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    @XmlElement
    public DirectReports getDirectReports() {
        return directReports;
    }

    public void setDirectReports(DirectReports directReports) {
        this.directReports = directReports;
    }

    @XmlElement
    public boolean isActiveEmployee() {
        return activeEmployee;
    }

    public void setActiveEmployee(boolean activeEmployee) {
        this.activeEmployee = activeEmployee;
    }

    public int compareTo(Employee other) {
        return this.id - other.id;
    }

    // method will return a timestamp as a string for a set year, month, and day. used for dob and hiredate
    public String dateTimestamp(int dobYear, int dobMonth, int dobDay) {
        DateTime dob = new DateTime(dobYear, dobMonth, dobDay, 0, 0, DateTimeZone.UTC);
        DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        String dobTimestamp = formatter.print(dob);

        // test of dobTimeStamp
        return dobTimestamp;
    }

    @Override
    public String toString() {
        return "Employee : {" +
                "id : " + id +
                ", firstName : " + firstName +
                ", middleName : " + middleName +
                ", lastName : " + lastName +
                ", givenName : " + givenName +
                ", socialSecurityNumber : " + socialSecurityNumber +
                ", dob : " + dob +
                ", postalAddress : " + postalAddress +
                ", phoneNumber : " + phoneNumber +
                ", emergencyContact : " + emergencyContact +
                ", hireDate : " + hireDate +
                ", directReports : " + directReports +
                ", activeEmployee : " + activeEmployee +
                '}';
    }
}
