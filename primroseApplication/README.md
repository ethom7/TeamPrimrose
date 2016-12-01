# RESTful Web Service

## Maven packaged. To create war file input 'mvn clean install'





# Inventory RESTful Web Service

## Root URL: http://localhost:8080/primroseApplication/inventory


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
            <td>Login user from the @FormParam with the following fields: String userName, String password. Returns a status code 200 upon success.</td>
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




# Employee RESTful Web Service

## Maven packaged. To create war file input 'mvn clean install'

## Root URL: http://localhost:8080/primroseApplication/employee


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
        <td>Updates the inventory database collection from the @FormParam that matches upon id. Fields: int id, String firstName, String middleName, String socialSecurityNumber, String dob, String postalAddress, String phoneNumber, String emergencyContact, boolean activeEmployee. Inserts into the employee database collection as a new document. PostalAddress and EmergencyContact is passed as a semi-colon delimited string, each field is separated by a semi colon. Returns a status code 200 upon success.</td>
    </tr>
    <tr>
        <td>/delete/{id}</td>
        <td>DELETE</td>
        <td>Deletes the employee from the inventory database collection by id passed as @PathParam int. Returns status code 200 if successful.</td>
    </tr>
</table>



# User RESTful Web Service

## Root URL: http://localhost:8080/primroseApplication/user


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
            <td>Login user from the @FormParam with the following fields: String userName, String password. Returns a status code 200 upon success.</td>
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