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
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by evanpthompson on 11/1/2016.
 * RESTful User Class.
 */

@Path("/")
public class UserRS {

    private static MongoCollection<Document> collection;  // MongoCollection variable that is instantiated with the no-arg constructor.

    // Class is initialized by the RestfulInventory class when the war file is launched within the Tomcat server.
    public UserRS() {
        collection = MongoConnector.getInstance().getMongoCollection("user");
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
        User user = find(id);
        return toRequestedType(id,"application/json");
    }

    // Get all inventory from the database collection in plain text.
    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        ArrayList<User> invList = toArrayList();
        return invList.toString();
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

        if (password.equals(verificationPassword)) {
            msg = "Entered password fields do not match.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(givenName, hashed);
        user.setId(id);

        // .append("passwordExpiration", user.);  to implement

        try {
            String usrString = mapper.writeValueAsString(user);
            Document usrDoc = new Document("id", id)
                    .append("givenName", givenName)
                    .append("userName", user.getUserName())
                    .append("password", user.getPassword())
                    .append("emailAddress", user.getEmailAddress())
                    .append("activeUser", true);
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


    // Updates the item in inventory by id number.
    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id, @FormParam("userName") String userName,
                           @FormParam("password") String password,
                           @FormParam("verificationPassword") String verificationPassword, @FormParam("activeUser") boolean activeUser) {



        String msg = null;
        if (id == 0) {
            msg = "A required id is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        // Build a working check on the remaining parameters prior to update.
        // So that if fields are empty they either do not get updated or updates are not allowed.


        /*if (productNumber == null && itemDescription == null && itemCost == null && itemPrice == null && itemCount == null) {
            msg = "A required parameter is missing.Nothing to update.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }*/

        if (password.equals(verificationPassword)) {
            msg = "Entered password fields do not match.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();

        user.setPassword(hashed);
        user.setActiveUser(activeUser);

        Bson filter = eq("id", id); // filter by matching id
        Bson projection = Projections.exclude("_id");  // exclude the object id from the return

        // return an iterable cursor object
        MongoCursor<Document> itr = collection.find(filter)
                .projection(projection)
                .iterator();

        // always use when iterating with MongoCursor
        try {
            while (itr.hasNext()) {
                Document cur = itr.next();

                if (cur.getInteger("id").equals(id)) {

                    collection.findOneAndReplace(cur, new Document("id", id)
                            .append("userName", userName)
                            .append("password", user.getPassword())
                            .append("passwordExpiration", user.getPasswordExpiration())
                            .append("activeUser", user.isActiveUser()));


                    msg = "update has been made.\n";  // update the message string to confirm update made.
                    return Response.ok(msg, "text/plain").build();  // return the response

                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        msg = "Something went wrong. Please try again.";
        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
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




    private boolean checkHashedPassword(String userName, String password) {
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
}