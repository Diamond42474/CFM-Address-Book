package library.database;

/**
 * 
 * @author Logan Miller
 *
 *         User used with database to provide a login system and have addresses
 *         associated with different users
 */
public class User {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String hashedPassword;

	/**
	 * Constructor that sets up all necessary attributes of the user
	 * 
	 * @param username       Unique username
	 * @param firstName      First Name
	 * @param lastName       Last name
	 * @param email          Email (not currently used)
	 * @param hashedPassword Hashed password
	 */
	public User(String username, String firstName, String lastName, String email, String hashedPassword) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.hashedPassword = hashedPassword;
	}

	/**
	 * Returns username
	 * 
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns first name
	 * 
	 * @return First name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns last name
	 * 
	 * @return Last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns email
	 * 
	 * @return Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns hashed password
	 * 
	 * @return Hashed Password
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

}
