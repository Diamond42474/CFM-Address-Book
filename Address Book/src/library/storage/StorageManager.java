package library.storage;

import com.fasterxml.jackson.core.type.TypeReference;

public class StorageManager {
	// Default location for connection string files
	private static final String parentFolder = System.getProperty("user.home") + "/Documents/CFM-Address-Book/";
	private static final String connectionStringFile = parentFolder + "database-credentials.json";

	public static String getConnectionString() throws Exception {
		if (FileService.fileExists(connectionStringFile)) {
			return FileService.loadFromFile(connectionStringFile, new TypeReference<String>() {
			});

		} else {
			return "";
		}
	}

	public static void setConnectionString(String connectionString) throws Exception {
		FileService.makeFolder(parentFolder);
		FileService.saveToFile(connectionStringFile, connectionString);
	}

	public static boolean connectionStringExists() {
		return FileService.fileExists(connectionStringFile);
	}
}
