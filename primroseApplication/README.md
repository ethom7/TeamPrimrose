# RESTful Web Service

## Maven packaged. To create war file input 'mvn clean install' in 'primroseApplication/' directory

ROOT URL: is the URL that is published and accessible from the root directory in the webserver

The url is http://[ipaddress]:[port]/

For the remainder of the documentation ROOT is equal to this.


# Inventory RESTful Web Service

## ROOT/primroseApplication/inventory


<table>
    <tr>
        <th>URI</th>
        <th>Method</th> 
         <th>Description</th>
    </tr>
    <tr>
        <td>/query</td>
        <td>GET</td>
        <td>Returns all items within the specified range from the inventory database collection in json format. @QueryParam start for the first id to return and size for the number to include from the range.</td>
    </tr>
    <tr>
        <td>/prodIdSearch/{productId}</td>
        <td>GET</td>
        <td>Returns the item from the inventory database collection in json format. Accepts productId from @PathParam to as the database query field. Response provides code 200 and the object returned in json.</td>
    </tr>
    <tr>
        <td>/prodNumberSearch/{productNumber}</td>
        <td>GET</td>
        <td>Returns the item from the inventory database collection in json format. Accepts productNumber from @PathParam to as the database query field. Response provides code 200 and the object returned in json.</td>
    </tr>
    <tr>
        <td>/idSearch/{productNumber}</td>
        <td>GET</td>
        <td>Returns the item from the inventory database collection in json format. Accepts id from @PathParam to as the database query field. Response provides code 200 and the object returned in json.</td>
    </tr>
    <tr>
        <td>/create</td>
        <td>POST</td>
        <td>Creates a new item from the @FormParam with the following fields: int id, String productNumber, String itemDescription, Double itemCost, Double itemPrice, int itemCount. Inserts into the inventory database collection as a new document. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/update</td>
        <td>PUT</td>
        <td>Updates the inventory database collection from the @FormParam that matches upon id. Fields: int id, String productNumber, String itemDescription, Double itemCost, Double itemPrice, int itemCount. Inserts into the inventory database collection as a new document. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/delete/{id}</td>
        <td>DELETE</td>
        <td>Deletes the inventory item from the inventory database collection by id passed as @PathParam int. Returns status code 200 if successful.</td>
    </tr>
</table>




# Employee RESTful Web Service

## ROOT/primroseApplication/employee


<table>
    <tr>
        <th>URI</th>
        <th>Method</th> 
         <th>Description</th>
    </tr>
    <tr>
        <td>/plain</td>
        <td>GET</td>
        <td>Returns all employees from the employee database collection in plain text format. Format controlled by the ArrayList and Employee toString method.</td>
    </tr>
    <tr>
        <td>/json</td>
        <td>GET</td>
        <td>Returns all employees from the employee database collection in json format.</td>
    </tr>    
    <tr>
        <td>/json/{id}</td>
        <td>GET</td>
        <td>Returns the employee by the input @PathParam id as an integer from the employee database collection in json format. If id is not found, status code 400 returned.</td>
    </tr>
    <tr>
        <td>/create</td>
        <td>POST</td>
        <td>Creates a new employee from the @FormParam with the following fields: int id, String firstName, String middleName, String socialSecurityNumber, String dob, String postalAddress, String phoneNumber, String emergencyContact, boolean activeEmployee. Inserts into the employee database collection as a new document. PostalAddress and EmergencyContact is passed as a semi-colon delimited string, each field is separated by a semi colon. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/update</td>
        <td>PUT</td>
        <td>Updates the employee database collection from the @FormParam that matches upon id. Fields: int id, String firstName, String middleName, String socialSecurityNumber, String dob, String postalAddress, String phoneNumber, String emergencyContact, boolean activeEmployee. Inserts into the employee database collection as a new document. PostalAddress and EmergencyContact is passed as a semi-colon delimited string, each field is separated by a semi colon. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/delete/{id}</td>
        <td>DELETE</td>
        <td>Deletes the employee from the employee database collection by id passed as @PathParam int. Returns status code 200 if successful.</td>
    </tr>
</table>



# User RESTful Web Service

## ROOT/primroseApplication/user


<table>
    <tr>
        <th>URI</th>
        <th>Method</th> 
         <th>Description</th>
    </tr>
    <tr>
        <td>/plain</td>
        <td>GET</td>
        <td>Returns all users from the user database collection in plain text format. Format controlled by the ArrayList and User toString method.</td>
    </tr>
    <tr>
        <td>/json</td>
        <td>GET</td>
        <td>Returns all users from the user database collection in json format.</td>
    </tr>
    <tr>
        <td>/json/{id}</td>
        <td>GET</td>
        <td>Returns the user by the input @PathParam id as an integer from the user database collection in json format. If id is not found, status code 400 returned.</td>
    </tr>
    <tr>
        <td>/create</td>
        <td>POST</td>
        <td>Creates a new user from the @FormParam with the following fields: int id, String givenName, String password, String passwordVerification. Inserts into the user database collection as a new document. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/userLogin</td>
        <td>POST</td>
        <td>Login user from the @FormParam with the following fields: String userName, String password, String ipAdd. Returns a status code 200 upon success and an AuthorizedResponse object.</td>
    </tr>
    <tr>
        <td>/update</td>
        <td>PUT</td>
        <td>Updates the user database collection from the @FormParam that matches upon id. Fields: int id, String currentPassword, String password, String passwordVerification, String userName, boolean activeUser. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/delete/{id}</td>
        <td>DELETE</td>
        <td>Deletes the user from the user database collection by id passed as @PathParam int. Returns status code 200 if successful.</td>
    </tr>
</table>