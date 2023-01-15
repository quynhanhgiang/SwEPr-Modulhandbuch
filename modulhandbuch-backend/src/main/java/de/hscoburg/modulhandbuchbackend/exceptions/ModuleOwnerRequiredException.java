package de.hscoburg.modulhandbuchbackend.exceptions;

public class ModuleOwnerRequiredException extends ElementRequiredException {
	
	public ModuleOwnerRequiredException() {
		super("Module owner");
	}
}
