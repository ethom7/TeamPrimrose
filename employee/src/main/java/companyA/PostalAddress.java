package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 11/1/2016.
 */

@XmlRootElement(name = "postalAddress")
public class PostalAddress {

    private String street;
    private String city;
    private String state;
    private String zip;

    /*  No-args constructor  */
    public PostalAddress() { }

    @XmlElement
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @XmlElement
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlElement
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlElement
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    /* Utility method will accept postalAddress as string along with a delimiter and populate a PostalAddress accordingly.
    buildPostalAddress("123 Street Name;City Name;State Name;Zip Code", ";");
     */
    public PostalAddress buildPostalAddress(String postalAddress, String delimiter) {
        String[] parts = postalAddress.split(delimiter);
        PostalAddress pa = null;
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
