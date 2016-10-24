import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.servlet.ServletContext;

/**
 * Created by evanpthompson on 10/22/2016.
 */


@Path("/users")
public class UsersRS {


    @Context
    private ServletContext sctx;          // dependency injection
    private static UsersList usersList; // set in populate()

    public UsersRS() { }

    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML})
    public Response getXml() {
        checkContext();
        return Response.ok(usersList, "application/xml").build();
    }

    @GET
    @Path("/xml/{id: \\d+}")
    @Produces({MediaType.APPLICATION_XML}) // could use "application/xml" instead
    public Response getXml(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/xml");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public Response getJson() {
        checkContext();
        return Response.ok(toJson(usersList), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/json");
    }

    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        checkContext();
        return usersList.toString();
    }


    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response create(@FormParam("givenName") String givenName, @FormParam("password") String password) {

        checkContext();
        String msg = null;
        // Require both properties to create.
        if (givenName == null || password == null) {
            msg = "Property 'name' or 'password' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Prediction and add it to the collection.
        int id = addUser(givenName, password);
        msg = "User " + id + " created: (name = " + givenName + " " + " userName = " + User.generateUserName(givenName) +
                " email address = " + User.generateEmailAddress(User.generateUserName(givenName)) + ").\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id,@FormParam("userName") String userName,
                           @FormParam("password") String password) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (userName == null && password == null)
            msg = "Neither username nor password is given: nothing to edit.\n";

        User u = usersList.find(id);
        if (u == null)
            msg = "There is no user with ID " + id + "\n";

        if (msg != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        // Update.
        if (userName != null) u.setUserName(userName);
        if (password != null) u.setPassword(password);
        msg = "User " + id + " has been updated.\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        User u = usersList.find(id);
        if (u == null) {
            msg = "There is no user with ID " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        usersList.getUsers().remove(u);
        msg = "User " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (usersList == null) populate();
    }

    private void populate() {
        usersList = new UsersList();

        String filename = "/WEB-INF/data/users.csv";
        InputStream in = sctx.getResourceAsStream(filename);

        // Read the data into the array of Predictions.
        if (in != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                int i = 0;
                String record = null;
                while ((record = reader.readLine()) != null) {
                    String[] parts = record.split(",");
                    addUser(parts[0] + parts[1] + parts[2], parts[3]);
                }
            }
            catch (Exception e) {
                throw new RuntimeException("I/O failed!");
            }
        }
    }

    // Add a new prediction to the list.
    private int addUser(String givenName, String password) {
        int id = usersList.add(givenName, password);
        return id;
    }

    // Prediction --> JSON document
    private String toJson(User user) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(user);
        }
        catch(Exception e) { }
        return json;
    }

    // PredictionsList --> JSON document
    private String toJson(UsersList usersList) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(usersList);
        }
        catch(Exception e) { }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        User user = usersList.find(id);
        if (user == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        else if (type.contains("json"))
            return Response.ok(toJson(user), type).build();
        else
            return Response.ok(user, type).build(); // toXml is automatic
    }



}