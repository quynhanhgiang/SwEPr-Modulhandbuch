package de.hscoburg.modulhandbuchbackend.exceptions;

public class SpoNotFoundException extends ElementNotFoundException {
	
	public SpoNotFoundException(Integer id) {
		super(id, "Spo");
	}
}
