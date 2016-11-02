package companyA;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by evanpthompson on 11/1/2016.
 */

@Path("/")
public class EmployeeRS {

    private static MongoCollection<Document> collection;  // MongoCollection variable that is instantiated with the no-arg constructor.

    // Class is initialized by the RestfulInventory class when the war file is launched within the Tomcat server.
    public EmployeeRS() {
        collection = MongoConnector.getInstance().getMongoCollection("employee");
    }

    // Get all items in the inventory database collection by json
    @GET
    @Path("/json")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson() {

        ArrayList<Employee> invList = toArrayList();
        return Response.ok(toJson(invList), "application/json").build();
    }


    // Get Inventory item by id from the database collection
    @GET
    @Path("/json/{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson(@PathParam("id") int id) {
        Employee employee = find(id);
        return toRequestedType(id,"application/json");
    }

    // Get all inventory from the database collection in plain text.
    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        ArrayList<Employee> invList = toArrayList();
        return invList.toString();
    }


    /* Utility method will accept postalAddress as string along with a delimiter and populate a PostalAddress accordingly.
    buildPostalAddress("123 Street Name;City Name;State Name;Zip Code", ";"); */

    /* Utility method will accept emergencyContact as string along with a delimiter and populate an EmergencyContact accordingly.
    buildEmergencyContact("This Contact Name;Relationship;Contact Phone number", ";"); */

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response create(@FormParam("id") int id, @FormParam("firstName") String firstName,
                           @FormParam("middleName") String middleName, @FormParam("lastName") String lastName,
                           @FormParam("socialSecurityNumber") String socialSecurityNumber, @FormParam("dob") String dob,
                           @FormParam("postalAddress") String postalAddress, @FormParam("phoneNumber") String phoneNumber,
                           @FormParam("emergencyContact") String emergencyContact, @FormParam("activeEmployee") boolean activeEmployee) {

        ObjectMapper mapper = new ObjectMapper();

        String msg = null;
        if (firstName == null || lastName == null) {
            msg = "A required parameter is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }

        LocalDate ld = LocalDate.parse(dob, DateTimeFormat.forPattern("MM/dd/yyyy"));   // will accept the format to convert as 01/01/2016 or 01-01-2016

        PostalAddress pa = new PostalAddress();
        EmergencyContact ec = new EmergencyContact();

        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setSocialSecurityNumber(socialSecurityNumber);
        employee.setDob(ld.toString(DateTimeFormat.forPattern("MM/dd/yyyy")));
        employee.setPostalAddress(pa.buildPostalAddress(postalAddress, ";"));
        employee.setPhoneNumber(phoneNumber);
        employee.setEmergencyContact(ec.buildEmergencyContact(emergencyContact, ";"));
        employee.setActiveEmployee(activeEmployee);

        // This was added. to prevent issues with inserting the postalAddress and emergencyContact
        // objects most likely will need to nest new Document to add them properly.
        try {
            String empString = mapper.writeValueAsString(employee);
            Document empDoc = new Document("id", id)
                    .append("firstName", firstName)
                    .append("middleName", middleName)
                    .append("lastName", lastName)
                    .append("socialSecurityNumber", socialSecurityNumber)
                    .append("dob", ld.toString(DateTimeFormat.forPattern("MM/dd/yyyy")))
                    .append("postalAddress", new Document("street", employee.getPostalAddress().getStreet()).append("city", employee.getPostalAddress().getCity()).append("state", employee.getPostalAddress().getState()).append("zip", employee.getPostalAddress().getZip()))
                    .append("phoneNumber", phoneNumber)
                    .append("emergencyContact", new Document("contactName", employee.getEmergencyContact().getContactName()).append("relation", employee.getEmergencyContact().getRelation()).append("emergencyPhoneNumber", employee.getEmergencyContact().getEmergencyPhoneNumber()))
                    .append("activeEmployee", activeEmployee);
            collection.insertOne(empDoc);
            msg = "Employee " + id + " created.\n";
            return Response.ok(msg, "text/plain").build();
        }
        catch (JsonProcessingException e) {
            e.getStackTrace();
        }
        msg = "Something went wrong.\n";
        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
    }


    // Updates the employee by id number.
    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id, @FormParam("firstName") String firstName,
                           @FormParam("middleName") String middleName, @FormParam("lastName") String lastName,
                           @FormParam("socialSecurityNumber") String socialSecurityNumber, @FormParam("dob") String dob,
                           @FormParam("postalAddress") String postalAddress, @FormParam("phoneNumber") String phoneNumber,
                           @FormParam("emergencyContact") String emergencyContact, @FormParam("activeEmployee") boolean activeEmployee) {



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


        LocalDate ld = LocalDate.parse(dob, DateTimeFormat.forPattern("MM/dd/yyyy"));   // will accept the format to convert as 01/01/2016 or 01-01-2016


        PostalAddress pa = new PostalAddress();
        EmergencyContact ec = new EmergencyContact();

        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setSocialSecurityNumber(socialSecurityNumber);
        employee.setDob(ld.toString(DateTimeFormat.forPattern("MM/dd/yyyy")));
        employee.setPostalAddress(pa.buildPostalAddress(postalAddress, ";"));
        employee.setPhoneNumber(phoneNumber);
        employee.setEmergencyContact(ec.buildEmergencyContact(emergencyContact, ";"));
        employee.setActiveEmployee(activeEmployee);


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
                            .append("firstName", firstName)
                            .append("middleName", middleName)
                            .append("lastName", lastName)
                            .append("socialSecurityNumber", socialSecurityNumber)
                            .append("dob", ld.toString(DateTimeFormat.forPattern("MM/dd/yyyy")))
                            .append("postalAddress", new Document("street", employee.getPostalAddress().getStreet()).append("city", employee.getPostalAddress().getCity()).append("state", employee.getPostalAddress().getState()).append("zip", employee.getPostalAddress().getZip()))
                            .append("phoneNumber", phoneNumber)
                            .append("emergencyContact", new Document("contactName", employee.getEmergencyContact().getContactName()).append("relation", employee.getEmergencyContact().getRelation()).append("emergencyPhoneNumber", employee.getEmergencyContact().getEmergencyPhoneNumber()))
                            .append("activeEmployee", activeEmployee));


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


    // Delete employee from the database collection by matched id
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        String msg = null;

        // run query to delete from db collection as long as id has been input.
        if (id != 0) {
            collection.findOneAndDelete(eq("id", id));
            msg = "Inventory item " + id + " has been deleted.\n";
            return Response.ok(msg, "text/plain").build();
        }
        else {
            msg = "No ID was provided. Nothing to do.";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }


    }




    /*  Utility Methods  */

    // method adds all inventory items to an arraylist from the database collection.
    // For scalability will need to accept optional query parameters to give a starting index and number to return.
    private ArrayList<Employee> toArrayList() {
        Bson projection = Projections.exclude("_id");
        List<Document> all = collection.find().projection(projection).into(new ArrayList<Document>());

        ArrayList<Employee> inventoryList = new ArrayList<Employee>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        try {
            for (Document cur : all) {
                Employee employee = new Employee();

                employee = mapper.readValue(cur.toJson(), Employee.class);
                inventoryList.add(employee);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }

    // return the matching inventory item from the inventory array.
    private Employee find(int id) {

        ArrayList<Employee> empList = toArrayList();

        Employee emp = null;
        for (Employee e : empList) {
            if (e.getId() == id) {
                emp = e;
                break;
            }
        }
        return emp;
    }

    private Response toRequestedType(int id, String type) {
        ArrayList<Employee> invList = toArrayList();

        Employee employee = find(id);

        if (employee == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
        }
        else if (type.contains("json")) {
            return Response.ok(toJson(employee), type).build();
        }
        else {
            return Response.ok(employee, type).build();  // default for xml, not operational.
        }
    }

    // convert inventory item to json
    private String toJson(Employee employee) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(employee);
        }
        catch (Exception e) {

        }
        return json;
    }

    // convert the inventory arraylist to json
    private String toJson(ArrayList<Employee> inventoryList) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(inventoryList);
        }
        catch (Exception e) {

        }
        return json;
    }


    private static LocalDate getDateFromString(String dateString) {
        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate df = LocalDate.parse(dateString, dtf);

            return df;
        } catch (Exception e) {  }
        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("MM-dd-yyyy");
            LocalDate df = LocalDate.parse(dateString, dtf);
            return df;
        } catch (Exception e) {  }

        return null;
    }


}
