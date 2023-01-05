package de.hscoburg.modulhandbuchbackend.exceptions;

public class ModuleOwnerRequiredException extends RuntimeException {
	
	public ModuleOwnerRequiredException() {
		super("Module owner");
	}
}
