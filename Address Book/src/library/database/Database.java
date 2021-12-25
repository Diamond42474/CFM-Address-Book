package library.database;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author Logan Miller This is manages all of the calls to the MongoDB
 *         database.
 * 
 *         Each connection is a unique instance that can interact with the
 *         database depending on the privileges of the user's connection string
 */
class Database {
	private MongoClient mongoClient;
	private Logger mongoLogger;

	/**
	 * Sets up the necessary objects to connect to the database.
	 * 
	 * By default, the level is set to OFF which makes it so mongodb does not print
	 * to the console when an action is taken
	 * 
	 * @param level The level to which mongodb's logger prints debug information to
	 *              the console.
	 * 
	 *              Default is set to OFF so that it does not interfere with the
	 *              user interface
	 */
	public Database(Level level) {
		mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(level);
	}

	/**
	 * Connects to a database using a connection string
	 * 
	 * If the connection string is blank, it will default to connecting to the
	 * localhost
	 * 
	 * @param connectionString MongoDB connection string to any given server. If the
	 *                         string is empty, the program will connect to the
	 *                         localhost.
	 */
	public void connect(String connectionString) {
		if (!connectionString.equals("")) {
			mongoClient = new MongoClient(new MongoClientURI(connectionString));
		} else {
			mongoClient = new MongoClient();
		}
	}

	/**
	 * Used to change the level of verbosity that the mongodb debugger displays
	 * 
	 * @param level The level to which mongodb's logger prints debug information to
	 *              the console.
	 * 
	 *              Default is set to OFF so that it does not interfere with the
	 *              user interface
	 */
	public void setDebuggerLevel(Level level) {
		mongoLogger.setLevel(level);
	}

	/**
	 * Retrieves a specific database from the server
	 * 
	 * @param databaseName Specific name of the database
	 * @return Instance of mongo database
	 */
	private MongoDatabase getDatabase(String databaseName) {
		return mongoClient.getDatabase(databaseName);
	}

	/**
	 * Returns collection which contains documents
	 * 
	 * @param databaseName   Name of the database
	 * @param collectionName Name of the collection
	 * @return Collection from the database
	 */
	private MongoCollection<Document> getCollection(String databaseName, String collectionName) {
		return getDatabase(databaseName).getCollection(collectionName);
	}

	/**
	 * Creates a new document in the specified collection
	 * 
	 * If the database and / or collection does not exist, it will be created by
	 * this command
	 * 
	 * @param databaseName   Name of the database
	 * @param collectionName Name of the collection
	 * @param doc            Document being added to the collection
	 */
	public void insertDocument(String databaseName, String collectionName, Document doc) {
		getCollection(databaseName, collectionName).insertOne(doc.append("deleted", false));
	}

	/**
	 * Emulated deleting an object from the database by setting the deleted flag to
	 * true. This is used in order to preserve data but make it functionally gone
	 * 
	 * @param databaseName   Name of the database
	 * @param collectionName Name of the collection
	 * @param query          Query used to specify the find method (can change
	 *                       multiple documents if not specific)
	 */
	public void deleteDocument(String databaseName, String collectionName, Document query) {
		getCollection(databaseName, collectionName).updateMany(query,
				new Document().append("$set", new Document().append("deleted", true)));

	}

	/**
	 * Updates a number of given parameters in the database
	 * 
	 * @param databaseName   Name of the database
	 * @param collectionName Name of the collection
	 * @param query          Query used to specify the find method (can change
	 *                       Multiple documents if not specific)
	 * @param update         Fields and values being updated / added
	 */
	public void updateDocument(String databaseName, String collectionName, Document query, Document update) {
		getCollection(databaseName, collectionName).updateMany(query, new Document().append("$set", update));
	}

	/**
	 * Returns a list of documents returned by a specific search query.
	 * 
	 * Only returns documents which the deleted flag is set to false
	 * 
	 * @param databaseName   The name of the database
	 * @param collectionName The name of the collection
	 * @param query          Query used to specify the find method (can change
	 *                       Multiple documents if not specific)
	 * @return List of all documents that match the seach query
	 */
	public List<Document> getDocument(String databaseName, String collectionName, Document query) {
		MongoCursor<Document> documents = getCollection(databaseName, collectionName)
				.find(query.append("deleted", false)).iterator();
		List<Document> out = new ArrayList<Document>();
		while (documents.hasNext()) {
			out.add(documents.next());
		}
		return out;
	}

	/**
	 * Returns a list of all database names
	 * 
	 * @return List of database names
	 */
	public List<String> getDatabases() {
		MongoCursor<String> databases = mongoClient.listDatabaseNames().iterator();
		List<String> output = new ArrayList<String>();
		while (databases.hasNext()) {
			output.add(databases.next());
		}
		return output;
	}
}
