package library.storage;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Used as a way to pass a type that has it's own genarics such as a
 * HashMap<K,V>. Thanks to Baeldung tutorials for helping me figure this out and
 * providing a fix to my problem
 * 
 * @author Logan Miller
 *
 * @param <T> Type to later get passed to a method
 */
abstract class TypeReference<T> {
	/**
	 * Type used to later get passed to the method
	 */
	private final Type type;

	/**
	 * Initializes the type
	 */
	public TypeReference() {
		Type superclass = getClass().getGenericSuperclass();
		type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
	}

	/**
	 * Returns the type being passed to the method
	 * 
	 * @return Type to go to method
	 */
	public Type getType() {
		return type;
	}
}