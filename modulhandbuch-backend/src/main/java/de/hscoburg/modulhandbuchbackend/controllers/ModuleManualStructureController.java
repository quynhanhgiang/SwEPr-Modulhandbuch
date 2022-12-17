package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleTypeDTO;
import de.hscoburg.modulhandbuchbackend.dto.SegmentDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualStructureController {
	private final ModuleManualRepository moduleManualRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("/segments")
	public List<SegmentDTO> allSegements(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
			
		List<SegmentDTO> segments = new LinkedList<>();

		SectionEntity currentSection = result.getSection();
		while (currentSection != null) {
			segments.add(this.modulhandbuchBackendMapper.map(currentSection, SegmentDTO.class));

			currentSection = currentSection.getNextSection();
		}

		return segments;
	}

	@GetMapping("/module-types")
	public List<ModuleTypeDTO> allModuleTypes(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
			
		List<ModuleTypeDTO> moduleTypes = new LinkedList<>();

		TypeEntity currentType = result.getType();
		while (currentType != null) {
			moduleTypes.add(this.modulhandbuchBackendMapper.map(currentType, ModuleTypeDTO.class));

			currentType = currentType.getNextType();
		}

		return moduleTypes;
	}
}
