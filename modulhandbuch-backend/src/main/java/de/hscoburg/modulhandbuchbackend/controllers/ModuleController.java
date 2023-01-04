package de.hscoburg.modulhandbuchbackend.controllers;

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
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
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
	private final VariationService variationService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("")
	public List<ModuleDTO> allModules(@RequestParam(name = "flat", required = false, defaultValue = "") String flat,
		@RequestParam(name = "not-in-manual", required = false, defaultValue = "") String moduleManualToIgnoreString) {
		if (!flat.equals("true")) {
			List<ModuleEntity> result = this.moduleRepository.findAll();
			return result.stream().map((module) -> modulhandbuchBackendMapper.map(module, ModuleFullDTO.class)).collect(Collectors.toList());
		}

		try {
			Integer moduleManualToIgnoreId = Integer.valueOf(moduleManualToIgnoreString);
			ModuleManualEntity moduleManualToIgnore = this.moduleManualRepository.findById(moduleManualToIgnoreId)
				.orElseThrow(() -> new ModuleManualNotFoundException(moduleManualToIgnoreId));
			
			List<VariationEntity> result = this.variationRepository.findByModuleManualNot(moduleManualToIgnore);
			return result.stream()
				.map(variation -> variation.getModule())
				.map(module -> modulhandbuchBackendMapper.map(module, ModuleFlatDTO.class)).collect(Collectors.toList());

		} catch (NumberFormatException | ModuleManualNotFoundException ignored) {}

		List<ModuleEntity> result = this.moduleRepository.findAll();
		return result.stream().map((module) -> modulhandbuchBackendMapper.map(module, ModuleFlatDTO.class)).collect(Collectors.toList());
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

		ModuleEntity moduleEntity = modulhandbuchBackendMapper.map(newModule, ModuleEntity.class);

		// TODO extract doubled contents in method (next three blocks)
		// extract only id from module manual and replace other contents of module manual with data from database
		if (moduleEntity.getVariations() != null) {
			List<VariationEntity> newVariations = moduleEntity.getVariations().stream()
				.map(variationEntity -> this.variationService.cleanEntity(variationEntity))
				.filter(variationEntity -> variationEntity != null)
				.collect(Collectors.toList());

			moduleEntity.setVariations(newVariations);
		}

		// extract only id from moduleOwner and replace other contents of moduleOwner with data from database
		if ((moduleEntity.getModuleOwner() != null) && (moduleEntity.getModuleOwner().getId() != null)) {
			moduleEntity.setModuleOwner(
				// TODO own exception
				this.collegeEmployeeRepository.findById(moduleEntity.getModuleOwner().getId()).orElseThrow(() -> new RuntimeException("Id for college employee not found"))
			);
		}

		// extract only id from profs and replace other contents of profs with data from database
		if (moduleEntity.getProfs() != null) {
			moduleEntity.setProfs(
				moduleEntity.getProfs().stream()
					.filter(prof -> prof.getId() != null)
					// TODO own Exception
					.map(prof -> this.collegeEmployeeRepository.findById(prof.getId()).orElseThrow(() -> new RuntimeException("Id not found")))
					.collect(Collectors.toList())
			);
		}

		ModuleEntity result = this.moduleRepository.save(moduleEntity);
		return modulhandbuchBackendMapper.map(result, ModuleFullDTO.class);
	}

	@PutMapping("/{id}")
	ModuleFullDTO replaceModule(@RequestBody ModuleFullDTO updatedModule, @PathVariable Integer id) {
		this.moduleRepository.findById(id).orElseThrow(() -> {
			// TODO own exception and advice
			throw new RuntimeException(String.format("ID %d is not mapped for any module. For creating a new module please use a POST request.", id));
		});

		updatedModule.setId(id);
		ModuleEntity moduleEntity = modulhandbuchBackendMapper.map(updatedModule, ModuleEntity.class);

		// TODO extract doubled contents in method (next three blocks)
		// extract only id from module manual and replace other contents of module manual with data from database
		if (moduleEntity.getVariations() != null) {
			List<VariationEntity> newVariations = moduleEntity.getVariations().stream()
				.filter(variation -> variation.getModuleManual() != null)
				.filter(variation -> variation.getModuleManual().getId() != null)
				.peek(variation -> variation.setModuleManual(
					// TODO own exception
					this.moduleManualRepository.findById(variation.getModuleManual().getId()).orElseThrow(() -> new RuntimeException("Id for spo not found"))
				))
				.collect(Collectors.toList());

			moduleEntity.setVariations(newVariations);
		}

		// extract only id from moduleOwner and replace other contents of moduleOwner with data from database
		if ((moduleEntity.getModuleOwner() != null) && (moduleEntity.getModuleOwner().getId() != null)) {
			moduleEntity.setModuleOwner(
				// TODO own exception
				this.collegeEmployeeRepository.findById(moduleEntity.getModuleOwner().getId()).orElseThrow(() -> new RuntimeException("Id for college employee not found"))
			);
		}

		// extract only id from profs and replace other contents of profs with data from database
		if (moduleEntity.getProfs() != null) {
			moduleEntity.setProfs(
				moduleEntity.getProfs().stream()
					.filter(prof -> prof.getId() != null)
					// TODO own Exception
					.map(prof -> this.collegeEmployeeRepository.findById(prof.getId()).orElseThrow(() -> new RuntimeException("Id not found")))
					.collect(Collectors.toList())
			);
		}

		ModuleEntity result = this.moduleRepository.save(moduleEntity);
		return modulhandbuchBackendMapper.map(result, ModuleFullDTO.class);
	}
}
