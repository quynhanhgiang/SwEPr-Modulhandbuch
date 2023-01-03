package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateModuleTypesInRequestException;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateSegmentsInRequestException;
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

		Function<ModuleManualEntity, SectionEntity> getFirstSection = moduleManualInner -> moduleManualInner.getFirstSection();
		BiConsumer<ModuleManualEntity, SectionEntity> setFirstSection = (moduleManualInner, section) -> moduleManualInner.setFirstSection(section);

		Consumer<Integer> duplicateSegmentsInRequestHandler = duplicateId -> {throw new DuplicateSegmentsInRequestException(duplicateId);};
		Consumer<Integer> segmentNotFoundHandler = notFoundId -> {throw new SegmentNotFoundException(notFoundId);};

		return this.moduleManualStructureService.updateStructure(segments, moduleManual, getFirstSection, setFirstSection, this.sectionRepository, SectionEntity.class, duplicateSegmentsInRequestHandler, segmentNotFoundHandler);
	}

	@PutMapping("/module-types")
	public List<StructureDTO> replaceModuleTypes(@RequestBody List<StructureDTO> moduleTypes, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		Function<ModuleManualEntity, TypeEntity> getFirstType = moduleManualInner -> moduleManualInner.getFirstType();
		BiConsumer<ModuleManualEntity, TypeEntity> setFirstType = (moduleManualInner, section) -> moduleManualInner.setFirstType(section);

		Consumer<Integer> duplicateModuleTypesInRequestHandler = duplicateId -> {throw new DuplicateModuleTypesInRequestException(duplicateId);};
		Consumer<Integer> moduleTypeNotFoundHandler = notFoundId -> {throw new ModuleTypeNotFoundException(notFoundId);};

		return this.moduleManualStructureService.updateStructure(moduleTypes, moduleManual, getFirstType, setFirstType, this.typeRepository, TypeEntity.class, duplicateModuleTypesInRequestHandler, moduleTypeNotFoundHandler);
	}
}
