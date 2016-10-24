/**
 * Created by evanpthompson on 10/22/2016.
 */

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@XmlRootElement(name = "employee")
public class Employee {

    public int id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String socialSecurityNumber;
    public String dob;
    public PostalAddress postalAddress;
    public EmergencyContact emergencyContact;
    public String phoneNumber;


    public Employee() {  }

    public Employee(String firstName, String middleName, String lastName, String socialSecurityNumber, String dob) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.dob = dob;
    }

    public Employee(String firstName, String middleName, String lastName, String socialSecurityNumber, 
                    PostalAddress postalAddress, String phoneNumber) {


        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.postalAddress = postalAddress;
        this.phoneNumber = phoneNumber;
    }

    public Employee(String firstName, String middleName, String lastName, String socialSecurityNumber,
                    PostalAddress postalAddress, EmergencyContact emergencyContact, String phoneNumber) {


        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.postalAddress = postalAddress;
        this.emergencyContact = emergencyContact;
        this.phoneNumber = phoneNumber;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public String getMiddleName() {
        return middleName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }


    public String getDob() {
        return dob;
    }

    @XmlElement
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    @XmlElement
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    @XmlElement
    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    @XmlElement
    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setId(int id) {
        this.id = id;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    void setMiddleName(String middleName) {
        this.middleName = middleName;
    }


    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    void setDob(String dob) {
        this.dob = dob;
    }

    void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }


    void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }


    void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }


    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String fullNameAsString() {
        return String.format("%s %s %s", firstName, middleName, lastName);
    }




    @Override
    public String toString() {
        return String.format("%s %s %s: phone number: %s\n%s\nEmergency Contact: %s", firstName, middleName, lastName, phoneNumber, postalAddress,emergencyContact);
    }

}
