package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.ElementRequiredException;

@ControllerAdvice
public class ElementRequiredAdvice {
	
	@ExceptionHandler(ElementRequiredException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<String> elementRequiredHandler(ElementRequiredException exception) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
			.contentType(MediaType.TEXT_PLAIN)
			.body(exception.getMessage());
	}
}
