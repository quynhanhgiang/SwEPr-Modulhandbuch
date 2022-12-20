package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleTypeNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.SegmentNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualStructureService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualStructureController {
	private final ModuleManualRepository moduleManualRepository;
	private final SectionRepository sectionRepository;
	private final TypeRepository typeRepository;
	private final ModuleManualStructureService moduleManualStructureService;

	@GetMapping("/segments")
	public List<StructureDTO> allSegements(@PathVariable Integer id) {
		return this.moduleManualStructureService.getStructure(id, moduleManual -> moduleManual.getFirstSection());
	}

	@GetMapping("/module-types")
	public List<StructureDTO> allModuleTypes(@PathVariable Integer id) {
		return this.moduleManualStructureService.getStructure(id, moduleManual -> moduleManual.getFirstType());
	}

	@PutMapping("/segments")
	public List<StructureDTO> replaceSegments(@RequestBody List<StructureDTO> segments, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		// remove all null values in given list
		segments.removeIf(element -> (element == null));

		// check if all given ids are present or null
		this.moduleManualStructureService.validateIds(segments.iterator(), this.sectionRepository, notFoundId -> {throw new SegmentNotFoundException(notFoundId);});

		// delete all segments associated with the given module manual
		this.moduleManualStructureService.deleteCurrentStructure(moduleManual.getFirstSection(), this.sectionRepository);
		moduleManual.setFirstSection(null);
		moduleManual = this.moduleManualRepository.save(moduleManual);

		// if the passed list is empty no saving is required
		if (segments.size() == 0) {
			return segments;
		}

		// save all segments
		return this.moduleManualStructureService.saveStructure(segments, moduleManual, sectionRepository, moduleManualRepository, SectionEntity.class, (moduleManualInner, section) -> moduleManualInner.setFirstSection((SectionEntity) section));
	}

	@PutMapping("/module-types")
	public List<StructureDTO> replaceModuleTypes(@RequestBody List<StructureDTO> moduleTypes, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		// remove all null values in given list
		moduleTypes.removeIf(element -> (element == null));

		// check if all given ids are present or null
		this.moduleManualStructureService.validateIds(moduleTypes.iterator(), this.typeRepository, notFoundId -> {throw new ModuleTypeNotFoundException(notFoundId);});

		// delete all module types associated with the given module manual
		this.moduleManualStructureService.deleteCurrentStructure(moduleManual.getFirstType(), this.typeRepository);
		moduleManual.setFirstType(null);
		moduleManual = this.moduleManualRepository.save(moduleManual);

		// if the passed list is empty no saving is required
		if (moduleTypes.size() == 0) {
			return moduleTypes;
		}

		// save all module types
		return this.moduleManualStructureService.saveStructure(moduleTypes, moduleManual, typeRepository, moduleManualRepository, TypeEntity.class, (moduleManualInner, type) -> moduleManualInner.setFirstType((TypeEntity) type));
	}
}
