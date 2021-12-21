package console;

import java.util.Scanner;

import library.database.DatabaseManager;
import library.settings.User;
import library.storage.StorageManager;

public class Dialogue {
	private static Scanner scanner;
	private static boolean running;

	public Dialogue() {
		scanner = new Scanner(System.in);
		running = true;
	}

	public void start() {
		firstStartup();
		startup();
		mainLoop();
		shutDown();
	}

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
		System.out.printf("Welcome %s, you can now access your address book!\n\n", User.getFirstName());
	}

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
					break;
				case ("2"):
					break;
				case ("3"):
					break;
				case ("4"):
					break;
				case ("5"):
					break;
				case ("6"):
					break;
				default:
					System.out.println("Sorry, that is not one of the options. Please try again");
			}
		}
	}

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
						System.out.println("The application is unable to make a connection to the server. Plase try again.");
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

	private void shutDown() {

	}

	private void login() {
		System.out.print("");
	}

	private void createAccount() {

	}
}
