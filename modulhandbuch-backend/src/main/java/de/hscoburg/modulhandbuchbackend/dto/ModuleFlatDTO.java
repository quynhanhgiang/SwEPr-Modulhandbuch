package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleFlatDTO extends ModuleDTO {
	private Integer id;
	private String abbreviation;
	private String moduleName;
	private String moduleOwner;
}
