package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.services.EnumService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("")
public class EnumController {
	private final EnumService enumService;
	
	@GetMapping("/cycles")
	public Set<String> allCycles() {
		return this.enumService.allCycles();
	}

	@GetMapping("/degrees")
	public Set<String> allDegrees() {
		return this.enumService.allDegrees();
	}

	@GetMapping("/durations")
	public Set<String> allDurations() {
		return this.enumService.allDurations();
	}

	@GetMapping("/genders")
	public Set<String> allGenders() {
		return this.enumService.allGenders();
	}

	@GetMapping("/languages")
	public Set<String> allLanguages() {
		return this.enumService.allLanguages();
	}
	
	@GetMapping("/maternity-protections")
	public Set<String> allMaternityProtections() {
		return this.enumService.allMaternityProtections();
	}

	@PutMapping("/cycles")
	public Set<String> replaceCycles(@RequestBody Set<String> newCycles) {
		return this.enumService.replaceCycles(newCycles);
	}

	@PutMapping("/degrees")
	public Set<String> replaceDegrees(@RequestBody Set<String> newDegrees) {
		return this.enumService.replaceDegrees(newDegrees);
	}

	@PutMapping("/durations")
	public Set<String> replaceDuration(@RequestBody Set<String> newDurations) {
		return this.enumService.replaceDuration(newDurations);
	}

	@PutMapping("/genders")
	public Set<String> replaceGenders(@RequestBody Set<String> newGenders) {
		return this.enumService.replaceGenders(newGenders);
	}

	@PutMapping("/languages")
	public Set<String> replaceLanguages(@RequestBody Set<String> newLanguages) {
		return this.enumService.replaceLanguages(newLanguages);
	}

	@PutMapping("/maternity-protections")
	public Set<String> replaceMaternityProtections(@RequestBody Set<String> newMaternityProtections) {
		return this.enumService.replaceMaternityProtections(newMaternityProtections);
	}
}
