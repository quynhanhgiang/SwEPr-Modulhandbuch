package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class is a data transfer object (DTO) for a module with selected fields.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleFlatDTO extends ModuleDTO {
	private Integer id;
	private String abbreviation;
	private String moduleName;
	private String moduleOwner;
}
