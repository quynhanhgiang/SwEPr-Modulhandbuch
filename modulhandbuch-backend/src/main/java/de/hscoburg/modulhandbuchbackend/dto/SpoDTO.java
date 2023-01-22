package de.hscoburg.modulhandbuchbackend.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * This class is a data transfer object (DTO) for an spo.
 */
@Data
public class SpoDTO {
	private Integer id;
	private String link;
	private LocalDate startDate;
	private LocalDate endDate;
	private String course;
	private String degree;
}
