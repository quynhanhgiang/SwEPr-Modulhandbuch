package de.hscoburg.modulhandbuchbackend.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleFullDTO extends ModuleDTO {
	private Integer id;
	private String abbreviation;
	private String moduleName;
	private CollegeEmployeeDTO moduleOwner;
	private List<ModuleVariationDTO> variations;
	private String cycle;
	private String duration;
	private List<CollegeEmployeeDTO> profs;
	private String language;
	private String usage;
	private String knowledgeRequirements;
	private String skills;
	private String content;
	private String examType;
	private String certificates;
	private String mediaType;
	private String literature;
	private String maternityProtection;
}
