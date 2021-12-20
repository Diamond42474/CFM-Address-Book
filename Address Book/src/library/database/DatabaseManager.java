package library.database;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bson.Document;

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

	/**
	 * Only used for testing and will be removed later
	 * 
	 * @param args N/A
	 */
	public static void main(String[] args) {
		try {
			start("");
		} catch (Exception e) {
			System.out.println("There was an error, bro");
			return;
		}
		Address address = new Address("James", "Bond", "2014 W Malabu Point", "Malabu", "Alaska", "1212");
		AddressQuery.Insert.addAddress(address);

		for (Address address1 : AddressQuery.Find.byFirstName("James")) {
			System.out.println(address1.getCity());
		}
		AddressQuery.Update.firstName("James", "Jack");
		// AddressQuery.Delete.byFirstName("James");
	}

	/**
	 * Creates a connection to a specified database (local or remote)
	 * 
	 * @param connectionString Connection String to any remote database. Leave this
	 *                         blank if you want to connect to localhost
	 * @throws Exception Thrown if there is an error when connecting to the
	 *                   database. Make sure that the connection string is right.
	 */
	public static void start(String connectionString) throws Exception {
		try {
			database = new Database(debuggerLevel);
			database.connect(connectionString);
		} catch (Exception e) {
			throw new Exception(e);
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
	 * 
	 * @author Logan Miller
	 * 
	 *         Used for all queries dealing with addresses
	 *
	 */
	public static class AddressQuery {
		/**
		 * 
		 * @author Logan Miller
		 * 
		 *         Used for all queries dealing with searching
		 *
		 */
		public static class Find {
			/**
			 * Finds documents based off of the first name and turns them into Addresses
			 * 
			 * @param firstName First Name of person
			 * @return List of Addresses matching the query
			 */
			public static List<Address> byFirstName(String firstName) {
				Document query = new Document().append("firstName", firstName);
				List<Document> out = database.getDocument(DATABASENAME, ADDRESSCOLLECTION, query);
				List<Address> addresses = new ArrayList<Address>();
				for (Document doc : out) {
					addresses.add(new Address(doc.getString("firstName"), doc.getString("lastName"),
							doc.getString("street"), doc.getString("city"), doc.getString("state"),
							doc.getString("zip"), doc.getString("dateCreated")));
				}
				return addresses;
			}
		}

		/**
		 * 
		 * @author Logan Miller
		 * 
		 *         Used for all queries dealing with adding documents
		 *
		 */
		public static class Insert {
			/**
			 * Adds an address to the collection
			 * 
			 * @param address Address being added
			 */
			public static void addAddress(Address address) {
				Document in = new Document().append("firstName", address.getFirstName())
						.append("lastName", address.getLastName()).append("street", address.getStreet())
						.append("city", address.getCity()).append("state", address.getState())
						.append("zip", address.getZip()).append("dateCreated", address.getDateCreated());
				database.insertDocument(DATABASENAME, ADDRESSCOLLECTION, in);
			}
		}

		/**
		 * 
		 * @author Logan Miller
		 * 
		 *         Used for all queries dealing with deleting documents
		 *
		 */
		public static class Delete {
			/**
			 * Deletes document based off of the firstName variable
			 * 
			 * Can delete multiple documents of the first name is not unique
			 * 
			 * @param firstName Person's first name
			 */
			public static void byFirstName(String firstName) {
				Document query = new Document().append("firstName", firstName);
				database.deleteDocument(DATABASENAME, ADDRESSCOLLECTION, query);
			}
		}

		/**
		 * 
		 * @author Logan Miller
		 * 
		 *         Used for all queries dealing with updating documents
		 *
		 */
		public static class Update {
			/**
			 * Updates a documents first name based off of it's first name
			 * 
			 * @param firstName    Person's current first name
			 * @param newFirstName new first name
			 */
			public static void firstName(String firstName, String newFirstName) {
				Document query = new Document().append("firstName", firstName);
				Document update = new Document().append("firstName", newFirstName);
				database.updateDocument(DATABASENAME, ADDRESSCOLLECTION, query, update);
			}
		}
	}
}
