package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

/**
 * This class is a data transfer object (DTO) for a module manual.
 */
@Data
public class ModuleManualDTO {
	private Integer id;
	private SpoDTO spo;
	private String semester;
}
