package objectclasses;


import java.util.Date;

public class Employee {
	
	private int employeeId;
	private String firstName;
	private String middleName;
	private String lastName;
	private int socialSecurityNumber;
	private Date dateOfBirth;
	private String dob;
	private PostalAddress postalAddress;
	private EmergencyContact emergencyContact;
	private String phoneNumber;
	
	
	public Employee() {  }
	
	public Employee(String firstName, String middleName, String lastName, int socialSecurityNumber, Date dateOfBirth,
			PostalAddress postalAddress, String phoneNumber) {
		
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.dateOfBirth = dateOfBirth;
		this.postalAddress = postalAddress;
		this.phoneNumber = phoneNumber;
	}
	
	public Employee(String firstName, String middleName, String lastName, int socialSecurityNumber, Date dateOfBirth,
			PostalAddress postalAddress, EmergencyContact emergencyContact, String phoneNumber) {
		
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.dateOfBirth = dateOfBirth;
		this.postalAddress = postalAddress;
		this.emergencyContact = emergencyContact;
		this.phoneNumber = phoneNumber;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public String getLastName() {
		return lastName;
	}

	public String getDob() {
		return dob;
	}

	public int getSocialSecurityNumber() {
		return socialSecurityNumber;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public PostalAddress getPostalAddress() {
		return postalAddress;
	}


	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setSocialSecurityNumber(int socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public void setPostalAddress(PostalAddress postalAddress) {
		this.postalAddress = postalAddress;
	}


	public void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String fullNameAsString() {
		return String.format("%s %s %s", firstName, middleName, lastName);
	}
	
	
	
	
	@Override
	public String toString() {
		return String.format("%s %s %s\nphone number: %s\n%s\nEmergency Contact: %s\n", firstName, middleName, lastName, phoneNumber, postalAddress,emergencyContact);
	}
	
	
	
}
