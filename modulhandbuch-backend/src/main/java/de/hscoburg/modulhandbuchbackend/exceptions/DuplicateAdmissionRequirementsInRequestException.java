package de.hscoburg.modulhandbuchbackend.exceptions;

public class DuplicateAdmissionRequirementsInRequestException extends DuplicateElementsInRequestException {
	
	public DuplicateAdmissionRequirementsInRequestException(Integer id) {
		super(id, "Admission requirement");
	}
}
