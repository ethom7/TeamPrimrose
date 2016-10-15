
public class PostalAddress {

	private String streetFirst;
	private String streetSecond;
	private String city;
	private String state;
	private String zip;
	
	public PostalAddress(String streetFirst, String city, String state, String zip) {
		this.streetFirst = streetFirst;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public PostalAddress(String streetFirst, String streetSecond, String city, String state, String zip) {
		this.streetFirst = streetFirst;
		this.streetSecond = streetSecond;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreetFirst() {
		return streetFirst;
	}

	public String getStreetSecond() {
		return streetSecond;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	private void setStreetFirst(String streetFirst) {
		this.streetFirst = streetFirst;
	}

	private void setStreetSecond(String streetSecond) {
		this.streetSecond = streetSecond;
	}

	private void setCity(String city) {
		this.city = city;
	}

	private void setState(String state) {
		this.state = state;
	}

	private void setZip(String zip) {
		this.zip = zip;
	}

	
	
	@Override
	public String toString() {
		
		String address = "address: " + streetFirst + " ";
		
		if (!(streetSecond.isEmpty())) {
			address += streetSecond + " ";
		}
		return String.format("%s \ncity: %s state: %s zip code: %s", address, city, state, zip);
	}
	
	
	
	
	
	
	
}
