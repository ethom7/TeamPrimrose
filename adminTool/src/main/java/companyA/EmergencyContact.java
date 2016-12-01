package companyA;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "emergencyContact")
public class EmergencyContact {


	private String contactName;
	private String relation;
	private String emergencyPhoneNumber;

	/*  no-args constructor  */
	public EmergencyContact() {  }
	
	public EmergencyContact(String contactName, String relation, String emergencyPhoneNumber) {
		this.contactName = contactName;
		this.relation = relation;
		this.emergencyPhoneNumber = emergencyPhoneNumber;
	}
	


	@XmlElement
	public String getContactName() {
		return contactName;
	}

	@XmlElement
	public String getRelation() {
		return relation;
	}

	@XmlElement
	public String getEmergencyPhoneNumber() {
		return emergencyPhoneNumber;
	}

	private void setContactName(String name) {
		this.contactName = contactName;
	}

	private void setRelation(String relation) {
		this.relation = relation;
	}

	private void setEmergencyPhoneNumber(String phoneNumber) {
		this.emergencyPhoneNumber = emergencyPhoneNumber;
	}


	/* Utility method will accept emergencyContact as string along with a delimiter and populate an EmergencyContact accordingly.
    buildEmergencyContact("This Contact Name;Relationship;Contact Phone number", ";");
     */
	public EmergencyContact buildEmergencyContact(String emergencyContact, String delimiter) {
		String[] parts = emergencyContact.split(delimiter);
		EmergencyContact ec = new EmergencyContact();
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