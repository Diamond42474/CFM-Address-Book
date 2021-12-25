package library.storage;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 
 * @author Logan Miller
 * 
 *         Manages reading and writing data to and from long term storage
 *
 */
public class StorageManager {
	// Default location for connection string files
	private static final String parentFolder = System.getProperty("user.home") + "/Documents/CFM-Address-Book/";
	private static final String connectionStringFile = parentFolder + "database-credentials.json";

	/**
	 * Grabs the database connection string from long-term storage
	 * 
	 * @return Database Connection String
	 * @throws Exception Thrown if the file is not found
	 */
	public static String getConnectionString() throws Exception {
		if (FileService.fileExists(connectionStringFile)) {
			return FileService.loadFromFile(connectionStringFile, new TypeReference<String>() {
			});

		} else {
			return "";
		}
	}

	/**
	 * Writes the connection string to long-term storage
	 * 
	 * @param connectionString Database connection string
	 * @throws Exception Thrown if there is an error writing to the file
	 */
	public static void setConnectionString(String connectionString) throws Exception {
		FileService.makeFolder(parentFolder);
		FileService.saveToFile(connectionStringFile, connectionString);
	}

	/**
	 * Checks if the connection string file is already in long-term storage
	 * 
	 * @return If the connection string exists
	 */
	public static boolean connectionStringExists() {
		return FileService.fileExists(connectionStringFile);
	}
}
