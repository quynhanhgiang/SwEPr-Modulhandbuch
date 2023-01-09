package de.hscoburg.modulhandbuchbackend.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateModuleTypesInRequestException;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateSegmentsInRequestException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleTypeNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.SegmentNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.StructureEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.StructureRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import lombok.Data;

@Data
@Service
public class ModuleManualStructureService {
	private final ModuleManualRepository moduleManualRepository;
	private final SectionRepository sectionRepository;
	private final TypeRepository typeRepository;
	private final VariationRepository variationRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;
	
	public <T extends StructureEntity<T>> List<StructureDTO> getStructure(Integer id, Function<ModuleManualEntity, T> getFirstEntity) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));
			
		List<StructureDTO> structure = new LinkedList<>();

		T currentEntity = getFirstEntity.apply(moduleManual);
		while (currentEntity != null) {
			structure.add(this.modulhandbuchBackendMapper.map(currentEntity, StructureDTO.class));

			currentEntity = currentEntity.getNext();
		}

		return structure;
	}

	private <T extends StructureEntity<T>> void validateIds(List<StructureDTO> structure, ModuleManualEntity moduleManual, StructureRepository<T> repository, Consumer<Integer> duplicateElementsInRequestHandler, Consumer<Integer> elementNotFoundHandler) {
		Set<Integer> ids = new TreeSet<>();
		Iterator<StructureDTO> iterator = structure.iterator();
		while (iterator.hasNext()) {
			StructureDTO currentElement = iterator.next();
			Integer currentElementId = currentElement.getId();
			if (currentElementId == null) {
				continue;
			}

			if (!ids.add(currentElementId)) {
				duplicateElementsInRequestHandler.accept(currentElementId);
			}

			if (repository.existsByIdAndModuleManual(currentElementId, moduleManual)) {
				continue;
			}

			elementNotFoundHandler.accept(currentElementId);
		}
	}

	private <T extends StructureEntity<T>> Map<VariationEntity, T> deleteCurrentStructureAndReturnPreviousMappingsInVariations(T firstEntity, StructureRepository<T> structureRepository, BiConsumer<VariationEntity, T> variationSetStructure, Function<T, List<VariationEntity>> variationRepositoryFindByStructure) {
		Map<VariationEntity, T> previousMappings = new HashMap<>();
		T nextEntity = firstEntity;
		while (nextEntity != null) {
			T currentEntity = nextEntity;

			List<VariationEntity> mappedVariations = variationRepositoryFindByStructure.apply(currentEntity);
			mappedVariations.stream()
				.peek(variation -> previousMappings.put(variation, currentEntity))
				.peek(variation -> variationSetStructure.accept(variation, null))
				.forEach(variation -> this.variationRepository.save(variation));

			structureRepository.delete(currentEntity);
			nextEntity = currentEntity.getNext();
		}

		return previousMappings;
	}

	private <T extends StructureEntity<T>> List<StructureDTO> saveStructure(List<StructureDTO> structure, ModuleManualEntity moduleManual, StructureRepository<T> structureRepository, Class<T> entityClass, BiConsumer<ModuleManualEntity, T> setFirstEntity) {
		// save structure in reverse order to retrieve id of successor easily
		ListIterator<StructureDTO> iterator = structure.listIterator(structure.size());
		LinkedList<StructureDTO> savedStructure = new LinkedList<>();
		T successor = null;
		while (iterator.hasPrevious()) {
			T currentEntity = this.modulhandbuchBackendMapper.map(iterator.previous(), entityClass);
			currentEntity.setModuleManual(moduleManual);
			currentEntity.setNext(successor);
			currentEntity = structureRepository.save(currentEntity);

			savedStructure.addFirst(this.modulhandbuchBackendMapper.map(currentEntity, StructureDTO.class));

			successor = currentEntity;
		}

		setFirstEntity.accept(moduleManual, successor);
		this.moduleManualRepository.save(moduleManual);

		return savedStructure;
	}

	private <T extends StructureEntity<T>> void restoreMappingsInVariations(Map<VariationEntity, T> previousMappings, StructureRepository<T> structureRepository, BiConsumer<VariationEntity, T> variationSetStructure) {
		previousMappings.entrySet().stream()
			.filter(entry -> structureRepository.findById(entry.getValue().getId()).isPresent())
			.peek(entry -> variationSetStructure.accept(entry.getKey(), entry.getValue()))
			.forEach(entry -> this.variationRepository.save(entry.getKey()));
	}

	private <T extends StructureEntity<T>> List<StructureDTO> updateStructure(List<StructureDTO> structure, Integer moduleManualId, Function<ModuleManualEntity, T> moduleManualGetFirstStructureElement, BiConsumer<ModuleManualEntity, T> moduleManualSetFirstStructureElement, StructureRepository<T> structureRepository, BiConsumer<VariationEntity, T> variationSetStructure, Function<T, List<VariationEntity>> variationRepositoryFindByStructure, Class<T> structureEntityClass, Consumer<Integer> duplicateElementsInRequestHandler, Consumer<Integer> elementNotFoundHandler) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(moduleManualId)
			.orElseThrow(() -> new ModuleManualNotFoundException(moduleManualId));

		// remove all null values in given list
		structure.removeIf(element -> (element == null));

		// check if all given ids are present or null and there are no duplicates of ids in request
		this.validateIds(structure, moduleManual, structureRepository, duplicateElementsInRequestHandler, elementNotFoundHandler);

		// delete all elements of structure associated with the given module manual and get a map with the previous mappings between variations and structure elements
		Map<VariationEntity, T> previousMappings = this.deleteCurrentStructureAndReturnPreviousMappingsInVariations(moduleManualGetFirstStructureElement.apply(moduleManual), structureRepository, variationSetStructure, variationRepositoryFindByStructure);
		moduleManualSetFirstStructureElement.accept(moduleManual, null);
		moduleManual = this.moduleManualRepository.save(moduleManual);

		// if the passed list is empty no saving is required
		if (structure.size() == 0) {
			return structure;
		}

		// save all elements of structure
		List<StructureDTO> savedStructure = this.saveStructure(structure, moduleManual, structureRepository, structureEntityClass, moduleManualSetFirstStructureElement);

		// restore mappings between variations and structure elements
		this.restoreMappingsInVariations(previousMappings, structureRepository, variationSetStructure);

		return savedStructure;
	}

	public List<StructureDTO> replaceSegments(List<StructureDTO> segments, Integer moduleManualId) {
		Function<ModuleManualEntity, SectionEntity> moduleManualGetFirstSection = moduleManual -> moduleManual.getFirstSection();
		BiConsumer<ModuleManualEntity, SectionEntity> moduleManualSetFirstSection = (moduleManual, section) -> moduleManual.setFirstSection(section);

		BiConsumer<VariationEntity, SectionEntity> variationSetSection = (variation, section) -> variation.setSegment(section);

		Function<SectionEntity, List<VariationEntity>> variationRepositoryFindBySection = section -> this.variationRepository.findBySection(section);

		Consumer<Integer> duplicateSegmentsInRequestHandler = duplicateId -> {throw new DuplicateSegmentsInRequestException(duplicateId);};
		Consumer<Integer> segmentNotFoundHandler = notFoundId -> {throw new SegmentNotFoundException(notFoundId);};

		return this.updateStructure(segments, moduleManualId, moduleManualGetFirstSection, moduleManualSetFirstSection, this.sectionRepository, variationSetSection, variationRepositoryFindBySection, SectionEntity.class, duplicateSegmentsInRequestHandler, segmentNotFoundHandler);
	}

	public List<StructureDTO> replaceModuleTypes(@RequestBody List<StructureDTO> moduleTypes, @PathVariable Integer moduleManualId) {
		Function<ModuleManualEntity, TypeEntity> getFirstType = moduleManual -> moduleManual.getFirstType();
		BiConsumer<ModuleManualEntity, TypeEntity> setFirstType = (moduleManual, type) -> moduleManual.setFirstType(type);

		BiConsumer<VariationEntity, TypeEntity> variationSetType = (variation, type) -> variation.setModuleType(type);

		Function<TypeEntity, List<VariationEntity>> variationRepositoryFindByType = type -> this.variationRepository.findByType(type);

		Consumer<Integer> duplicateModuleTypesInRequestHandler = duplicateId -> {throw new DuplicateModuleTypesInRequestException(duplicateId);};
		Consumer<Integer> moduleTypeNotFoundHandler = notFoundId -> {throw new ModuleTypeNotFoundException(notFoundId);};

		return this.updateStructure(moduleTypes, moduleManualId, getFirstType, setFirstType, this.typeRepository, variationSetType, variationRepositoryFindByType, TypeEntity.class, duplicateModuleTypesInRequestHandler, moduleTypeNotFoundHandler);
	}
}
