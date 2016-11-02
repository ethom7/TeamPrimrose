# Inventory RESTful Web Service

## Maven packaged. To create war file input 'mvn clean install'

## Root URL: http://localhost:8080/inventory/inventoryResources


<table>
    <tr>
        <th>URI</th>
        <th>Method</th> 
         <th>Description</th>
    </tr>
    <tr>
        <td>/plain</td>
        <td>GET</td>
        <td>Returns all inventory items from the inventory database collection in plain text format. Format controlled by the ArrayList and Inventory toString method.</td>
    </tr>
    <tr>
        <td>/json</td>
        <td>GET</td>
        <td>Returns all inventory items from the inventory database collection in json format.</td>
    </tr>
    <tr>
        <td>/json/{id}</td>
        <td>GET</td>
        <td>Returns the inventory item by the input @PathParam id as an integer from the inventory database collection in json format. If id is not found, status code 400 returned.</td>
    </tr>
    <tr>
        <td>/create</td>
        <td>POST</td>
        <td>Creates a new inventory item from the @FormParam with the following fields: int id, String productNumber, String itemDescription, double itemCost, double itemPrice, int itemCount. Inserts into the inventory database collection as a new document. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/update</td>
        <td>PUT</td>
        <td>Updates the inventory database collection from the @FormParam that matches upon id. Fields: int id, String productNumber, String itemDescription, double itemCost, double itemPrice, int itemCount. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/delete/{id}</td>
        <td>DELETE</td>
        <td>Deletes the inventory item from the inventory database collection by id passed as @PathParam int. Returns status code 200 if successful.</td>
    </tr>
</table>
