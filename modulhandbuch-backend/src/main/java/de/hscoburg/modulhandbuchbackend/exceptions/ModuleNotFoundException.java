package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when a module is
 * not found.
 */
public class ModuleNotFoundException extends ElementNotFoundException {

	public ModuleNotFoundException(Integer id) {
		super(id, "Module");
	}
}
