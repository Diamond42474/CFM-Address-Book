package library.database;

import java.util.Date;

/**
 * 
 * @author Logan Miller
 *
 */
public class Address {
	private String firstName;
	private String lastName;

	private String street;
	private String city;
	private String state;
	private String zip;
	private String dateCreated;

	private String username;

	/**
	 * Constructor to create the Address.
	 * 
	 * This is used as a template for inserting and finding documents from the
	 * database
	 * 
	 * @param firstName Person's first name
	 * @param lastName  Person's last name
	 * @param street    Person's street address
	 * @param city      Person's city
	 * @param state     Person's state
	 * @param zip       Person's zip code
	 * @param username  Username associated with the address
	 */
	public Address(String firstName, String lastName, String street, String city, String state, String zip,
			String username) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.dateCreated = new Date().toString();
		this.username = username;
	}

	/**
	 * Used when retrieving a document from the database as the time created can now
	 * be set instead of it being reset by the original constructor
	 * 
	 * @param firstName   Person's first name
	 * @param lastName    Person's last name
	 * @param street      Person's street address
	 * @param city        Person's city
	 * @param state       Person's state
	 * @param zip         Person's zip code
	 * @param dateCreated The timestamp that the document was originally created
	 * @param username  Username associated with the address
	 */
	public Address(String firstName, String lastName, String street, String city, String state, String zip,
			String dateCreated, String username) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.dateCreated = dateCreated;
		this.username = username;
	}

	/**
	 * Returns the Person's first name
	 * 
	 * @return First Name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the Person's last name
	 * 
	 * @return Last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the Person's street address
	 * 
	 * @return Street Address
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Returns the Person's city
	 * 
	 * @return City
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the Person's state
	 * 
	 * @return State
	 */
	public String getState() {
		return state;
	}

	/**
	 * Returns the Person's zip code
	 * 
	 * @return Zip code
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Returns the timestamp that the document was created
	 * 
	 * @return timestamp of when the document was created
	 */
	public String getDateCreated() {
		return dateCreated;
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
	 * Sets addresses first name
	 * 
	 * @param firstName First name of address
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets addresses last name
	 * 
	 * @param lastName Last name of address
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets street of address
	 * 
	 * @param street Street of address
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Sets city of address
	 * 
	 * @param city City of address
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Sets state of address
	 * 
	 * @param state State of address
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Sets zip of address
	 * 
	 * @param zip Zip code of address
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

}
