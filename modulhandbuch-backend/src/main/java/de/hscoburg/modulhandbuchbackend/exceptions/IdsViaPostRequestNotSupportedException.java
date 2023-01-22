package de.hscoburg.modulhandbuchbackend.exceptions;

/**
 * This exception is thrown when a POST request contains an id different to null.
 */
public class IdsViaPostRequestNotSupportedException extends RuntimeException {
	
	public IdsViaPostRequestNotSupportedException() {
		super("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
	}
}
