package de.hscoburg.modulhandbuchbackend;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleDTO;
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

	@GetMapping("")
	List<ModuleDTO> allModules() {
		List<ModuleEntity> result = this.repository.findAll();
		return result.stream().map((module) -> modelMapper.map(module, ModuleDTO.class)).toList();
	}
	
	@GetMapping("/{id}")
	ModuleDTO oneModule(@PathVariable Integer id) {
		ModuleEntity result = this.repository.findById(id)
			.orElseThrow(() -> new ModuleNotFoundException(id));
		return modelMapper.map(result, ModuleDTO.class);
	}

	@PostMapping("")
	ModuleDTO newModule(@RequestBody ModuleDTO newModule) {
		if (newModule.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Please do not send IDs");
		}

		ModuleEntity moduleEntity = modelMapper.map(newModule, ModuleEntity.class);
		ModuleEntity result = this.repository.save(moduleEntity);
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
