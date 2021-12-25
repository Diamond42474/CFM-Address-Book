package library.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class FileService {
	/**
	 * Creates folder
	 * 
	 * @param dir path to folder
	 */
	static void makeFolder(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * Returns if a file exists
	 * 
	 * @param dir path to file
	 * @return if the file exists
	 */
	static boolean fileExists(String dir) {
		return new File(dir).exists();
	}

	/**
	 * Saves an object to a JSON file
	 * 
	 * @param filename name of file
	 * @param element  Object being saved
	 * @throws JsonProcessingException Thrown when there is an error parsing JSON
	 *                                 data
	 * @throws IOException             Thrown when there is an error saving the file
	 */
	public static void saveToFile(String filename, Object element) throws JsonProcessingException, IOException {
		File file = new File(filename);
		ObjectMapper om = new ObjectMapper();
		om.writerWithDefaultPrettyPrinter().writeValue(file, element);

	}

	/**
	 * Takes a type reference and returns the object loaded from the file mapped to
	 * the type of typeReference
	 * 
	 * @param <T>      Type being mapped
	 * @param filename name of file being read from
	 * @param token    TypeReference of type being mapped
	 * 
	 * @return An object of the token type
	 * @throws JsonParseException   Thrown when there is an error parsing JSON
	 * @throws JsonMappingException Thrown when there is an error mapping the object
	 *                              type
	 * @throws IOException          Thrown when there is an error loading the file
	 */
	public static <T> T loadFromFile(String filename, TypeReference<T> token)
			throws JsonParseException, JsonMappingException, IOException {
		T obj = null;
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		ObjectMapper om = new ObjectMapper();
		obj = om.readValue(br, token);
		return obj;
	}
}