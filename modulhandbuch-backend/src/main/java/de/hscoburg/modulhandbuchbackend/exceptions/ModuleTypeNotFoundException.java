package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when a module type is
 * not found.
 */
public class ModuleTypeNotFoundException extends ElementNotFoundException {
	
	public ModuleTypeNotFoundException(Integer id) {
		super(id, "Module type");
	}
}
