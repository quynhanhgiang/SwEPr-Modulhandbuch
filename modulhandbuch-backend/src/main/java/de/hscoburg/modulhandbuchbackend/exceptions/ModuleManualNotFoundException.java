package de.hscoburg.modulhandbuchbackend.exceptions;

public class ModuleManualNotFoundException extends ElementNotFoundException {
	
	public ModuleManualNotFoundException(Integer id) {
		super(id, "Module manual");
	}
}
