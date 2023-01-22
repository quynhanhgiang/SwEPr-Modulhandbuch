package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.EmailAlreadyBoundException;

/**
 * This class is a controller advice that will be applied to all controllers in the application.
 */
@ControllerAdvice
public class EmailAlreadyBoundAdvice {

	/**
	 * This function will be called whenever an {@link EmailAlreadyBoundException} is thrown. It will return a 409
	 * status code and a plain text message with the exception message.
	 * 
	 * @param exception The exception that was thrown.
	 * @return A {@link ResponseEntity} with a status of 409, a content type of text/plain, and a body containing
	 * the exception message.
	 */
	@ExceptionHandler(EmailAlreadyBoundException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<String> emailAlreadyBoundHandler(EmailAlreadyBoundException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.contentType(MediaType.TEXT_PLAIN)
			.body(exception.getMessage());
	}
}
