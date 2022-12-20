package de.hscoburg.modulhandbuchbackend.exceptions;

public class ModuleTypeNotFoundException extends ElementNotFoundException {
	
	public ModuleTypeNotFoundException(Integer id) {
		super(id, "Module type");
	}
}
