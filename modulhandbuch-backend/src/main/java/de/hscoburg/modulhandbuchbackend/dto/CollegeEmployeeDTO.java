package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

/**
 * This class is a data transfer object (DTO) for a college employee.
 */
@Data
public class CollegeEmployeeDTO {
	private Integer id;
	private String firstName;
	private String lastName;
	private String title;
	private String gender;
	private String email;
}
