package de.hscoburg.modulhandbuchbackend.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hscoburg.modulhandbuchbackend.exceptions.ElementNotFoundException;

@ControllerAdvice
public class ElementNotFoundAdvice {
	
	@ExceptionHandler(ElementNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> elementNotFoundHandler(ElementNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.contentType(MediaType.TEXT_PLAIN)
			.body(exception.getMessage());
	}
}
