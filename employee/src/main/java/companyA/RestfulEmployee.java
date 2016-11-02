package companyA;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by evanpthompson on 11/1/2016.
 */
@ApplicationPath("/employeeResources")
public class RestfulEmployee extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(EmployeeRS.class);
        return set;
    }
}
