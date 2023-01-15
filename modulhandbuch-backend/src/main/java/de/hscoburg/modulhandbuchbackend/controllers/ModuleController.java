package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleFlatDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleFullDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.services.VariationService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/modules")
public class ModuleController {
	private final ModuleRepository moduleRepository;
	private final CollegeEmployeeRepository collegeEmployeeRepository;
	private final ModuleManualRepository moduleManualRepository;
	private final VariationRepository variationRepository;
	private final ModuleService moduleService;
	private final VariationService variationService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("")
	public List<ModuleDTO> allModules(@RequestParam(name = "flat", required = false, defaultValue = "") String flat,
		@RequestParam(name = "not-in-manual", required = false, defaultValue = "") String moduleManualToIgnoreString) {
		if (!flat.equals("true")) {
			List<ModuleEntity> result = this.moduleRepository.findAll();
			return result.stream()
				.map(module -> this.modulhandbuchBackendMapper.map(module, ModuleFullDTO.class))
				.collect(Collectors.toList());
		}

		try {
			// on NumberFormatException moduleManualToIgnore was not set (or not a number) so return all modules
			Integer moduleManualToIgnoreId = Integer.valueOf(moduleManualToIgnoreString);
			
			List<ModuleEntity> allModules = this.moduleRepository.findAll();
			List<ModuleEntity> modulesNotInModuleManual = new LinkedList<>();
			for (ModuleEntity module : allModules) {
				List<VariationEntity> variations = this.variationRepository.findByModule(module);
				long countOfVariationWithModuleManualToIgnore = variations.stream()
					.filter(variation -> variation.getModuleManual().getId() == moduleManualToIgnoreId)
					.count();

				if (countOfVariationWithModuleManualToIgnore == 0) {
					modulesNotInModuleManual.add(module);
				}
			}

			return modulesNotInModuleManual.stream()
				.map(module -> this.modulhandbuchBackendMapper.map(module, ModuleFlatDTO.class))
				.collect(Collectors.toList());

		} catch (NumberFormatException ignored) {}

		List<ModuleEntity> result = this.moduleRepository.findAll();
		return result.stream()
			.map(module -> this.modulhandbuchBackendMapper.map(module, ModuleFlatDTO.class))
			.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	ModuleFullDTO oneModule(@PathVariable Integer id) {
		ModuleEntity result = this.moduleRepository.findById(id)
			.orElseThrow(() -> new ModuleNotFoundException(id));
		return modulhandbuchBackendMapper.map(result, ModuleFullDTO.class);
	}

	@PostMapping("")
	ModuleFullDTO newModule(@RequestBody ModuleFullDTO newModule) {
		if (newModule.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
		}

		return this.moduleService.saveModule(newModule);
	}

	@PutMapping("/{id}")
	ModuleFullDTO replaceModule(@RequestBody ModuleFullDTO updatedModule, @PathVariable Integer id) {
		updatedModule.setId(id);

		ModuleEntity moduleToUpdate = this.moduleRepository.findById(id).orElseThrow(() -> {
			// TODO own exception and advice
			throw new RuntimeException(String.format("ID %d is not mapped for any module. For creating a new module please use a POST request.", id));
		});

		// delete current variations
		List<VariationEntity> currentVariations = moduleToUpdate.getVariations();
		currentVariations.stream()
			.forEach(variation -> this.variationRepository.delete(variation));
		currentVariations = null;
		moduleToUpdate.setVariations(currentVariations);

		return this.moduleService.saveModule(updatedModule);
	}
}
