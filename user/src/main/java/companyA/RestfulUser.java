package companyA;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by evanpthompson on 11/1/2016.
 * Initializes the RESTful class when the war file is loaded into the Tomcat webserver.
 */

@ApplicationPath("/userResources")
public class RestfulUser extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(UserRS.class);
        return set;
    }
}