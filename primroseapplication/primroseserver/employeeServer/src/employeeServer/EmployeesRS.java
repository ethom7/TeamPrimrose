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


@Path("/employees")
public class EmployeesRS {


    @Context
    private ServletContext sctx;          // dependency injection
    private static EmployeesList employeesList; // set in populate()

    public EmployeesRS() { }

    /*
    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML})
    public Response getXml() {
        checkContext();
        return Response.ok(employeesList, "application/xml").build();
    }

    @GET
    @Path("/xml/{id: \\d+}")
    @Produces({MediaType.APPLICATION_XML}) // could use "application/xml" instead
    public Response getXml(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/xml");
    }
    */

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public Response getJson() {
        checkContext();
        return Response.ok(toJson(employeesList), "application/json").build();
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
        return employeesList.toString();
    }


    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response create(@FormParam("firstName") String firstName, @FormParam("middleName") String middleName,
                           @FormParam("lastName") String lastName,
                           @FormParam("socialSecurityNumber") String socialSecurityNumber, @FormParam("dob") String dob) {

        checkContext();
        String msg = null;
        // Require both properties to create.
        if (firstName == null || lastName == null || socialSecurityNumber == null || dob == null) {
            msg = "Property 'who' or 'what' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Prediction and add it to the collection.
        int id = addEmployee(firstName, middleName, lastName, socialSecurityNumber, dob);
        msg = "Employee " + id + " created: (name = " + firstName + " " + middleName + " " + lastName +
                " socialSecurityNumber = " + socialSecurityNumber + " date of birth " + dob + ").\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id,@FormParam("firstName") String firstName,
                           @FormParam("middleName") String middleName, @FormParam("lastName") String lastName,
                           @FormParam("socialSecurityNumber") String socialSecurityNumber, @FormParam("dob") String dob) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (firstName == null && lastName == null &&  socialSecurityNumber== null && dob == null)
            msg = "Neither name nor ssn nor dob is given: nothing to edit.\n";

        Employee e = employeesList.find(id);
        if (e == null)
            msg = "There is no employee with ID " + id + "\n";

        if (msg != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        // Update.
        if (firstName != null) e.setFirstName(firstName);
        if (middleName != null) e.setMiddleName(middleName);
        if (lastName != null) e.setLastName(lastName);
        if (socialSecurityNumber != null) e.setSocialSecurityNumber(socialSecurityNumber);
        if (dob != null) e.setDob(dob);
        msg = "Employee " + id + " has been updated.\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        Employee e = employeesList.find(id);
        if (e == null) {
            msg = "There is no employee with ID " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        employeesList.getEmployees().remove(e);
        msg = "Employee " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (employeesList == null) populate();
    }

    private void populate() {
        employeesList = new EmployeesList();

        String filename = "/WEB-INF/data/employees.csv";
        InputStream in = sctx.getResourceAsStream(filename);

        // Read the data into the array of Predictions.
        if (in != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                int i = 0;
                String record = null;
                while ((record = reader.readLine()) != null) {
                    String[] parts = record.split(",");
                    addEmployee(parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
            catch (Exception e) {
                throw new RuntimeException("I/O failed!");
            }
        }
    }

    // Add a new prediction to the list.
    private int addEmployee(String firstName, String middleName, String lastName, String socialSecurityNumber, String dob) {
        int id = employeesList.add(firstName, middleName, lastName, socialSecurityNumber, dob);
        return id;
    }

    // Prediction --> JSON document
    private String toJson(Employee employee) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(employee);
        }
        catch(Exception e) { }
        return json;
    }

    // PredictionsList --> JSON document
    private String toJson(EmployeesList employeesList) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(employeesList);
        }
        catch(Exception e) { }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        Employee employee = employeesList.find(id);
        if (employee == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        else if (type.contains("json"))
            return Response.ok(toJson(employee), type).build();
        else
            return Response.ok(employee, type).build(); // toXml is automatic
    }



}
