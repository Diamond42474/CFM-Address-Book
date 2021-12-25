package console;

import java.util.List;
import java.util.Scanner;
import org.bson.Document;
import library.database.Address;
import library.database.DatabaseManager;
import library.database.User;
import library.storage.StorageManager;
import library.utils.Utils;

/**
 * This class controls all of the user interaction to and from the database.
 * Everything from logging in, to editing existing addresses
 * 
 * @author Logan Miller
 *
 */
public class Dialogue {
	private static Scanner scanner;
	private static boolean running;
	private static User user;

	/**
	 * Constructor that sets up the necessary resources
	 */
	public Dialogue() {
		scanner = new Scanner(System.in);
		running = true;
	}

	/**
	 * Starts the dialogue and controls the overall flow of dialogue stages:
	 * 
	 * - startup
	 * 
	 * Gets the user logged in, the database setup, and can create a new user
	 * 
	 * - main loop
	 * 
	 * Controls all main functions such as adding an address, editing, searching,
	 * and removing
	 * 
	 * - shutdown
	 * 
	 * Cleans up resources
	 */
	public void start() {
		firstStartup();
		startup();
		mainLoop();
		shutDown();
	}

	/**
	 * Connects to the database and lets the user login or create a new account
	 */
	private void startup() {
		System.out.print("Hello, welcome to your address book!\n\n");
		System.out.println("Would you like to login or create a new account?\n0: Login\n1: Create new account");
		switch (scanner.nextLine()) {
			case "0":
				login();
				break;
			case "1":
				createAccount();
				break;
			default:
				System.out.println("That is not one of the options...");
				startup();
		}
		Utils.clearScreen();
		System.out.printf("Welcome %s, you can now access your address book!\n\n", user.getFirstName());
	}

	/**
	 * Continually loops through all commands until the user decides to close the
	 * application.
	 * 
	 * Commands:
	 * 
	 * - Exit - Switch Account - View Address Book - Search for Address - Add
	 * Address - Remove Address - Edit Address
	 */
	private void mainLoop() {
		while (running) {
			System.out.println("Here is a list of available commands:");
			System.out.println("" + "0: Exit\n" + "1: Switch Accounts\n" + "2: View Address Book\n"
					+ "3: Search for Address\n" + "4: Add Address\n" + "5: Remove Address\n" + "6: Edit Address\n");
			switch (scanner.nextLine()) {
				case ("0"):
					running = false;
					break;
				case ("1"):
					Utils.clearScreen();
					login();
					break;
				case ("2"):
					Utils.clearScreen();
					viewAddressBook();
					break;
				case ("3"):
					Utils.clearScreen();
					searchForAddress();
					break;
				case ("4"):
					Utils.clearScreen();
					addAddress();
					break;
				case ("5"):
					Utils.clearScreen();
					removeAddress();
					break;
				case ("6"):
					Utils.clearScreen();
					editAddress();
					break;
				default:
					System.out.println("Sorry, that is not one of the options. Please try again");
			}
		}
	}

	/**
	 * Formats and displays list of addresses in easy to read format
	 * 
	 * @param list List of Addresses needing to be displayed
	 */
	private void displayAddresses(List<Address> list) {
		int[] lengths = { 12, 11, 8, 6, 7, 10 };
		for (int i = 0; i < list.size(); i++) {
			Address a = list.get(i);
			if (a.getFirstName().length() > lengths[0]) {
				lengths[0] = a.getFirstName().length() + 1;
			}
			if (a.getLastName().length() > lengths[1]) {
				lengths[1] = a.getLastName().length() + 1;
			}
			if (a.getStreet().length() > lengths[2]) {
				lengths[2] = a.getStreet().length() + 1;
			}
			if (a.getCity().length() > lengths[3]) {
				lengths[3] = a.getCity().length() + 1;
			}
			if (a.getState().length() > lengths[4]) {
				lengths[4] = a.getState().length() + 1;
			}
			if (a.getZip().length() > lengths[5]) {
				lengths[5] = a.getZip().length() + 1;
			}
		}
		System.out.printf("| First Name%s| Last Name%s| Street%s| City%s| State%s| Zip Code%s|\n",
				Utils.spaces(lengths[0] - 10), Utils.spaces(lengths[1] - 9), Utils.spaces(lengths[2] - 6),
				Utils.spaces(lengths[3] - 4), Utils.spaces(lengths[4] - 5), Utils.spaces(lengths[5] - 8));
		System.out.println("-");
		for (int i = 0; i < list.size(); i++) {
			Address a = list.get(i);
			System.out.printf("| %s| %s| %s| %s| %s| %s|\n-\n",
					a.getFirstName() + Utils.spaces(lengths[0] - a.getFirstName().length()),
					a.getLastName() + Utils.spaces(lengths[1] - a.getLastName().length()),
					a.getStreet() + Utils.spaces(lengths[2] - a.getStreet().length()),
					a.getCity() + Utils.spaces(lengths[3] - a.getCity().length()),
					a.getState() + Utils.spaces(lengths[4] - a.getState().length()),
					a.getZip() + Utils.spaces(lengths[5] - a.getZip().length()));
		}
	}

	/**
	 * Displays all addresses belonging to the user
	 */
	private void viewAddressBook() {

		displayAddresses(DatabaseManager.findAddress(new Document().append("username", user.getUsername())));
	}

	/**
	 * Goes through a set of questions to allow the user to create a search filter
	 * and generate a Document used for a database query
	 * 
	 * @return A Document used for database queries
	 */
	private Document queryGenerator() {
		boolean notDone = true;
		System.out.println("Create filter and enter 0 when ready.");
		Document query = new Document();
		while (notDone) {
			System.out.println(
					"Add filter option:\n0: Done\n1: First Name\n2: Last Name\n3: Street\n4: City\n5: State\n6: Zip Code\n");
			switch (scanner.nextLine()) {
				case ("0"):
					notDone = false;
					break;
				case ("1"):
					System.out.println("Enter a first name:");
					query.append("firstName", scanner.nextLine());
					break;
				case ("2"):
					System.out.println("Enter a last name:");
					query.append("lastName", scanner.nextLine());
					break;
				case ("3"):
					System.out.println("Enter a street:");
					query.append("street", scanner.nextLine());
					break;
				case ("4"):
					System.out.println("Enter a city:");
					query.append("city", scanner.nextLine());
					break;
				case ("5"):
					System.out.println("Enter a state:");
					query.append("state", scanner.nextLine());
					break;
				case ("6"):
					System.out.println("Enter a zip:");
					query.append("zip", scanner.nextLine());
					break;
				default:
					System.out.println("That is not an option.");
			}
		}
		return query;
	}

	/**
	 * Searches for an address given a filter
	 */
	private void searchForAddress() {
		Utils.clearScreen();
		displayAddresses(DatabaseManager.findAddress(queryGenerator().append("username", user.getUsername())));
	}

	/**
	 * Creates a new address and adds it to the database
	 */
	private void addAddress() {

		System.out.println("First Name:");
		String firstName = scanner.nextLine();
		System.out.println("Last Name:");
		String lastName = scanner.nextLine();
		System.out.println("Street:");
		String street = scanner.nextLine();
		System.out.println("City:");
		String city = scanner.nextLine();
		System.out.println("State:");
		String state = scanner.nextLine();
		System.out.println("Zip:");
		String zip = scanner.nextLine();

		Address a = new Address(firstName, lastName, street, city, state, zip, user.getUsername());
		DatabaseManager.insertAddress(a);
	}

	/**
	 * Removes an address from the database
	 */
	private void removeAddress() {
		Utils.clearScreen();
		Document query = queryGenerator().append("username", user.getUsername());
		Utils.clearScreen();
		System.out.println("The following addresses will be deleted:");
		displayAddresses(DatabaseManager.findAddress(query));
		System.out.println("Are you sure you want to proceed?\n0: Yes\n1: No");
		switch (scanner.nextLine()) {
			case ("0"):
				DatabaseManager.deleteAddress(query);
				break;
			case ("1"):
				System.out.println("Operation has been cancelled");
				break;
			default:
				System.out.println("Operation has been cancelled");
		}
		Utils.clearScreen();
	}

	/**
	 * Edits one or more databases depending on the search filter
	 */
	private void editAddress() {
		Utils.clearScreen();
		Document query = queryGenerator().append("username", user.getUsername());
		Utils.clearScreen();
		System.out.println("The following document(s) will be edited:");
		List<Address> list = DatabaseManager.findAddress(query);
		displayAddresses(list);
		System.out.println("Would you like to proceed?\n0: Yes\n1: No");
		switch (scanner.nextLine()) {
			case ("0"):
				Utils.clearScreen();
				System.out.println("Enter in the following fields and how you would like them to be updated:");
				Document update = queryGenerator();
				Utils.clearScreen();
				System.out.println("The following will be changed");
				displayAddresses(list);
				for (int i = 0; i < list.size(); i++) {
					if (update.containsKey("firstName")) {
						list.get(i).setFirstName(update.getString("firstName"));
					}
					if (update.containsKey("lastName")) {
						list.get(i).setLastName(update.getString("lastName"));
					}
					if (update.containsKey("street")) {
						list.get(i).setStreet(update.getString("street"));
					}
					if (update.containsKey("city")) {
						list.get(i).setCity(update.getString("city"));
					}
					if (update.containsKey("state")) {
						list.get(i).setState(update.getString("state"));
					}
					if (update.containsKey("zip")) {
						list.get(i).setZip(update.getString("zip"));
					}
				}
				System.out.print("\n");
				displayAddresses(list);
				System.out.println("\nDo you want to proceed?\n0: Yes\n1: No");
				switch (scanner.nextLine()) {
					case ("0"):
						DatabaseManager.updateAddress(query, update);
						System.out.println("Address has been updated");
						break;
					case ("1"):
						System.out.println("Operation has been cancelled");
						break;
					default:
						System.out.println("Operation has been cancelled");

				}
				break;
			case ("1"):
				System.out.println("Operation has been cancelled");
				break;
			default:
				System.out.println("Operation has been cancelled");
		}

	}

	/**
	 * Used to setup the database and user if the application has never been ran
	 */
	private void firstStartup() {
		if (!StorageManager.connectionStringExists()) {
			System.out.print("Hello, looks like this is your first time using the application!\n"
					+ "There are just a couple things to do before you can start using your address book!\n\n");

			System.out.println("First things first, lets get you setup with your database!\n"
					+ "Plese select one of the following options:\n" + "0: Connect to server using connection string\n"
					+ "1: Use local machine for database");
			switch (scanner.nextLine()) {
				case ("0"):
					System.out.println("Please enter your connection string:");
					String con = scanner.nextLine();
					try {
						StorageManager.setConnectionString(con);
					} catch (Exception e) {
						System.out.println("The application was not able to save the connection string.\n"
								+ "Please contact your system admin for help.");
						firstStartup();
					}
					if (!DatabaseManager.checkConnection(con)) {
						System.out.println(
								"The application is unable to make a connection to the server. Plase try again.");
						firstStartup();
					}
					break;
				case ("1"):
					try {
						StorageManager.setConnectionString("");
					} catch (Exception e) {
						System.out.println("The application was not able to save the connection string.\n"
								+ "Please contact your system admin for help.");
						firstStartup();
					}
					if (!DatabaseManager.checkConnection("")) {
						System.out.println("MongoDB is not running on your system."
								+ " This must be installed in order for the application to use your local machine");
						firstStartup();
					}
					break;
				default:
					System.out.println("That is not one of the options, please try again...");
					firstStartup();
			}

		}
		startDatabase();
	}

	/**
	 * Starts up the database using the connection string stored in the file
	 */
	private void startDatabase() {
		try {
			DatabaseManager.start();
		} catch (Exception e) {
			System.out.println("There was an error when attempting to connect to the database...\n"
					+ "Make sure you are connected to the internet and press enter when you are ready.");
			scanner.nextLine();
			startDatabase();
		}
	}

	/**
	 * Closes all necessary resources
	 */
	private void shutDown() {
		scanner.close();
		Utils.clearScreen();
		System.out.println("Your address book has been safely stored in the cloud!\nHave a good day!");
	}

	/**
	 * Logs in a user and verifies them using hashed passwords
	 */
	private void login() {
		System.out.println("Username:");
		String username = scanner.nextLine();
		System.out.println("Password:");
		String password = scanner.nextLine();
		List<User> users = DatabaseManager
				.findUser(new Document().append("username", username).append("hashedPassword", Utils.hash(password)));
		if (users.size() > 0) {
			user = users.get(0);
		} else {
			System.out.println(
					"The username or password you entered is not correct.\n0: Try Again\n1: Create new account");
			switch (scanner.nextLine()) {
				case ("0"):
					login();
					break;
				case ("1"):
					createAccount();
					break;
				default:
					login();
			}
		}
	}

	/**
	 * Creates a new user account
	 */
	private void createAccount() {
		System.out.println("Enter in a username:");
		String username = scanner.nextLine();
		System.out.println("Enter in your first name:");
		String firstName = scanner.nextLine();
		System.out.println("Enter in your last name:");
		String lastName = scanner.nextLine();
		System.out.println("Enter in your email:");
		String email = scanner.nextLine();
		System.out.println("Enter in a password:");
		String password = scanner.nextLine();

		System.out.printf(
				"Is the following information correct?"
						+ "\nUsername: %s\nFirst Name: %s\nLast Name: %s\nEmail: %s\nPassword: %s\n0: Yes\n1: No\n",
				username, firstName, lastName, email, password);
		switch (scanner.nextLine()) {
			case ("0"):
				user = new User(username, firstName, lastName, email, Utils.hash(password));
				if (DatabaseManager.findUser(new Document().append("username", username)).size() == 0) {
					DatabaseManager.addUser(user);
				} else {
					System.out.println("This user already exists. Please try a different username.");
					createAccount();
				}
				break;
			case ("1"):
				createAccount();
				break;
			default:
				System.out.println("That is not an option. Please try again.");
				createAccount();
		}
		login();
	}
}
