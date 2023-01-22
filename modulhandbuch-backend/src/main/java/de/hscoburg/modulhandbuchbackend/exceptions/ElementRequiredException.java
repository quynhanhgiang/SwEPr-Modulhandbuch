package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a element is required but not provided.
 */
public class ElementRequiredException extends RuntimeException {

	public ElementRequiredException(String elementName) {
		super(String.format("%s is required but is null.", elementName));
	}
}
