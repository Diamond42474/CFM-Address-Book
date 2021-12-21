package library.settings;

public class User {
	private static String username;
	private static String firstName;
	private static String lastName;
	private static String email;

	public static void setUser(String username, String firstName, String lastName, String email) {
		User.username = username;
		User.firstName = firstName;
		User.lastName = lastName;
		User.email = email;
	}

	public static String getUsername() {
		return username;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static String getLastName() {
		return lastName;
	}

	public static String getEmail() {
		return email;
	}

}
