package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a request contains duplicate segments.
 */
public class DuplicateSegmentsInRequestException extends DuplicateElementsInRequestException {
	
	public DuplicateSegmentsInRequestException(Integer id) {
		super(id, "Segment");
	}
}
