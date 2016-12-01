package companyA;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Sorts.descending;


public class App {
	
	static myMongoObject mmO = new myMongoObject();  
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Please wait while MongoDB is set up");
		MongoSetup(mmO);
	
	while(true) {
		  
		System.out.println("Please select a function: ");
		System.out.println("1: Load users from .csv file");
		System.out.println("2: Set a user as inactive");
		System.out.println("0: Exit");
		try {
			int choice = input.nextInt();
			switch(choice) {
			case 1: {
				long start = Instant.now().toEpochMilli();

				System.out.println("Loading users from .csv file...");
				loadCSV(mmO);
				addToCollection(mmO);
				long end = Instant.now().toEpochMilli();

				long execution = end - start;

				System.out.println("Execution time in milliseconds: " + execution);
				System.out.println("Execution time in seconds: " + (execution/1000) + "\n");
				break;
			}
			case 2: {
				
				try {
					setInactive(mmO);
				} catch (JsonParseException e) {
					
					e.printStackTrace();
				} catch (JsonMappingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				break;
			}
			
			case 0: {
				System.out.println("Thanks for using Primrose software, goodbye!");
				System.exit(0);
			}
			default: {
				System.out.println("Invalid input");
				input.nextLine();
				break;
			}
			}
		}
		catch (InputMismatchException e)
		{
			System.out.println("A number was not entered");
			input.nextLine();
		}
		
	}//end while
		
		
		
	}
	
	private static void MongoSetup(myMongoObject mmO) {

		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		mmO.setEmployeeList(employeeList);
		
		// instantiate a mongodb from MongoConnector class
		MongoDatabase db = MongoConnector.getInstance().getMongoDatabase();
				
		// instantiate a collection 
		MongoCollection<Document> employeeCollection = db.getCollection("employee");
		MongoCollection<Document> userCollection = db.getCollection("user"); 	
		mmO.setEmployeeCollection(employeeCollection);
		mmO.setUserCollection(userCollection);
	}
	
	// this method does not currently read in the emergency contact info to the user.
	// Can easily be implemented as was postalAddress.
	private static void loadCSV(myMongoObject mmO) {
		
		//TODO: prompt the user for the path and extension of their own csv file as String (tenK)
		System.out.println("Please enter the path of the CSV file including the extension .csv:");
		input.nextLine();
		String tenK = input.nextLine();  //"src/main/java/companyA/testdataN10K.csv"
		
		ArrayList<HashMap<String, String>> hm2 = ReadMethods.createListFromCSV(tenK, ",");  //readIn list

		// corrected the listing to dob
		String[] dataOrder = {"firstName", "middleName", "lastName", "socialSecurityNumber", "dob", "street", "city", "state", "zip", "phoneNumber", "hireDate", "contactName", "relation", "emergencyPhoneNumber"};	//this is correct data order
		int nextIDAvail;
		nextIDAvail = getNextId(0);
		
		for (HashMap<String, String> row : hm2) {
			 
			Employee emp = new Employee();
			emp.setActiveEmployee(true);
			//Employee(, row.get(dataOrder[1]), row.get(dataOrder[2]), row.get(dataOrder[3]), row.get(dataOrder[4]), new PostalAddress(row.get(dataOrder[5])), row.get(dataOrder[6]), row.get(dataOrder[7]))
			
			emp.setId(nextIDAvail);
			nextIDAvail++;
			
			emp.setFirstName(row.get(dataOrder[0]));
			emp.setMiddleName(row.get(dataOrder[1]));
			emp.setLastName(row.get(dataOrder[2]));
			emp.setGivenName(row.get(dataOrder[0]) + " " + row.get(dataOrder[1]) + " " + row.get(dataOrder[2]));
			emp.setSocialSecurityNumber(row.get(dataOrder[3]));
			emp.setDob(row.get(dataOrder[4]));
			PostalAddress address = new PostalAddress(row.get(dataOrder[5]),row.get(dataOrder[6]),row.get(dataOrder[7]),row.get(dataOrder[8]));
			emp.setPostalAddress(address);
			emp.setPhoneNumber(row.get(dataOrder[9]));
			emp.setHireDate(row.get(dataOrder[10]));
			EmergencyContact cont = new EmergencyContact(row.get(dataOrder[11]),row.get(dataOrder[12]),row.get(dataOrder[13]));
			emp.setEmergencyContact(cont);

			mmO.getEmployeeList().add(emp);		
		}
		
	}
	
	
	private static void addToCollection(myMongoObject mmO) {
		for (int i = 0; i < mmO.getEmployeeList().size()-1;i++) {
			ObjectMapper mapper = new ObjectMapper();
			String employeeString;
			String userString;
			
			try {
				Employee tempEmployee = mmO.getEmployeeList().get(i);
				tempEmployee.setId(i);
				employeeString = mapper.writeValueAsString(tempEmployee);
				
				User user = getUser(mmO.getEmployeeList().get(i).getGivenName());	
				user.setId(i);
				user.setActiveUser(mmO.getEmployeeList().get(i).isActiveEmployee());  //this sets the user active to be the corresponding value of the employee active 
				userString = mapper.writeValueAsString(user);
					
				
				Document employeeDoc = Document.parse(employeeString);  //employeeDoc holds all the values of employeeString
				mmO.getEmployeeCollection().insertOne(employeeDoc); //employeeDoc is what's loaded into the collection, collection is what's in mongo
				
				
				Document userDoc = Document.parse(userString);
				mmO.getUserCollection().insertOne(userDoc);

			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
		}
		System.out.println("Users/employees have been added to the database");
		System.out.println();
	}
	
	
	
	
	
	private static void setInactive(myMongoObject mmO) throws JsonParseException, JsonMappingException, IOException {
		System.out.print("To set a user as INACTIVE, please enter the user ID number: ");
		int inactiveID = input.nextInt();
		boolean makeInactive = false;
		
				
		Bson filter = eq("id", inactiveID); //filter by matching id
		Bson projection = Projections.exclude("_id"); //exclude the object id from the return
				
		//return an iterable cursor object
		MongoCursor<Document> itr = mmO.getUserCollection().find(filter).projection(projection).iterator();  

	
		try {
			while (itr.hasNext()) {
				Document cur = itr.next();
				
				if (cur.getInteger("id").equals(inactiveID)) {
					ObjectMapper mapper = new ObjectMapper();
					User user = mapper.readValue(cur.toJson(), User.class);
					
					System.out.println();
					System.out.println("Username: "+user.getUserName() +"\n"+ "Given name: " +user.getGivenName());
					System.out.println();
					System.out.print("Are you sure you want to set this user INACTIVE?  Re-enter user ID to confirm: ");
					int inactiveID2 = input.nextInt();
					if (inactiveID == inactiveID2) {
						mmO.getUserCollection().findOneAndReplace(cur, new Document("id", inactiveID)
							.append("givenName", user.getGivenName())
							.append("userName", user.getUserName())
							.append("password", user.getPassword())
							.append("emailAddress", user.getEmailAddress())
							.append("passwordExpiration", user.getPasswordExpiration())
							.append("activeUser", makeInactive)); 
							
					System.out.println("User has been set as inactive");
					}
					else {
						System.out.println();
						System.out.println("User IDs to inactivate do not match.  Returning you to main menu...");
						System.out.println();
					}
				}		
			}
		}
		finally {
			itr.close();
		}
				
	}

	
	
	
	public static User getUser(String employee) {
		String tempPassword = "TeamPrimrose!1";  // string for temporary password
		String hashed = BCrypt.hashpw(tempPassword, BCrypt.gensalt());  // encrypt password to store to the db collection
	   User user = new User(employee, hashed);
	   return user;
	}
	
	
		
	 // method is used to search the database for the highest id number and returns the next available id number.
    public static int getNextId(int defaultStartId) {

        int nextId = 0;

        MongoCollection<Document> collection = MongoConnector.getInstance().getMongoCollection("employee");  // creates connection to db collection specified.

        // Check to see the number of employees in the employee database collection
        // If collection has any employees in it proceed to retrieve last used id number. Else
        if (collection.count() > 0) {
            Document doc = collection.find(exists("id")).sort(descending("id")).first();
            
            nextId = doc.getInteger("id");
            nextId++;
        }
        else {
            nextId = defaultStartId;
        }

        return nextId;
    }
	
}