package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateElementsInRequestException;

/**
 * This class is a controller advice that will be applied to all controllers in
 * the application.
 */
@ControllerAdvice
public class DuplicateElementsInRequestAdvice {

	/**
	 * This function will be called whenever an
	 * {@link DuplicateElementsInRequestException} is thrown. It will return a 422
	 * status code and a plain text message with the exception message.
	 * 
	 * @param exception The exception that was thrown.
	 * @return A {@link ResponseEntity} with a status of 422, a content type of
	 *         text/plain, and a body containing
	 *         the exception message.
	 */
	@ExceptionHandler(DuplicateElementsInRequestException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<String> duplicateElementsInRequestHandler(DuplicateElementsInRequestException exception) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.TEXT_PLAIN)
				.body(exception.getMessage());
	}
}
