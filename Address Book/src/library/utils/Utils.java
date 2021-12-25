package library.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

/**
 * 
 * @author Logan Miller
 * 
 *         General functions that are used a lot
 *
 */
public class Utils {
	/**
	 * Hashes text
	 * 
	 * @param text Original text
	 * @return Hashed version of original text
	 */
	public static String hash(String text) {
		return Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
	}

	/**
	 * Returns string with specific number of spaces. Used mainly for formatting
	 * 
	 * @param numberOfSpaces Number of spaces to be returned
	 * @return String of spaces
	 */
	public static String spaces(int numberOfSpaces) {
		String out = "";
		while (out.length() < numberOfSpaces) {
			out += " ";
		}
		return out;
	}

	/**
	 * Clears console screen (works with Windows) (not sure about Unix OS)
	 */
	public static void clearScreen() {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
		}
	}
}
