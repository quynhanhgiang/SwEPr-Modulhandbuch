package de.hscoburg.modulhandbuchbackend.dto;

import lombok.Data;

/**
 * This class is a data transfer object (DTO) for information about a file.
 */
@Data
public class FileInfoDTO {
	String filename;
	String link;
	String timestamp;
}
