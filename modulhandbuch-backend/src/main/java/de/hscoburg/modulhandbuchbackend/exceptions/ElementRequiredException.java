package de.hscoburg.modulhandbuchbackend.exceptions;

public class ElementRequiredException extends RuntimeException {
	
	public ElementRequiredException(String elementName) {
		super(String.format("%s is required but is null.", elementName));
	}
}
