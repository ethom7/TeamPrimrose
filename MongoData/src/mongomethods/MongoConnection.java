package mongomethods;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

	private static final MongoConnection INSTANCE = new MongoConnection();  // final instance of itself
	private static final String DBNAME = "primrose";  // private string for db name
	
	private MongoClient client = null;
	private MongoDatabase db = null;
	
	private MongoConnection() {
		client = new MongoClient("192.168.1.211", 27017);
		db = client.getDatabase(DBNAME);
	}
	
	public static MongoConnection getInstance() {
		return INSTANCE;
	}
	
	public MongoDatabase getMongoDatabase() {
		return db;
	}
}
