package companyA;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by evanpthompson on 11/1/2016.
 * RESTful User Class.
 */

@Path("/user")
public class UserRS {

    private static MongoCollection<Document> collection;  // MongoCollection variable that is instantiated with the no-arg constructor.
    private static MongoCollection<Document> userLog;  // MongoCollection variable that is instantiated with the no-arg constructor.

    // Class is initialized by the RestfulInventory class when the war file is launched within the Tomcat server.
    // User Data Requirement 1.0.0
    public UserRS() {
        collection = MongoConnector.getInstance().getMongoCollection("user");

        /*  2.1.0 and 2.2.0 for user access  */
        userLog = MongoConnector.getInstance().getMongoCollection("user-log");
    }

    // Get all items in the user database collection by json
    @GET
    @Path("/json")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson() {

        ArrayList<User> invList = toArrayList();
        return Response.ok(toJson(invList), "application/json").build();
    }


    // Get User item by id from the database collection
    @GET
    @Path("/json/{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson(@PathParam("id") int id) {
        Bson filter = eq("id", id); // filter by matching id
        Bson projection = Projections.exclude("_id");  // exclude the object id from the return

        // return an iterable cursor object
        MongoCursor<Document> itr = collection.find(filter)
                .iterator();

        ObjectMapper mapper = new ObjectMapper();

        // always use when iterating with MongoCursor
        try {

            while (itr.hasNext()) {
                Document cur = itr.next();

                if (cur.getInteger("id").equals(id)) {

                    return Response.ok(cur.toJson(), "application/json").build();  // return the response

                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        String msg = "Invalid id.";

        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.APPLICATION_JSON).build();
    }

    // Get all inventory from the database collection in plain text.
    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        ArrayList<User> invList = toArrayList();
        return invList.toString();
    }

    // Get User item by id from the database collection
    @GET
    @Path("/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getUser(@PathParam("id") int id,
                            @CookieParam("authorized-session") String timestamp) {
        User user = find(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        String output = "Last visit: " + timestamp + "\r\n\r\n";
        output += "User: " + user.getUserName() + " \r\nemail address: " + user.getEmailAddress();

        return Response.ok(output, "text/plain").build();
    }



    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response create(@FormParam("id") int id, @FormParam("givenName") String givenName,
                           @FormParam("password") String password,
                           @FormParam("verificationPassword") String verificationPassword) {

        ObjectMapper mapper = new ObjectMapper();

        String msg = null;
        if (givenName == null || password == null || verificationPassword == null) {
            msg = "A required parameter is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        if (!password.equals(verificationPassword)) {
            msg = "Entered password fields do not match.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(givenName, hashed);
        user.setId(id);
        user.passwordExpiration();
        user.setActiveUser(true);

        // .append("passwordExpiration", user.);  to implement

        try {
            String usrString = mapper.writeValueAsString(user);
            Document usrDoc = Document.parse(usrString);
            collection.insertOne(usrDoc);
            msg = "User " + id + " created.\n";
            return Response.ok(msg, "text/plain").build();
        }
        catch (JsonProcessingException e) {
            e.getStackTrace();
        }
        msg = "Something went wrong.\n";
        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
    }


    /* method for completing user login.
    * User Access and Authentication Requirement 2.0.0
    */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/userLogin")
    public Response doLogin(@FormParam("userName") String userName, @FormParam("password") String password, @FormParam("ipAdd") String ipAdd) {
        String msg = "";

        DateTime dt = new DateTime();  // for future use to implement authentication expiration.
        String lastVisit = dt.toString();


        /*  2.1.0 and 2.2.0 for user access  */

        if (userName == null || password == null) {
            msg = "A required field is missing.\n";
            // 3.0.0 failed user access
            Document logEntry = new Document("timestamp", lastVisit)
                                    .append("ipAddress", ipAdd)
                                    .append("userName", userName)
                                    .append("attemptOutcome", "fail")
                                    .append("reason", "username or password blank.");
            userLog.insertOne(logEntry);
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        userName = userName.toLowerCase();

        User user = findUser(userName, password);

        if (user == null) {
            msg = "Username and/or password do not match.\n";
            // 3.0.0 failed user access
            Document logEntry = new Document("timestamp", lastVisit)
                    .append("ipAddress", ipAdd)
                    .append("userName", userName)
                    .append("attemptOutcome", "fail")
                    .append("reason", "username or password do not match.");
            userLog.insertOne(logEntry);

            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }
        // 4.1.0 Login to the application is restricted to current employees that have been setup within
        // the authorized user database.
        else if (user.isActiveUser() == false) {
            msg = "User is not active.\n";
            // 3.0.0 failed user access
            Document logEntry = new Document("timestamp", lastVisit)
                    .append("ipAddress", ipAdd)
                    .append("userName", userName)
                    .append("attemptOutcome", "fail")
                    .append("reason", "inactive user but matching credentials.");
            userLog.insertOne(logEntry);
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }
        else {
            // successful user access
            Document logEntry = new Document("timestamp", lastVisit)
                    .append("ipAddress", ipAdd)
                    .append("userName", userName)
                    .append("attemptOutcome", "success");
            userLog.insertOne(logEntry);

            String output = "User logged in successfully <a href=\"http://192.168.1.211:8080/primroseApplication/user/" + user.getId() + "\">" + user.getId() + "</a>";

            // update uri for service address for client when moving to a new network, ip and port
            URI location = URI.create("http://192.168.1.211:8080/primroseApplication/user/" + user.getId());

            msg = "User " + userName + " has been successfully logged in. " + dt.toString() + "\n" + "Id " + String.format("%d", user.getId()) + "\n";

            // for cookie return
            //return Response.created(location).entity(output).cookie(new NewCookie("authorized-session", lastVisit)).cookie(new NewCookie("id", String.format("%d", user.getId())).build();

            AuthorizedResponse authResp = new AuthorizedResponse(String.format("%d", user.getId()), lastVisit, "landing.html");
            return Response.ok(toJson(authResp), "application/json").build();
            //return Response.ok(msg, "text/plain").build();

        }


    }


    // Updates the user by id number. Allows the password or activeUser to be updated.
    // Username only required for password update.
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update")
    public Response update(@FormParam("id") int id, @FormParam("userName") String userName,
                           @FormParam("password") String password, @FormParam("currentPassword") String currentPassword,
                           @FormParam("verificationPassword") String verificationPassword, @FormParam("activeUser") boolean activeUser) {



        String msg = null;
        if (id == 0) {
            msg = "A required id is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.APPLICATION_JSON).build();
        }

        Bson filter = eq("id", id); // filter by matching id
        Bson projection = Projections.exclude("_id");  // exclude the object id from the return

        // return an iterable cursor object
        MongoCursor<Document> itr = collection.find(filter)
                .iterator();

        ObjectMapper mapper = new ObjectMapper();

        // always use when iterating with MongoCursor
        try {
            while (itr.hasNext()) {
                Document cur = itr.next();

                if (cur.getInteger("id").equals(id)) {

                    try {

                        User user = new User();
                        user = mapper.readValue(cur.toJson(), User.class);


                        if (id != 0) {
                            user.setId(id);
                        }
                        if (password != null) {
                            // Verify user prior to changes being made
                            if (!checkHashedPassword(userName, currentPassword)) {
                                msg = "Entered username and password do not match for user.\n";
                                return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.APPLICATION_JSON).build();
                            }
                            if (!password.equals(verificationPassword)) {
                                msg = "Entered password fields do not match.\n";
                                return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.APPLICATION_JSON).build();
                            }

                            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                            user.setPassword(hashed);
                            user.setPasswordExpiration(user.passwordExpiration());
                        }

                        //if (activeUser != user.isActiveUser()) {
                         //   user.setActiveUser(activeUser);
                        //}

                        String usrString = mapper.writeValueAsString(user);
                        Document usrDoc = Document.parse(usrString);

                        collection.findOneAndReplace(filter, usrDoc);
                        msg = "update has been made.\n";  // update the message string to confirm update made.


                        return Response.status(Response.Status.OK).entity(msg).type(MediaType.APPLICATION_JSON).build();  // return the response


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        msg = "Something went wrong. Please try again.";
        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.APPLICATION_JSON).build();
    }


    // Delete user from the database collection by matched id
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        String msg = null;

        // run query to delete from db collection as long as id has been input.
        if (id != 0) {
            collection.findOneAndDelete(eq("id", id));
            msg = "User " + id + " has been deleted.\n";
            return Response.ok(msg, "text/plain").build();
        }
        else {
            msg = "No ID was provided. Nothing to do.";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }


    }



    // Method accepts userName and password input, will return true if password is valid for the user.
    // Password entry is in plain text.
    private static boolean checkHashedPassword(String userName, String password) {
        User user = null;
        ObjectMapper mapper = new ObjectMapper();

        Bson filter = eq("userName", userName);
        Bson projection = Projections.exclude("_id");

        MongoCursor<Document> itr = collection.find(filter)
                .projection(projection)
                .iterator();

        try {
            while (itr.hasNext()) {
                Document cur = itr.next();
                String hashed = cur.getString("password");
                if (BCrypt.checkpw(password, hashed)) {

                    try {
                        user = mapper.readValue(cur.toJson(), User.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        return false;

    }

    private User findUser(String userName, String password) {

        //PublicUser pubUser = null;
        User user = null;

        ObjectMapper mapper = new ObjectMapper();

        Bson filter = eq("userName", userName);
        Bson projection = Projections.exclude("_id");

        MongoCursor<Document> itr = collection.find(filter)
                .projection(projection)
                .iterator();

        try {
            while (itr.hasNext()) {
                Document cur = itr.next();
                String hashed = cur.getString("password");
                if (BCrypt.checkpw(password, hashed)) {

                    try {
                        user = mapper.readValue(cur.toJson(), User.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    return user;
                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        return user;
    }


    /*  Utility Methods  */

    // method adds all users to an arraylist from the database collection.
    // For scalability will need to accept optional query parameters to give a starting index and number to return.
    private ArrayList<User> toArrayList() {
        Bson projection = Projections.exclude("_id");
        List<Document> all = collection.find().projection(projection).into(new ArrayList<Document>());

        ArrayList<User> userList = new ArrayList<User>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            for (Document cur : all) {
                User user = new User();

                user = mapper.readValue(cur.toJson(), User.class);
                userList.add(user);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    // return the matching inventory item from the inventory array.
    private User find(int id) {

        ArrayList<User> userList = toArrayList();

        User usr = null;
        for (User u : userList) {
            if (u.getId() == id) {
                usr = u;
                break;
            }
        }
        return usr;
    }

    private Response toRequestedType(int id, String type) {
        ArrayList<User> invList = toArrayList();

        User user = find(id);

        if (user == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
        }
        else if (type.contains("json")) {
            return Response.ok(toJson(user), type).build();
        }
        else {
            return Response.ok(user, type).build();  // default for xml, not operational.
        }
    }

    // convert user item to json
    private String toJson(User user) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(user);
        }
        catch (Exception e) {

        }
        return json;
    }

    // convert the user arraylist to json
    private String toJson(ArrayList<User> userList) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(userList);
        }
        catch (Exception e) {

        }
        return json;
    }

    private String toJson(AuthorizedResponse auth) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(auth);
        }
        catch (Exception e) {

        }
        return json;
    }
}