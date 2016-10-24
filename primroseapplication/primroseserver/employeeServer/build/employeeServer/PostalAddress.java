import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 10/22/2016.
 */

@XmlRootElement(name = "postaladdress")
public class PostalAddress {

    private String streetFirst;
    private String streetSecond;
    private String city;
    private String state;
    private String zip;

    public PostalAddress() {}

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

    @XmlElement
    public String getStreetFirst() {
        return streetFirst;
    }

    @XmlElement
    public String getStreetSecond() {
        return streetSecond;
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
