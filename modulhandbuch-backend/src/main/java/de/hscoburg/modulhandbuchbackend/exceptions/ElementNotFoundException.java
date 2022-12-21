package de.hscoburg.modulhandbuchbackend.exceptions;

public class ElementNotFoundException extends RuntimeException {
	
	public ElementNotFoundException(Integer id, String elementName) {
		super(String.format("%s with id %d not found.", elementName, id));
	}
}
