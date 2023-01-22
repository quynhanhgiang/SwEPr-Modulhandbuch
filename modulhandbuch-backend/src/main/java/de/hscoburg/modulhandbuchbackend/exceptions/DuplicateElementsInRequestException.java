package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a request contains duplicate elements.
 */
public class DuplicateElementsInRequestException extends RuntimeException {

	public DuplicateElementsInRequestException(Integer id, String elementName) {
		super(String.format("%s with id %d has duplicates in request.", elementName, id));
	}
}
