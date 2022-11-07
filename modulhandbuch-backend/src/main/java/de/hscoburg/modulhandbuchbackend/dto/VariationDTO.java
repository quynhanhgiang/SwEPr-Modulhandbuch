package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

@Data
public class VariationDTO {
	private String spo;
	private Integer semester;
	private Integer sws;
	private Integer ects;
	private String workLoad;
}
