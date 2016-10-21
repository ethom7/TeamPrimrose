package datamethods;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongomethods.MongoConnection;
import objectclasses.Employee;
import objectclasses.PostalAddress;
import objectclasses.User;

public class DataTester {
	
	public static void main(String[] args) {
		
		
		
		String oneK = "src/testdataN1K.csv";
		String tenK = "src/testdataN10K.csv";
	
		ArrayList<HashMap<String, String>> hm1 = ReadMethods.createListFromCSV(oneK, ",");  //readIn list
		ArrayList<HashMap<String, String>> hm2 = ReadMethods.createListFromCSV(tenK, ",");  //readIn list
		
		String[] dataOrder = {"firstName", "middleName", "lastName", "socialSecurityNumber", "dateOfBirth", "postalAddress", "phoneNumber", "hireDate"};
		
		ArrayList<Employee> employeeList = new ArrayList<>();
		
		// instantiate a mongodb from MongoConnection class
		MongoDatabase db = MongoConnection.getInstance().getMongoDatabase();
				
		// instantiate a collection 
		MongoCollection<Document> employeeCollection = db.getCollection("employees");
		MongoCollection<Document> userCollection = db.getCollection("users"); 	
		
		//System.out.println("Rows: " + hm1.size() + " Columns: " + hm1.get(0).size());  //works
		//System.out.println("Rows: " + hm2.size() + " Columns: " + hm2.get(0).size());  //works
		
		for (HashMap<String, String> row : hm2) {
			Employee emp = new Employee();
			
			//Employee(, row.get(dataOrder[1]), row.get(dataOrder[2]), row.get(dataOrder[3]), row.get(dataOrder[4]), new PostalAddress(row.get(dataOrder[5])), row.get(dataOrder[6]), row.get(dataOrder[7]))
			
			emp.setFirstName(row.get(dataOrder[0]));
			emp.setMiddleName(row.get(dataOrder[1]));
			emp.setLastName(row.get(dataOrder[2]));
			emp.setSocialSecurityNumber(Integer.parseInt(row.get(dataOrder[3])));
			emp.setDob(row.get(dataOrder[4]));
			emp.setPostalAddress(new PostalAddress(row.get(dataOrder[5])));
			emp.setPhoneNumber(row.get(dataOrder[6]));
			
			employeeList.add(emp);
		}
		
		
		// create a tester document
		/*Document doc = new Document("name", "MongoDB")
						.append("type", "database")
						.append("count", 1)
						.append("info", new Document("x", 203).append("y", 102));
						
		collection.insertOne(doc);
		*/
		
		for (int i = 0; i < employeeList.size();i++) {
			ObjectMapper mapper = new ObjectMapper();
			String employeeString;
			String userString;
			try {
				
				User user = getUser(employeeList.get(i).fullNameAsString());
				employeeString = mapper.writeValueAsString(employeeList.get(i));
				userString = mapper.writeValueAsString(user);
				
				Document employeeDoc = new Document("id", i).append("employee", employeeString);
				employeeCollection.insertOne(employeeDoc);
				
				Document userDoc = new Document("id", i).append("employee", userString);
				userCollection.insertOne(userDoc);
				
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
			
			
		}
			
		System.out.println(employeeCollection.count());
		System.out.println(userCollection.count());
			
			

		//System.out.println("Employees in employeeList: " + employeeList.size());
		
		/*  verify employeeList is populated
		for (Employee em : employeeList) {
			System.out.println(em);
		}
		
		System.out.println("Employees in employeeList: " + employeeList.size());
		*/
		
	}
	
	public static User getUser(String employee) {
		   
	   User user = new User(employee, "TeamPrimrose!1");
		   
	   return user;
	}

}
