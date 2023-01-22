package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.ElementNotFoundException;

/**
 * This class is a controller advice that will be applied to all controllers in the application.
 */
@ControllerAdvice
public class ElementNotFoundAdvice {
	
	/**
	 * This function will be called whenever an {@link ElementNotFoundException} is thrown. It will return a 404
	 * status code and a plain text message with the exception message.
	 * 
	 * @param exception The exception that was thrown.
	 * @return A {@link ResponseEntity} with a status of 404, a content type of text/plain, and a body containing
	 * the exception message.
	 */
	@ExceptionHandler(ElementNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> elementNotFoundHandler(ElementNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.contentType(MediaType.TEXT_PLAIN)
			.body(exception.getMessage());
	}
}
