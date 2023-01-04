package de.hscoburg.modulhandbuchbackend.exceptions;

public class DuplicateElementsInRequestException extends RuntimeException {
	
	public DuplicateElementsInRequestException(Integer id, String elementName) {
		super(String.format("%s with id %d has duplicates in request.", elementName, id));
	}
}
