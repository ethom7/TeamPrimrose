package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 11/1/2016.
 */

@XmlRootElement( name = "emergencyContact")
public class EmergencyContact {

    private String contactName;
    private String relation;
    private String emergencyPhoneNumber;

    public EmergencyContact() {  }


    @XmlElement
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @XmlElement
    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @XmlElement
    public String getEmergencyPhoneNumber() {
        return emergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }


    /* Utility method will accept emergencyContact as string along with a delimiter and populate an EmergencyContact accordingly.
    buildEmergencyContact("This Contact Name;Relationship;Contact Phone number", ";");
     */
    public EmergencyContact buildEmergencyContact(String emergencyContact, String delimiter) {
        String[] parts = emergencyContact.split(delimiter);
        EmergencyContact ec = null;
        if (parts.length == 3) {
            ec.setContactName(parts[0]);
            ec.setRelation(parts[1]);
            ec.setEmergencyPhoneNumber(parts[2]);
        }
        return ec;
    }


    @Override
    public String toString() {
        return "{ " +
                "contactName : " + contactName +
                ", relation : " + relation +
                ", emergencyPhoneNumber : " + emergencyPhoneNumber +
                " }";

    }
}
