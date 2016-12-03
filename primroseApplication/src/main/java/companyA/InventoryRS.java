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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.and;

/**
 * Created by evanpthompson on 10/31/2016.
 * RESTful Inventory Class.
 */

@Path("/inventory")
public class InventoryRS {

    private static MongoCollection<Document> collection;  // MongoCollection variable that is instantiated with the no-arg constructor.

    // Class is initialized by the RestfulInventory class when the war file is launched within the Tomcat server.
    // User Data Requirement 1.0.0
    public InventoryRS() {
        collection = MongoConnector.getInstance().getMongoCollection("inventory");
    }

    // Get all items in the inventory database collection by json
    @GET
    @Path("/json")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson() {

        ArrayList<Inventory> invList = toArrayList();
        return Response.ok(toJson(invList), "application/json").build();
    }

    @GET
    @Path("/query")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson(@DefaultValue("0") @QueryParam("start") int start,
                            @DefaultValue("50") @QueryParam("size") int size) {

        Bson projection = Projections.exclude("_id");
        List<Document> all = collection.find(and(gte("id", start), lt("id", (start+size)))).projection(projection).into(new ArrayList<Document>());

        ArrayList<Inventory> invList = new ArrayList<Inventory>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            for (Document cur : all) {
                Inventory inventory = new Inventory();

                inventory = mapper.readValue(cur.toJson(), Inventory.class);
                invList.add(inventory);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Response.ok(toJson(invList), "application/json").build();
    }


    // Get Inventory item by id from the database collection
    @GET
    @Path("/json/{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson(@PathParam("id") int id) {
        Inventory inventory = find(id);
        return toRequestedType(id,"application/json");
    }

    // Get all inventory from the database collection in plain text.
    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        ArrayList<Inventory> invList = toArrayList();
        return invList.toString();
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response create(@FormParam("id") int id, @FormParam("productNumber") String productNumber,
                           @FormParam("itemDescription") String itemDescription, @FormParam("itemCost") double itemCost,
                           @FormParam("itemPrice") double itemPrice, @FormParam("itemCount") int itemCount) {

        ObjectMapper mapper = new ObjectMapper();

        String msg = null;
        if (productNumber == null || itemDescription == null) {
            msg = "A required parameter is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
        }
        Inventory inventory = new Inventory();

        inventory.setId(id);
        inventory.setProductNumber(productNumber);
        inventory.setItemDescription(itemDescription);
        inventory.setItemCost(itemCost);
        inventory.setItemPrice(itemPrice);
        inventory.setItemCount(itemCount);

        try {
            String invString = mapper.writeValueAsString(inventory);
            Document invDoc = Document.parse(invString);
            collection.insertOne(invDoc);
            msg = "Inventory " + id + " created.\n";
            return Response.ok(msg, "text/plain").build();
        }
        catch (JsonProcessingException e) {
            e.getStackTrace();
        }
        msg = "Something went wrong.\n";
        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
    }


    // Updates the item in inventory by id number. Update one to all available fields.
    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id, @FormParam("productNumber") String productNumber,
                           @FormParam("itemDescription") String itemDescription, @FormParam("itemCost") double itemCost,
                           @FormParam("itemPrice") double itemPrice, @FormParam("itemCount") int itemCount) {



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

                if (cur.getInteger("id").equals(id)) {  // ensure item matches



                    try {
                        Inventory inventory = new Inventory();
                        inventory = mapper.readValue(cur.toJson(), Inventory.class);

                        if (id != 0) {
                            inventory.setId(id);
                        }
                        if (productNumber != null) {
                            inventory.setProductNumber(productNumber);
                        }
                        if (itemDescription != null) {
                            inventory.setItemDescription(itemDescription);
                        }
                        if (itemCost != 0.0) {
                            inventory.setItemCost(itemCost);
                        }
                        if (itemPrice != 0.0) {
                            inventory.setItemPrice(itemPrice);
                        }
                        if (itemCount != 0.0) {
                            inventory.setItemCount(itemCount);
                        }

                        String invString = mapper.writeValueAsString(inventory);
                        Document invDoc = Document.parse(invString);

                        collection.findOneAndReplace(filter, invDoc);
                        msg = "update has been made.\n";  // update the message string to confirm update made.
                        return Response.ok(msg, "text/plain").build();  // return the response

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        msg = "Something went wrong. Please try again.";
        return Response.status(Response.Status.BAD_REQUEST). entity(msg).type(MediaType.TEXT_PLAIN).build();
    }

    // Delete inventory item from the database collection by matched id
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
    private ArrayList<Inventory> toArrayList() {
        Bson projection = Projections.exclude("_id");
        List<Document> all = collection.find().projection(projection).into(new ArrayList<Document>());

        ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            for (Document cur : all) {
                Inventory inventory = new Inventory();

                inventory = mapper.readValue(cur.toJson(), Inventory.class);
                inventoryList.add(inventory);
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
    private Inventory find(int id) {

        ArrayList<Inventory> invList = toArrayList();

        Inventory inv = null;
        for (Inventory i : invList) {
            if (i.getId() == id) {
                inv = i;
                break;
            }
        }
        return inv;
    }

    private Response toRequestedType(int id, String type) {
        ArrayList<Inventory> invList = toArrayList();

        Inventory inventory = find(id);

        if (inventory == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
        }
        else if (type.contains("json")) {
            return Response.ok(toJson(inventory), type).build();
        }
        else {
            return Response.ok(inventory, type).build();  // default for xml, not operational.
        }
    }

    // convert inventory item to json
    private String toJson(Inventory inventory) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(inventory);
        }
        catch (Exception e) {

        }
        return json;
    }

    // convert the inventory arraylist to json
    private String toJson(ArrayList<Inventory> inventoryList) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(inventoryList);
        }
        catch (Exception e) {

        }
        return json;
    }

}
