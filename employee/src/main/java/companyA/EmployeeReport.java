package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 11/1/2016.
 */

@XmlRootElement( name = "employeeReport")
public class EmployeeReport {

    private int id;
    private String givenName;

    public EmployeeReport() {  }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @Override
    public String toString() {
        return "EmployeeReport : { " +
                "id : " + id +
                ", givenName : " + givenName +
                " }";
    }
}
