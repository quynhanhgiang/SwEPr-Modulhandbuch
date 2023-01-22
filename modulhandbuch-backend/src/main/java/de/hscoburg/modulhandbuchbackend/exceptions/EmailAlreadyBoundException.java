package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when an email address is already bound to a user.
 */
public class EmailAlreadyBoundException extends RuntimeException {
	
	public EmailAlreadyBoundException(String email, String name) {
		super(String.format("Email address %s is already bound to %s", email, name));
	}
}
