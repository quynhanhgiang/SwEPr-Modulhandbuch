package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This class is a custom exception that is thrown when a segment is
 * not found.
 */
public class SegmentNotFoundException extends ElementNotFoundException {

	public SegmentNotFoundException(Integer id) {
		super(id, "Segment");
	}
}
