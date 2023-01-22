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

/**
 * This class is a REST controller that handles requests regarding enums.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("")
public class EnumController {
	private final EnumService enumService;

	/**
	 * This method handles GET requests to the `/cycles` endpoint and returns a set of strings which represent all the possible values of the enum `cycle`.
	 * 
	 * @return A set of strings representing the enum `cycle`.
	 */
	@GetMapping("/cycles")
	public Set<String> allCycles() {
		return this.enumService.allCycles();
	}

	/**
	 * This method handles GET requests to the `/degrees` endpoint and returns a set of strings which represent all the possible values of the enum `degree`.
	 * 
	 * @return A set of strings representing the enum `degree`.
	 */
	@GetMapping("/degrees")
	public Set<String> allDegrees() {
		return this.enumService.allDegrees();
	}

		/**
	 * This method handles GET requests to the `/durations` endpoint and returns a set of strings which represent all the possible values of the enum `duration`.
	 * 
	 * @return A set of strings representing the enum `duration`.
	 */
	@GetMapping("/durations")
	public Set<String> allDurations() {
		return this.enumService.allDurations();
	}

		/**
	 * This method handles GET requests to the `/genders` endpoint and returns a set of strings which represent all the possible values of the enum `gender`.
	 * 
	 * @return A set of strings representing the enum `gender`.
	 */
	@GetMapping("/genders")
	public Set<String> allGenders() {
		return this.enumService.allGenders();
	}

	/**
	 * This method handles GET requests to the `/languages` endpoint and returns a set of strings which represent all the possible values of the enum `language`.
	 * 
	 * @return A set of strings representing the enum `language`.
	 */
	@GetMapping("/languages")
	public Set<String> allLanguages() {
		return this.enumService.allLanguages();
	}
	
	/**
	 * This method handles GET requests to the `/maternity-protections` endpoint and returns a set of strings which represent all the possible values of the enum `maternityProtection`.
	 * 
	 * @return A set of strings representing the enum `maternityProtection`.
	 */
	@GetMapping("/maternity-protections")
	public Set<String> allMaternityProtections() {
		return this.enumService.allMaternityProtections();
	}

	/**
	 * This method handles PUT requests to the `/cycles` endpoint and replaces the current values of the enum `cycle` with new values. It then returns a set of strings which represent the updated enum.
	 * 
	 * @param newCycles A set with the new values for enum `cycle` to replace the existing enum with.
	 * @return A set of strings representing the updated enum `cycle`.
	 */
	@PutMapping("/cycles")
	public Set<String> replaceCycles(@RequestBody Set<String> newCycles) {
		return this.enumService.replaceCycles(newCycles);
	}

	/**
	 * This method handles PUT requests to the `/degrees` endpoint and replaces the current values of the enum `degree` with new values. It then returns a set of strings which represent the updated enum.
	 * 
	 * @param newDegrees A set with the new values for enum `degree` to replace the existing enum with.
	 * @return A set of strings representing the updated enum `degree`.
	 */
	@PutMapping("/degrees")
	public Set<String> replaceDegrees(@RequestBody Set<String> newDegrees) {
		return this.enumService.replaceDegrees(newDegrees);
	}

	/**
	 * This method handles PUT requests to the `/durations` endpoint and replaces the current values of the enum `duration` with new values. It then returns a set of strings which represent the updated enum.
	 * 
	 * @param newDurations A set with the new values for enum `duration` to replace the existing enum with.
	 * @return A set of strings representing the updated enum `duration`.
	 */
	@PutMapping("/durations")
	public Set<String> replaceDuration(@RequestBody Set<String> newDurations) {
		return this.enumService.replaceDuration(newDurations);
	}

	/**
	 * This method handles PUT requests to the `/genders` endpoint and replaces the current values of the enum `gender` with new values. It then returns a set of strings which represent the updated enum.
	 * 
	 * @param newGenders A set with the new values for enum `gender` to replace the existing enum with.
	 * @return A set of strings representing the updated enum `gender`.
	 */
	@PutMapping("/genders")
	public Set<String> replaceGenders(@RequestBody Set<String> newGenders) {
		return this.enumService.replaceGenders(newGenders);
	}

	/**
	 * This method handles PUT requests to the `/languages` endpoint and replaces the current values of the enum `language` with new values. It then returns a set of strings which represent the updated enum.
	 * 
	 * @param newLanguages A set with the new values for enum `language` to replace the existing enum with.
	 * @return A set of strings representing the updated enum `language`.
	 */
	@PutMapping("/languages")
	public Set<String> replaceLanguages(@RequestBody Set<String> newLanguages) {
		return this.enumService.replaceLanguages(newLanguages);
	}

		/**
	 * This method handles PUT requests to the `/maternity-protections` endpoint and replaces the current values of the enum `maternityProtection` with new values. It then returns a set of strings which represent the updated enum.
	 * 
	 * @param newMaternityProtections A set with the new values for enum `maternityProtection` to replace the existing enum with.
	 * @return A set of strings representing the updated enum `maternityProtection`.
	 */
	@PutMapping("/maternity-protections")
	public Set<String> replaceMaternityProtections(@RequestBody Set<String> newMaternityProtections) {
		return this.enumService.replaceMaternityProtections(newMaternityProtections);
	}
}
