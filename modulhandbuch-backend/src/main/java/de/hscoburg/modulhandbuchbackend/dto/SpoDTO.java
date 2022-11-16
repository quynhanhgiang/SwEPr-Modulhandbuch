package de.hscoburg.modulhandbuchbackend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SpoDTO {
	private Integer id;
	private String link;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String course;
}
