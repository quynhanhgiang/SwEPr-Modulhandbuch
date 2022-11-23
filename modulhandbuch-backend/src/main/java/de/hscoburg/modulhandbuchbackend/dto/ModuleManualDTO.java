package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

@Data
public class ModuleManualDTO {
	private Integer id;
	private SpoDTO spo;
	private String semester;
}
