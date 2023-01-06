package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.DegreeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CycleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.DegreeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.DurationRepository;
import de.hscoburg.modulhandbuchbackend.repositories.GenderRepository;
import de.hscoburg.modulhandbuchbackend.repositories.LanguageRepository;
import de.hscoburg.modulhandbuchbackend.repositories.MaternityProtectionRepository;
import de.hscoburg.modulhandbuchbackend.services.EnumService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("")
public class EnumController {
	private final CycleRepository cycleRepository;
	private final DegreeRepository degreeRepository;
	private final DurationRepository durationRepository;
	private final GenderRepository genderRepository;
	private final LanguageRepository languageRepository;
	private final MaternityProtectionRepository maternityProtectionRepository;
	private final EnumService enumService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("/cycles")
	public List<String> allCycles() {
		return this.enumService.getAllAsStringList(this.cycleRepository);
	}

	@GetMapping("/degrees")
	public List<String> allDegrees() {
		return this.enumService.getAllAsStringList(this.degreeRepository);
	}

	@GetMapping("/durations")
	public List<String> allDurations() {
		return this.enumService.getAllAsStringList(this.durationRepository);
	}

	@GetMapping("/genders")
	public List<String> allGenders() {
		return this.enumService.getAllAsStringList(this.genderRepository);
	}

	@GetMapping("/languages")
	public List<String> allLanguages() {
		return this.enumService.getAllAsStringList(this.languageRepository);
	}
	
	@GetMapping("/maternity-protections")
	public List<String> allMaternityProtections() {
		return this.enumService.getAllAsStringList(this.maternityProtectionRepository);
	}

	@PutMapping("/cycles")
	public List<String> replaceCycles(@RequestBody List<String> newCycles) {
		return this.enumService.replaceEnum(newCycles, CycleEntity.class, this.cycleRepository);
	}

	@PutMapping("/degrees")
	public List<String> replaceDegrees(@RequestBody List<String> newDegrees) {
		return this.enumService.replaceEnum(newDegrees, DegreeEntity.class, this.degreeRepository);
	}

	@PutMapping("/durations")
	public List<String> replaceDuration(@RequestBody List<String> newDurations) {
		return this.enumService.replaceEnum(newDurations, DurationEntity.class, this.durationRepository);
	}

	@PutMapping("/genders")
	public List<String> replaceGenders(@RequestBody List<String> newGenders) {
		return this.enumService.replaceEnum(newGenders, GenderEntity.class, this.genderRepository);
	}

	@PutMapping("/languages")
	public List<String> replaceLanguages(@RequestBody List<String> newLanguages) {
		return this.enumService.replaceEnum(newLanguages, LanguageEntity.class, this.languageRepository);
	}

	@PutMapping("/maternity-protections")
	public List<String> replaceMaternityProtections(@RequestBody List<String> newMaternityProtections) {
		return this.enumService.replaceEnum(newMaternityProtections, MaternityProtectionEntity.class, this.maternityProtectionRepository);
	}
}
