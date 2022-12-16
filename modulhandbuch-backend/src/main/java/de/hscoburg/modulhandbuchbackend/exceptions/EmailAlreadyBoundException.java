package de.hscoburg.modulhandbuchbackend.exceptions;

public class EmailAlreadyBoundException extends RuntimeException {
	
	public EmailAlreadyBoundException(String email, String name) {
		super(String.format("Email address %s is already bound to %s", email, name));
	}
}
