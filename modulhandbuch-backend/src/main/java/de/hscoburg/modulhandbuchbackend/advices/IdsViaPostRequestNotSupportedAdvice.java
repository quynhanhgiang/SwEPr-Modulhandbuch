package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.IdsViaPostRequestNotSupportedException;

/**
 * This class is a controller advice that will be applied to all controllers in
 * the application.
 */
@ControllerAdvice
public class IdsViaPostRequestNotSupportedAdvice {

	/**
	 * This function will be called whenever an
	 * {@link IdsViaPostRequestNotSupportedException} is thrown. It will return a
	 * 422
	 * status code and a plain text message with the exception message.
	 * 
	 * @param exception The exception that was thrown.
	 * @return A {@link ResponseEntity} with a status of 422, a content type of
	 *         text/plain, and a body containing
	 *         the exception message.
	 */
	@ExceptionHandler(IdsViaPostRequestNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<String> idsViaPostRequestNotSupportedHandler(
			IdsViaPostRequestNotSupportedException exception) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.TEXT_PLAIN)
				.body(exception.getMessage());
	}
}
