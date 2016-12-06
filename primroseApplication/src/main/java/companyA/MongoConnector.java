package companyA;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by evanpthompson on 10/26/2016.
 * MongoConnector provides methods for getInstance and getMongoDatabase.
 */
public class MongoConnector {

    private static final MongoConnector INSTANCE = new MongoConnector();  // final instance of itself

    /*
     * The following fields are specific to the launched instance relative to the service deployment and the datbase
     */

    private static final String DBNAME = "primrose";  // private string for db name
    private final char[] password = {'T','e', 'a', 'm', 'P', 'r', 'i', 'm', 'r', 'o', 's', 'e', '!', '1'};
    private final String ipAddress = "192.168.1.211";
    private final String port = "27017";
    private final String authDB = "user-data";

    private MongoClient client = null;  // instantiate attribute to null
    private MongoDatabase db = null;  // instantiate attribute to null

    private MongoConnector() {

        String URIString = "mongodb://primrose:" + "TeamPrimrose!1" + "@" + ipAddress + ":" + port + "/" + authDB;


        // "mongodb://primrose:TeamPrimrose!1@192.168.1.211:27017/user-data"
        MongoClientURI uri = new MongoClientURI(URIString);

        // MongoClient has multiple constructors, can take no-args for default "localhost", 27017 or a string and int for defined ip and port.
        // also accepts a new MongoClientUR("mongodb://localhost:27017")
        client = new MongoClient(uri);
        db = client.getDatabase(DBNAME);
    }

    public static MongoConnector getInstance() {
        return INSTANCE;
    }

    public MongoDatabase getMongoDatabase() {
        return db;
    }

    public MongoCollection<Document> getMongoCollection(String collectionName) {
        return db.getCollection(collectionName);
    }
}
