package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleTypeDTO;
import de.hscoburg.modulhandbuchbackend.dto.SegmentDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
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
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("/segments")
	public List<SegmentDTO> allSegements(@PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
			
		List<SegmentDTO> segments = new LinkedList<>();

		SectionEntity currentSection = moduleManual.getSection();
		while (currentSection != null) {
			segments.add(this.modulhandbuchBackendMapper.map(currentSection, SegmentDTO.class));

			currentSection = currentSection.getNextSection();
		}

		return segments;
	}

	@GetMapping("/module-types")
	public List<ModuleTypeDTO> allModuleTypes(@PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
			
		List<ModuleTypeDTO> moduleTypes = new LinkedList<>();

		TypeEntity currentType = moduleManual.getType();
		while (currentType != null) {
			moduleTypes.add(this.modulhandbuchBackendMapper.map(currentType, ModuleTypeDTO.class));

			currentType = currentType.getNextType();
		}

		return moduleTypes;
	}

	@PutMapping("/segments")
	public List<SegmentDTO> replaceSegments(@RequestBody List<SegmentDTO> segments, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));

		// remove all null values in given list
		segments.removeIf(element -> (element == null));

		// check if all given ids are present or null
		for (Iterator<SegmentDTO> iterator = segments.iterator(); iterator.hasNext();) {
			SegmentDTO currentSegment = iterator.next();
			if (currentSegment.getId() == null) {
				continue;
			}

			if (this.sectionRepository.existsById(currentSegment.getId())) {
				continue;
			}

			// TODO own exception and advice
			throw new RuntimeException(String.format("Id %d for segment not found.", currentSegment.getId()));
		}

		// delete all segments associated with the given module manual
		SectionEntity currentSection = moduleManual.getFirstSection();
		moduleManual.setFirstSection(null);
		moduleManual = this.moduleManualRepository.save(moduleManual);
		while (currentSection != null) {
			this.sectionRepository.deleteById(currentSection.getId());
			currentSection = currentSection.getNextSection();
		}

		// if the passed list is empty no saving is required
		if (segments.size() == 0) {
			return segments;
		}

		// save all segments in reverse order to retrieve id of successor
		LinkedList<SegmentDTO> savedSegments = new LinkedList<>();
		SectionEntity successor = null;
		for (ListIterator<SegmentDTO> iterator = segments.listIterator(segments.size()); iterator.hasPrevious();) {
			SectionEntity section = this.modulhandbuchBackendMapper.map(iterator.previous(), SectionEntity.class);
			section.setNextSection = successor;
			section = this.sectionRepository.save(section);

			savedSegments.addFirst(this.modulhandbuchBackendMapper.map(section, SegmentDTO.class));

			successor = section;
		}

		moduleManual.setFirstSection(successor);

		return savedSegments;
	}

	@PutMapping("/module-types")
	public List<ModuleTypeDTO> replaceModuleTypes(@RequestBody List<ModuleTypeDTO> moduleTypes, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));

		// remove all null values in given list
		moduleTypes.removeIf(element -> (element == null));

		// check if all given ids are present or null
		for (Iterator<ModuleTypeDTO> iterator = moduleTypes.iterator(); iterator.hasNext();) {
			ModuleTypeDTO currentModuleType = iterator.next();
			if (currentModuleType.getId() == null) {
				continue;
			}

			if (this.typeRepository.existsById(currentModuleType.getId())) {
				continue;
			}

			// TODO own exception and advice
			throw new RuntimeException(String.format("Id %d for module type not found.", currentModuleType.getId()));
		}

		// delete all moduleTypes associated with the given module manual
		TypeEntity currentType = moduleManual.getFirstType();
		moduleManual.setFirstType(null);
		moduleManual = this.moduleManualRepository.save(moduleManual);
		while (currentType != null) {
			this.typeRepository.deleteById(currentType.getId());
			currentType = currentType.getNextType();
		}

		// if the passed list is empty no saving is required
		if (moduleTypes.size() == 0) {
			return moduleTypes;
		}

		// save all moduleTypes in reverse order to retrieve id of successor
		LinkedList<ModuleTypeDTO> savedModuleTypes = new LinkedList<>();
		TypeEntity successor = null;
		for (ListIterator<ModuleTypeDTO> iterator = moduleTypes.listIterator(moduleTypes.size()); iterator.hasPrevious();) {
			TypeEntity type = this.modulhandbuchBackendMapper.map(iterator.previous(), TypeEntity.class);
			type.setNextType = successor;
			type = this.typeRepository.save(type);

			savedModuleTypes.addFirst(this.modulhandbuchBackendMapper.map(type, ModuleTypeDTO.class));

			successor = type;
		}

		moduleManual.setFirstType(successor);

		return savedModuleTypes;
	}
}
