package de.hscoburg.modulhandbuchbackend.exceptions;

public class DuplicateModuleTypesInRequestException extends DuplicateElementsInRequestException {
	
	public DuplicateModuleTypesInRequestException(Integer id) {
		super(id, "Module type");
	}
}
