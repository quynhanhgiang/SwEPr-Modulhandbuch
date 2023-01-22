package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when an spo is
 * not found.
 */
public class SpoNotFoundException extends ElementNotFoundException {

	public SpoNotFoundException(Integer id) {
		super(id, "Spo");
	}
}
