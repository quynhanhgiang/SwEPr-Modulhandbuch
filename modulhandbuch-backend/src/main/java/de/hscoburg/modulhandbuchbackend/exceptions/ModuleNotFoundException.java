package de.hscoburg.modulhandbuchbackend.exceptions;

public class ModuleNotFoundException extends ElementNotFoundException {
	
	public ModuleNotFoundException(Integer id) {
		super(id, "Module");
	}
}
