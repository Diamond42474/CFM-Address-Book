package library.database;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bson.Document;

import library.storage.StorageManager;

/**
 * 
 * @author Logan Miller
 * 
 *         Manages all of the commands going to and from the server. This is
 *         tightly coupled to the program specific requirements such as
 *         creating, deleting, and updating addresses in the database.
 * 
 *         This class specifically controls actions taking against the database.
 */
public class DatabaseManager {
	private static Database database;
	private static Level debuggerLevel = Level.OFF;

	// Specific Database Info
	private final static String DATABASENAME = "CFM-Test";
	// Specific Collection info
	private final static String ADDRESSCOLLECTION = "Addresses";
	private final static String USERSCOLLECTION = "Users";

	/**
	 * Creates a connection to a specified database (local or remote)
	 * 
	 * @throws Exception Thrown if there is an error when connecting to the
	 *                   database. Make sure that the connection string is right.
	 */
	public static void start() throws Exception {
		String connectionString = StorageManager.getConnectionString();
		try {
			database = new Database(debuggerLevel);
			database.connect(connectionString);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Checks to see if the application can connect to the given database
	 * 
	 * @param connectionString Database connection string
	 * @return If the application can connect to the database
	 */
	public static boolean checkConnection(String connectionString) {
		Database db = new Database(debuggerLevel);
		try {
			db.connect(connectionString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Sets the verbosity of mongodb
	 * 
	 * @param level level The level to which mongodb's logger prints debug
	 *              information to the console.
	 * 
	 *              Default is set to OFF so that it does not interfere with the
	 *              user interface
	 */
	public static void setDebuggerLevel(Level level) {
		if (database.equals(null)) {
			debuggerLevel = level;
		} else {
			database.setDebuggerLevel(level);
		}
	}

	/**
	 * Finds a list of users given a query
	 * 
	 * @param query Parameters for user search
	 * @return List of users
	 */
	public static List<User> findUser(Document query) {
		List<Document> out = database.getDocument(DATABASENAME, USERSCOLLECTION, query);
		List<User> users = new ArrayList<User>();
		for (Document doc : out) {
			users.add(new User(doc.getString("username"), doc.getString("firstName"), doc.getString("lastName"),
					doc.getString("email"), doc.getString("hashedPassword")));
		}
		return users;
	}

	/**
	 * Adds a user to the database
	 * 
	 * @param user User being added to the database
	 */
	public static void addUser(User user) {
		Document in = new Document().append("username", user.getUsername()).append("firstName", user.getFirstName())
				.append("lastName", user.getLastName()).append("email", user.getEmail())
				.append("hashedPassword", user.getHashedPassword());
		database.insertDocument(DATABASENAME, USERSCOLLECTION, in);
	}

	/**
	 * Finds a list of addresses given a query
	 * 
	 * @param query Parameters for address search
	 * @return List of addresses
	 */
	public static List<Address> findAddress(Document query) {
		List<Document> out = database.getDocument(DATABASENAME, ADDRESSCOLLECTION, query);
		List<Address> addresses = new ArrayList<Address>();
		for (Document doc : out) {
			addresses.add(new Address(doc.getString("firstName"), doc.getString("lastName"), doc.getString("street"),
					doc.getString("city"), doc.getString("state"), doc.getString("zip"), doc.getString("dateCreated")));
		}
		return addresses;
	}

	/**
	 * Adds an address to the database
	 * 
	 * @param address Address being added to the database
	 */
	public static void insertAddress(Address address) {
		Document in = new Document().append("firstName", address.getFirstName())
				.append("lastName", address.getLastName()).append("street", address.getStreet())
				.append("city", address.getCity()).append("state", address.getState()).append("zip", address.getZip())
				.append("dateCreated", address.getDateCreated()).append("username", address.getUsername());
		database.insertDocument(DATABASENAME, ADDRESSCOLLECTION, in);
	}

	/**
	 * Deletes an address
	 * 
	 * @param query Filter to define what addresses will be deleted
	 */
	public static void deleteAddress(Document query) {
		database.deleteDocument(DATABASENAME, ADDRESSCOLLECTION, query);
	}

	/**
	 * Updates an address
	 * 
	 * @param query  Filter to define what address will be updated
	 * @param update Fields being updated and their values
	 */
	public static void updateAddress(Document query, Document update) {
		database.updateDocument(DATABASENAME, ADDRESSCOLLECTION, query, update);
	}
}
