package de.hscoburg.modulhandbuchbackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("")
public class DebugController {
	
	@GetMapping("")
	String test() {
		log.debug("Working");
		return "Working";
	}
}
