package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualEnumService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is a REST controller that handles requests regarding enums
 * associated with a module manual.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualEnumController {
	private final ModuleManualRepository moduleManualRepository;
	private final AdmissionRequirementRepository admissionRequirementRepository;
	private final ModuleManualEnumService moduleManualEnumService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	/**
	 * This method handles GET requests to the `/module-manuals/{id}/requirements`
	 * endpoint where id is a variable integer. It then uses the id to find the
	 * mapped data set in the database. If it finds one, it returns a set of strings
	 * which represent all the possible values of the enum `admissionRequirement`.
	 * If the module manual for the given id could not be found, it throws a
	 * {@link ModuleManualNotFoundException}.
	 * 
	 * @return A set of strings representing the enum `admissionRequirement`.
	 */
	@GetMapping("/requirements")
	public List<EnumDTO> allRequirements(@PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
				.orElseThrow(() -> new ModuleManualNotFoundException(id));

		List<AdmissionRequirementEntity> admissionRequirements = this.admissionRequirementRepository
				.findByModuleManual(moduleManual);

		return admissionRequirements.stream()
				.map(admissionRequirement -> this.modulhandbuchBackendMapper.map(admissionRequirement, EnumDTO.class))
				.collect(Collectors.toList());
	}

	/**
	 * This method handles PUT requests to the `/module-manuals/{id}/requirements`
	 * endpoint where id is a variable integer. It then uses the id to find the
	 * mapped data set in the database. If it finds one, it replaces the current
	 * values of the enum `admissionRequirement` with new values. It then returns a
	 * set of strings which represent the updated enum.
	 * 
	 * @param requirements A set with the new values for enum `admissionRequirement`
	 *                     to replace the existing enum with.
	 * @return A set of strings representing the updated enum
	 *         `admissionRequirement`.
	 */
	@PutMapping("/requirements")
	public List<EnumDTO> replaceRequirements(@RequestBody List<EnumDTO> requirements, @PathVariable Integer id) {
		return this.moduleManualEnumService.replaceRequirements(requirements, id);
	}
}
