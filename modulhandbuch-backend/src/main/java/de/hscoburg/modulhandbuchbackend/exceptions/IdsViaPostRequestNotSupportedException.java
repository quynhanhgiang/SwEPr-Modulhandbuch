package de.hscoburg.modulhandbuchbackend.exceptions;

public class IdsViaPostRequestNotSupportedException extends RuntimeException {
	
	public IdsViaPostRequestNotSupportedException() {
		super("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
	}
}
