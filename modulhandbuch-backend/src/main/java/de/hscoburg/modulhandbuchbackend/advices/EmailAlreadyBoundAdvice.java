package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.EmailAlreadyBoundException;

@ControllerAdvice
public class EmailAlreadyBoundAdvice {

	@ExceptionHandler(EmailAlreadyBoundException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<String> moduleNotFoundHandler(EmailAlreadyBoundException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.contentType(MediaType.TEXT_PLAIN)
			.body(exception.getMessage());
	}
}
