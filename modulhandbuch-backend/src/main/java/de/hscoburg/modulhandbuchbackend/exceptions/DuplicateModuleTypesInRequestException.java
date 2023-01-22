package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a request contains duplicate module
 * types.
 */
public class DuplicateModuleTypesInRequestException extends DuplicateElementsInRequestException {
	
	public DuplicateModuleTypesInRequestException(Integer id) {
		super(id, "Module type");
	}
}
