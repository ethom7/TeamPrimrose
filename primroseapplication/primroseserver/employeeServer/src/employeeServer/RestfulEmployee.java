/**
 * Created by evanpthompson on 10/22/2016.
 */

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/employeeResources")
public class RestfulEmployee extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(EmployeesRS.class);
        return set;
    }
}

