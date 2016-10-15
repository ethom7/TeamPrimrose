
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;

import java.io.File;
import java.util.Arrays;

import org.bson.Document;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class MongoData {

   public static void main( String args[] ) {
	
	   //connectToDB();
      //readCollection();
	   jsonGo();
   }
   
   public static void jsonGo() {
		Employee emp = getEmployee();
		
		User user = getUser(emp);
		
		ObjectMapper mapper1 = new ObjectMapper();
		ObjectMapper mapper2 = new ObjectMapper();
		
		try {
			
			// write out to file
			//mapper.writeValue(new File("employee.json"), emp);
			//mapper.writerWithDefaultPrettyPrinter().writeValue(new File("employee.json"), emp);  
			String jsonInString1 = mapper1.writeValueAsString(emp);  // write to json string
			String jsonInString2 = mapper1.writeValueAsString(user);  // write to json string
			
			MongoClient mongoClient = new MongoClient( "192.168.1.154" , 27017 );
			
	         // Now connect to your databases
			MongoDatabase database = mongoClient.getDatabase("primrose");
			System.out.println("Connect to database successfully");
	         
			MongoCollection<Document> collection1 = database.getCollection("employee");
			Document doc1 = Document.parse(jsonInString1);
			collection1.insertOne(doc1);
			
			MongoCollection<Document> collection2 = database.getCollection("user");
			Document doc2 = Document.parse(jsonInString2);
			
			collection2.insertOne(doc2);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
   
   public static Employee getEmployee() {
		
		Date dob = new Date();
		dob.setYear(1986);
		dob.setMonth(4);
		dob.setDate(1);
		
		PostalAddress postal = new PostalAddress("1015 East Elm Street", "Olathe", "Kansas", "66061");
		EmergencyContact emergency = new EmergencyContact("Amanda Thompson", "spouse", "9132696908");
		
		Employee emp1 = new Employee("Evan", "Paul Leslie", "Thompson", 123456789, dob, postal, emergency, "9133758787");
				
		return emp1;
   }
   
   public static User getUser(Employee employee) {
	   
	   User user = new User(employee.fullNameAsString(), "TeamPrimrose!1");
	   
	   return user;
   }
   
   public static void writeToDB() {
	   MongoClient mongoClient = new MongoClient( "192.168.1.154" , 27017 );
	   
   }
   
   public static void connectToDB() {
	   
	   /*

		{  // object in db
		   "_id" : <ObjectId>,
		   "name" : <string>,
		   "contact" : {
		      "phone" : <string>
		      "email" : <string>
		      "location" : [ <longitude>, <latitude> ]
		   },
		   "stars" : int,
		   "categories" : <array of strings>
		   "grades" : <array of integers>,
		 }
		 
		 
		 // object insert
		  * From: http://mongodb.github.io/mongo-java-driver/3.4/javadoc/?com/mongodb/client/MongoCollection.html#insertOne-TDocument-
		  * Document() // empty Document instance constructor
		  * Document(Map<String, Object> map) // creates a Document instance initialized with the given map
		  * Document(String key, Object value) // Create a Document instance initialized with the given key/value pair
		 
		 Document document = new Document("name", "Café Con Leche")
               .append("contact", new Document("phone", "228-555-0149")
                                       .append("email", "cafeconleche@example.com")
                                       .append("location",Arrays.asList(-73.92502, 40.8279556)))
               .append("stars", 3)
               .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));
		 
	   */
	   
	   
	   
	   
	   try{
			
	         // To connect to mongodb server
	         MongoClient mongoClient = new MongoClient( "192.168.1.154" , 27017 );
				
	         // Now connect to your databases
	         MongoDatabase database = mongoClient.getDatabase("primrose");
	         System.out.println("Connect to database successfully");
	         
	         MongoCollection<Document> collection = database.getCollection("employee");
	         
	         Document document = new Document();

	  collection.insertOne(document);
	         
	         
	         
	         
	         
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         
	      }
   }
   
   public static void readCollection() {
	   try{   
			
	         // To connect to mongodb server
	         MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://192.168.1.154:27017"));
				
	         // Now connect to your databases
	         MongoDatabase database = mongoClient.getDatabase("primrose");
	         System.out.println("Connect to database successfully");
	         
	         MongoCollection<Document> coll = database.getCollection("weather");
	         
	         for (Document d : coll.find()) {
	        	 System.out.println(d);
	         }
	         
	         
	         mongoClient.close();
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	   
   }
   
}
