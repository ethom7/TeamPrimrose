package mongomethods;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTester {
	
	public static void main(String[] args) {
		
		// instantiate a mongodb from MongoConnection class
		MongoDatabase db = MongoConnection.getInstance().getMongoDatabase();
		
		// instantiate a collection 
		MongoCollection<Document> collection = db.getCollection("tester");
		 
		// create a tester document
		Document doc = new Document("name", "MongoDB")
				.append("type", "database")
				.append("count", 1)
				.append("info", new Document("x", 203).append("y", 102));
		
		collection.insertOne(doc);
		
		// find first() returns a document
		Document myDoc = collection.find().first();
		
		// confirm both connection to collection, data write to the db, and data read from the db
		System.out.println(myDoc.toJson());
	}

}
