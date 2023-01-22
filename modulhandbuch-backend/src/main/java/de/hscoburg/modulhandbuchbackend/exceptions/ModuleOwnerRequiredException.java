package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a module owner is required but not provided.
 */
public class ModuleOwnerRequiredException extends ElementRequiredException {

	public ModuleOwnerRequiredException() {
		super("Module owner");
	}
}
