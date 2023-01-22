package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when a college employee is
 * not found.
 */
public class CollegeEmployeeNotFoundException extends ElementNotFoundException {
	
	public CollegeEmployeeNotFoundException(Integer id) {
		super(id, "College employee");
	}
}
