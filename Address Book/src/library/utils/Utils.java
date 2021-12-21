package library.utils;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class Utils {
	public static String hash(String text) {
		return Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
	}
}
