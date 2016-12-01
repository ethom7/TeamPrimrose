package companyA;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "postalAddress")
public class PostalAddress {

	private String street;
	private String city;
	private String state;
	private String zip;

	/*  No-args constructor  */
	public PostalAddress() { }
	
	public PostalAddress(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	@XmlElement
	public String getStreet() {
		return street;
	}

	@XmlElement
	public String getCity() {
		return city;
	}

	@XmlElement
	public String getState() {
		return state;
	}

	@XmlElement
	public String getZip() {
		return zip;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}



	/* Utility method will accept postalAddress as string along with a delimiter and populate a PostalAddress accordingly.
    buildPostalAddress("123 Street Name;City Name;State Name;Zip Code", ";");
     */
	public PostalAddress buildPostalAddress(String postalAddress, String delimiter) {
		String[] parts = postalAddress.split(delimiter);
		PostalAddress pa = new PostalAddress();
		if (parts.length == 4) {
			pa.setStreet(parts[0]);
			pa.setCity(parts[1]);
			pa.setState(parts[2]);
			pa.setZip(parts[3]);
		}
		return pa;
	}

	@Override
	public String toString() {
		return "{ " +
				"street : " + street +
				", city : " + city +
				", state : " + state +
				", zip : " + zip +
				" }";

	}

}
