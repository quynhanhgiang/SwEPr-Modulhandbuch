package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a request contains duplicate admission
 * requirements.
 */
public class DuplicateAdmissionRequirementsInRequestException extends DuplicateElementsInRequestException {
	
	public DuplicateAdmissionRequirementsInRequestException(Integer id) {
		super(id, "Admission requirement");
	}
}
