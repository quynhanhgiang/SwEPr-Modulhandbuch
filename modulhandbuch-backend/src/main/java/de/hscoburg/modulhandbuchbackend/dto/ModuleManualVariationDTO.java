package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

@Data
public class ModuleManualVariationDTO {
	private ModuleFlatDTO module;
	private Integer semester;
	private Integer sws;
	private Integer ects;
	private String workLoad;
	private StructureDTO moduleType;
	private StructureDTO segment;
	private EnumDTO admissionRequirement;
}
