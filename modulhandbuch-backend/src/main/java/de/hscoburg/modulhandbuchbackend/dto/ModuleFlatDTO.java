package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

@Data
public class ModuleFlatDTO {
	private Integer id;
	private String abbreviation;
	private String moduleName;
	private CollegeEmployeeDTO moduleOwner;
}
