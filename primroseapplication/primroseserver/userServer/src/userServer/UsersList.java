import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 10/22/2016.
 */

@XmlRootElement(name = "usersList")
public class UsersList {

    private List<User> users;
    private AtomicInteger userId;

    public UsersList() {
        users = new CopyOnWriteArrayList<User>();
        userId = new AtomicInteger();
    }

    @XmlElement
    @XmlElementWrapper(name = "users")
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public User find(int id) {
        User user = null;
        // Search the list -- for now, the list is short enough that
        // a linear search is ok but binary search would be better if the
        // list got to be an order-of-magnitude larger in size.
        for (User u : users) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        }
        return user;
    }

    public int add(String givenName, String password) {
        int id = userId.incrementAndGet();
        User u = new User();
        u.setGivenName(givenName);
        u.setUserName(User.generateUserName(givenName));
        u.setEmailAddress(User.generateEmailAddress(User.generateUserName(givenName)));
        u.setPassword(password);
        u.setId(id);
        users.add(u);
        return id;
    }

    @Override
    public String toString() {
        String s = "";
        for (User u : users) s += u.toString();
        return s;
    }
}
