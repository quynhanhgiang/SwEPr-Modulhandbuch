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
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualEnumController {
	private final ModuleManualRepository moduleManualRepository;
	private final AdmissionRequirementRepository admissionRequirementRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("/requirements")
	public List<EnumDTO> allRequirements(@PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		List<AdmissionRequirementEntity> admissionRequirements = this.admissionRequirementRepository.findByModuleManual(moduleManual);

		return admissionRequirements.stream()
			.map(admissionRequirement -> this.modulhandbuchBackendMapper.map(admissionRequirements, EnumDTO.class))
			.collect(Collectors.toList());
	}
	
	@PutMapping("/requirements")
	public List<EnumDTO> replaceRequirements(@RequestBody List<EnumDTO> requirements, @PathVariable Integer id) {
		// remove all null values in given list
		requirements.removeIf(element -> (element == null));

		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		this.admissionRequirementRepository.deleteByModuleManual(moduleManual);

		// save all requirements and update values for return
		return requirements.stream()
			.map(requirement -> {
				AdmissionRequirementEntity admissionRequirement = this.modulhandbuchBackendMapper.map(requirement, AdmissionRequirementEntity.class);
				admissionRequirement.setModuleManual(moduleManual);
				admissionRequirement = this.admissionRequirementRepository.save(admissionRequirement);
				return this.modulhandbuchBackendMapper.map(admissionRequirement, EnumDTO.class);
			})
			.collect(Collectors.toList());
	}
}
