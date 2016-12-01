package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by evanpthompson on 11/1/2016.
 */

@XmlRootElement( name = "directReports")
public class DirectReports {

    private ArrayList<EmployeeReport> directReports;

    public DirectReports() {
        directReports = new ArrayList<EmployeeReport>();
    }

    @XmlElement
    public ArrayList<EmployeeReport> getDirectReports() {
        return this.directReports;
    }

    public void setDirectReports(ArrayList<EmployeeReport> directReports) {
        this.directReports = directReports;
    }

    @Override
    public String toString() {
        String s = "";
        for (EmployeeReport emp : directReports) {
            s += emp.toString();
        }
        return s;
    }

    public EmployeeReport find(int id) {
        EmployeeReport emp = null;

        for (EmployeeReport e : directReports) {
            if (e.getId() == id) {
                emp = e;
                break;
            }
        }
        return emp;
    }

    public void add(EmployeeReport employee) {
        directReports.add(employee);
    }

}
