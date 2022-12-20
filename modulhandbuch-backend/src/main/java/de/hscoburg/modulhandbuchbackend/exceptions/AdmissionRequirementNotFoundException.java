package de.hscoburg.modulhandbuchbackend.exceptions;

public class AdmissionRequirementNotFoundException extends ElementNotFoundException {
	
	public AdmissionRequirementNotFoundException(Integer id) {
		super(id, "Admission requirement");
	}
}
