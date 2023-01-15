package de.hscoburg.modulhandbuchbackend.exceptions;

public class CollegeEmployeeNotFoundException extends ElementNotFoundException {
	
	public CollegeEmployeeNotFoundException(Integer id) {
		super(id, "College employee");
	}
}
