package de.hscoburg.modulhandbuchbackend.exceptions;

public class SegmentNotFoundException extends ElementNotFoundException {
	
	public SegmentNotFoundException(Integer id) {
		super(id, "Segment");
	}
}
