import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by evanpthompson on 10/22/2016.
 */

@XmlRootElement(name = "emergencycontact")
public class EmergencyContact {

    private String name;
    private String relation;
    private String contactPhoneNumber;
    private String emailAddress;

    public EmergencyContact() {}

    public EmergencyContact(String name, String relation, String contactPhoneNumber) {
        this.name = name;
        this.relation = relation;
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public EmergencyContact(String name, String relation, String contactPhoneNumber, String emailAddress) {
        this.name = name;
        this.relation = relation;
        this.contactPhoneNumber = contactPhoneNumber;
        this.emailAddress = emailAddress;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getRelation() {
        return relation;
    }

    @XmlElement
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    @XmlElement
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setRelationship(String relation) {
        this.relation = relation;
    }

    private void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }


    public String toString() {
        String contact = "";
        if (!(emailAddress.isEmpty())) {
            contact += emailAddress + " ";
        }

        contact += contactPhoneNumber;

        return String.format("name: %s relation: %s  contact info: %s", name, relation, contact);
    }
}
