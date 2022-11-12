package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityCycleStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityDurationStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityLanguageStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.VariationEntityCategoryStringConverter;
import de.hscoburg.modulhandbuchbackend.dto.ModuleDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleFlatDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/modules")
public class ModuleController {
	private final ModuleRepository moduleRepository;
	private final CollegeEmployeeRepository collegeEmployeeRepository;
	private final SpoRepository spoRepository;
	private final VariationRepository variationRepository;
	private final ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	public void init() {
		this.modelMapper.addConverter(new ModuleEntityCycleStringConverter());
		this.modelMapper.addConverter(new ModuleEntityDurationStringConverter());
		this.modelMapper.addConverter(new ModuleEntityLanguageStringConverter());
		this.modelMapper.addConverter(new VariationEntityCategoryStringConverter());
	}

	// TODO better return type?
	@GetMapping("")
	List<?> allModules(@RequestParam(name="flat", required = false, defaultValue = "") String flat) {
		if (!flat.equals("true")) {
			List<ModuleEntity> result = this.moduleRepository.findAll();
			return result.stream().map((module) -> modelMapper.map(module, ModuleDTO.class)).toList();
		}

		List<ModuleEntity> result = this.moduleRepository.findAll();
		return result.stream().map((module) -> modelMapper.map(module, ModuleFlatDTO.class)).toList();
	}
	
	@GetMapping("/{id}")
	ModuleDTO oneModule(@PathVariable Integer id) {
		ModuleEntity result = this.moduleRepository.findById(id)
			.orElseThrow(() -> new ModuleNotFoundException(id));
		return modelMapper.map(result, ModuleDTO.class);
	}

	@PostMapping("")
	ModuleDTO newModule(@RequestBody ModuleDTO newModule) {
		if (newModule.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
		}

		ModuleEntity moduleEntity = modelMapper.map(newModule, ModuleEntity.class);

		// TODO extract doubled contents in method (next three blocks)
		// extract only id from spo and replace other contents of spo with data from database
		if (moduleEntity.getVariations() != null) {
			moduleEntity.setVariations(
				moduleEntity.getVariations().stream()
					.filter(variation -> variation.getSpo() != null)
					.filter(variation -> variation.getSpo().getId() != null)
					.peek(variation -> variation.setSpo(
						// TODO own exception
						this.spoRepository.findById(variation.getSpo().getId()).orElseThrow(() -> new RuntimeException("Id for spo not found"))
					))
					.toList()
			);
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
					.toList()
			);
		}

		ModuleEntity result = this.moduleRepository.save(moduleEntity);
		return modelMapper.map(result, ModuleDTO.class);
	}

	// TODO
	// @PutMapping("/{id}")
	// ModuleEntity replaceModule(@RequestBody ModuleEntity newModule, @PathVariable Integer id) {
	// 	return this.repository.findById(id)
	// 	// .map(module -> {							// TODO
	// 	// 	module.setName(newEmployee.getName());
	// 	// 	module.setRole(newEmployee.getRole());
	// 	// 	return this.repository.save(module);
	// 	// })
	// 	.map(module -> {
	// 		return this.repository.save(module);
	// 	})
	// 	.orElseGet(() -> {
	// 		newModule.setId(id);
	// 		return this.repository.save(newModule);
	// 	});
	// }

	// TODO
	// @DeleteMapping("/{id}")
	// void deleteModule(@PathVariable Integer id) {
	// 	this.repository.deleteById(id);
	// }
}
