package objectclasses;

public class EmergencyContact {

	private String name;
	private String relation;
	private String phoneNumber;
	private String emailAddress;
	
	public EmergencyContact(String name, String relation, String phoneNumber) {
		this.name = name;
		this.relation = relation;
		this.phoneNumber = phoneNumber;
	}
	
	public EmergencyContact(String name, String relation, String phoneNumber, String emailAddress) {
		this.name = name;
		this.relation = relation;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public String getName() {
		return name;
	}

	public String getRelation() {
		return relation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

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

	private void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public String toString() {
		String contact = "";
		if (!(emailAddress.isEmpty())) {
			contact += emailAddress + " ";
		}
		
		contact += phoneNumber;
		
		return String.format("name: %s relation: %s  contact info: %s", name, relation, contact);
	}
	
	
}
