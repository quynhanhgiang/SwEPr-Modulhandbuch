package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when an admission requirement
 * is not found.
 */
public class AdmissionRequirementNotFoundException extends ElementNotFoundException {

	public AdmissionRequirementNotFoundException(Integer id) {
		super(id, "Admission requirement");
	}
}
