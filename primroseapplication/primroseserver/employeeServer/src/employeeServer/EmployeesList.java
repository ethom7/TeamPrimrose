import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 10/22/2016.
 */

@XmlRootElement(name = "employeesList")
public class EmployeesList {

    private List<Employee> employees;
    private AtomicInteger employeeId;

    public EmployeesList() {
        employees = new CopyOnWriteArrayList<Employee>();
        employeeId = new AtomicInteger();
    }

    @XmlElement
    @XmlElementWrapper(name = "employees")
    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public Employee find(int id) {
        Employee employee = null;
        // Search the list -- for now, the list is short enough that
        // a linear search is ok but binary search would be better if the
        // list got to be an order-of-magnitude larger in size.
        for (Employee e : employees) {
            if (e.getId() == id) {
                employee = e;
                break;
            }
        }
        return employee;
    }

    public int add(String firstName, String middleName, String lastName, String socialSecurityNumber, String dob) {
        int id = employeeId.incrementAndGet();
        Employee e = new Employee();
        e.setFirstName(firstName);
        e.setMiddleName(middleName);
        e.setLastName(lastName);
        e.setSocialSecurityNumber(socialSecurityNumber);
        e.setDob(dob);
        e.setId(id);
        employees.add(e);
        return id;
    }

    @Override
    public String toString() {
        String s = "";
        for (Employee e : employees) s += e.toString();
        return s;
    }

}
