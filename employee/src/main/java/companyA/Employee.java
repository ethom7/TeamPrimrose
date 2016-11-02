package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by evanpthompson on 11/1/2016.
 */

@XmlRootElement(name = "employee")
public class Employee implements Comparable<Employee> {

    private int id;
    private Object _id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String givenName;
    private String socialSecurityNumber;
    private String dob;
    private PostalAddress postalAddress;
    private String phoneNumber;
    private EmergencyContact emergencyContact;
    private Date hireDate;
    private DirectReports directReports;
    private boolean activeEmployee;

    public Employee() {     }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
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
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
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

    @Override
    public String toString() {
        return "Employee : {" +
                "id : " + id +
                ", _id : " + _id +
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
