package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when a module manual is
 * not found.
 */
public class ModuleManualNotFoundException extends ElementNotFoundException {

	public ModuleManualNotFoundException(Integer id) {
		super(id, "Module manual");
	}
}
