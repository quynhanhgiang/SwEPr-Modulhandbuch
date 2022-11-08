package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleFlatDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleGetDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModulePostDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/modules")
public class ModuleController {
	private final ModuleRepository repository;
	private final ModelMapper modelMapper = new ModelMapper();

	// TODO better representation of enums in DTOs

	// TODO better return type?
	@GetMapping("")
	List<?> allModules(@RequestParam(name="flat", required = false, defaultValue = "") String flat) {
		if (!flat.equals("true")) {
			List<ModuleEntity> result = this.repository.findAll();
			return result.stream().map((module) -> modelMapper.map(module, ModuleGetDTO.class)).toList();
		}

		List<ModuleEntity> result = this.repository.findAll();
		return result.stream().map((module) -> modelMapper.map(module, ModuleFlatDTO.class)).toList();
	}
	
	@GetMapping("/{id}")
	ModulePostDTO oneModule(@PathVariable Integer id) {
		ModuleEntity result = this.repository.findById(id)
			.orElseThrow(() -> new ModuleNotFoundException(id));
		return modelMapper.map(result, ModulePostDTO.class);
	}

	@PostMapping("")
	ModulePostDTO newModule(@RequestBody ModulePostDTO newModule) {
		if (newModule.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
		}

		ModuleEntity moduleEntity = modelMapper.map(newModule, ModuleEntity.class);
		ModuleEntity result = this.repository.save(moduleEntity);
		return modelMapper.map(result, ModulePostDTO.class);
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
