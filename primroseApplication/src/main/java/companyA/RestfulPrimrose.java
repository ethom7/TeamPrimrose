package companyA;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by evanpthompson on 11/18/2016.
 */

@ApplicationPath("/")
public class RestfulPrimrose extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(EmployeeRS.class);
        set.add(InventoryRS.class);
        set.add(UserRS.class);
        return set;
    }
}
