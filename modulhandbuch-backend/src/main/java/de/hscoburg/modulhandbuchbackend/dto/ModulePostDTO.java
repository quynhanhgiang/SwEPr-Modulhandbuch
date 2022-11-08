package de.hscoburg.modulhandbuchbackend.dto;

import java.util.List;

import lombok.Data;

@Data
public class ModulePostDTO {
	private Integer id;
	private String moduleName;
	private String abbreviation;
	// TODO change to VariationDTO
	private List<String> variations;
	private String cycle;
	private String duration;
	private CollegeEmployeeDTO moduleOwner;
	private List<String> profs;
	private String language;
	private String usage;
	private String admissionRequirements;
	private String knowledgeRequirements;
	private String skills;
	private String content;
	private String examType;
	private String certificates;
	private String mediaTypes;
	private String literature;
	private String maternityProtection;
}
