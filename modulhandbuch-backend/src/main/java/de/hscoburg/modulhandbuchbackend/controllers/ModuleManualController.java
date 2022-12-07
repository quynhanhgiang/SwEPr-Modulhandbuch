package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleManualDTO;
import de.hscoburg.modulhandbuchbackend.mappers.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals")
public class ModuleManualController {
	private final ModuleManualRepository moduleManualRepository;
	private final SpoRepository spoRepository;
	
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper = new ModulhandbuchBackendMapper();
	
	@GetMapping("")
	public List<ModuleManualDTO> allModuleManuals() {
		List<ModuleManualEntity> result = this.moduleManualRepository.findAll();
		return result.stream().map(moduleManual -> modulhandbuchBackendMapper.map(moduleManual, ModuleManualDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	ModuleManualDTO oneModuleManual(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	@PostMapping("")
	ModuleManualDTO newModuleManual(@RequestBody ModuleManualDTO newModuleManual) {
		if (newModuleManual.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
		}

		ModuleManualEntity moduleManualEntity = modulhandbuchBackendMapper.map(newModuleManual, ModuleManualEntity.class);

		if (moduleManualEntity.getSpo() != null) {
			if (moduleManualEntity.getSpo().getId() == null) {
				// if id of spo is null a new entry for it should be created
				SpoEntity result = this.spoRepository.save(moduleManualEntity.getSpo());
				moduleManualEntity.setSpo(result);
			} else {
				// extract only id from spo and replace other contents of spo with data from database
				moduleManualEntity.setSpo(
					// TODO own exception
					this.spoRepository.findById(moduleManualEntity.getSpo().getId()).orElseThrow(() -> new RuntimeException("Id for spo not found"))
				);
			}
		}

		ModuleManualEntity result = this.moduleManualRepository.save(moduleManualEntity);
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}
}
