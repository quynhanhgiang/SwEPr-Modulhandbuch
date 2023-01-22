package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when an element is
 * not found.
 */
public class ElementNotFoundException extends RuntimeException {

	public ElementNotFoundException(Integer id, String elementName) {
		super(String.format("%s with id %d not found.", elementName, id));
	}
}
