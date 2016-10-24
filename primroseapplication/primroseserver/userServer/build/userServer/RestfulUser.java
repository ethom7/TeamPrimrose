/**
 * Created by evanpthompson on 10/22/2016.
 */

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/userResources")
public class RestfulUser extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(UsersRS.class);
        return set;
    }
}

