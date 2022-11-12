package de.hscoburg.modulhandbuchbackend.exceptions;

public class ModuleNotFoundException extends RuntimeException {
	
	public ModuleNotFoundException(Integer id) {
		super("Could not find module " + id);
	}
}
