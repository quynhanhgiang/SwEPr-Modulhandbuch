package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleManualDTO;
import de.hscoburg.modulhandbuchbackend.mappers.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals")
public class ModuleManualController {
	private final ModuleManualRepository moduleManualRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper = new ModulhandbuchBackendMapper();
	
	@GetMapping("")
	public List<ModuleManualDTO> allModuleManuals() {
		List<ModuleManualEntity> result = this.moduleManualRepository.findAll();
		return result.stream().map(moduleManual -> modulhandbuchBackendMapper.map(moduleManual, ModuleManualDTO.class)).collect(Collectors.toList());
	}
}
