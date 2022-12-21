package de.hscoburg.modulhandbuchbackend.exceptions;

public class DuplicateSegmentsInRequestException extends DuplicateElementsInRequestException {
	
	public DuplicateSegmentsInRequestException(Integer id) {
		super(id, "Segment");
	}
}
