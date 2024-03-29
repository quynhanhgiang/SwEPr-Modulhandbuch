package de.hscoburg.modulhandbuchbackend.services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	private <T extends StructureEntity<T>> List<StructureDTO> updateStructure(List<StructureDTO> structureDTO, ModuleManualEntity moduleManualEntity, BiConsumer<ModuleManualEntity, T> moduleManualSetFirstEntity, StructureRepository<T> structureRepository, BiConsumer<VariationEntity, T> variationSetStructure, Function<T, List<VariationEntity>> variationRepositoryFindByStructure, Class<T> structureEntityClass) {
		List<T> structureElements = structureRepository.findByModuleManual(moduleManualEntity);
		// set first element to null to avoid conflicts at deletion
		moduleManualSetFirstEntity.accept(moduleManualEntity, null);
		moduleManualEntity = this.moduleManualRepository.save(moduleManualEntity);

		// set each successor to null to avoid conflicts at deletion
		structureElements = structureElements.stream()
			.peek(element -> element.setNext(null))
			.map(element -> structureRepository.save(element))
			.collect(Collectors.toList());

		// delete elements in database which are not in updated structure
		Set<Integer> idsOfStructureElementsToUpdate = structureDTO.stream()
			.filter(element -> element.getId() != null)
			.map(element -> element.getId())
			.collect(Collectors.toSet());
		for (T structureElement : structureElements) {
			Integer structureElementId = structureElement.getId();
			if (idsOfStructureElementsToUpdate.contains(structureElementId)) {
				continue;
			}

			List<VariationEntity> associatedVariations = variationRepositoryFindByStructure.apply(structureElement);
			associatedVariations.stream()
				.peek(variation -> variationSetStructure.accept(variation, null))
				.forEach(variation -> this.variationRepository.save(variation));
			
			structureRepository.delete(structureElement);
		}


		// update present elements and save new elements (values with id null) in reverse order to set successor easily
		LinkedList<StructureDTO> updatedStructureDTO = new LinkedList<>();
		ListIterator<StructureDTO> iterator = structureDTO.listIterator(structureDTO.size());
		T successor = null;
		while (iterator.hasPrevious()) {
			StructureDTO structureDTOElement = iterator.previous();
			Integer currentEntityId = structureDTOElement.getId();
			T currentEntity;
			if (currentEntityId != null) {
				currentEntity = structureRepository.findById(currentEntityId).get();
			} else {
				currentEntity = this.modulhandbuchBackendMapper.map(structureDTOElement, structureEntityClass);
			}
			currentEntity.setValue(structureDTOElement.getValue());
			currentEntity.setModuleManual(moduleManualEntity);
			currentEntity.setNext(successor);
			currentEntity = structureRepository.save(currentEntity);

			updatedStructureDTO.addFirst(this.modulhandbuchBackendMapper.map(currentEntity, StructureDTO.class));

			successor = currentEntity;
		}

		// current successor is first element so set it in module manual
		moduleManualSetFirstEntity.accept(moduleManualEntity, successor);
		this.moduleManualRepository.save(moduleManualEntity);

		return updatedStructureDTO;
	}

	private <T extends StructureEntity<T>> List<StructureDTO> replaceStructure(List<StructureDTO> structure, Integer moduleManualId, BiConsumer<ModuleManualEntity, T> moduleManualSetFirstStructureElement, StructureRepository<T> structureRepository, BiConsumer<VariationEntity, T> variationSetStructure, Function<T, List<VariationEntity>> variationRepositoryFindByStructure, Class<T> structureEntityClass, Consumer<Integer> duplicateElementsInRequestHandler, Consumer<Integer> elementNotFoundHandler) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(moduleManualId)
			.orElseThrow(() -> new ModuleManualNotFoundException(moduleManualId));

		// remove all null values in given list
		structure.removeIf(element -> (element == null));

		// check if all given ids are present or null and there are no duplicates of ids in request
		this.validateIds(structure, moduleManual, structureRepository, duplicateElementsInRequestHandler, elementNotFoundHandler);

		// delete unused elements, add new elements, update other elements
		List<StructureDTO> savedStructure = this.updateStructure(structure, moduleManual, moduleManualSetFirstStructureElement, structureRepository, variationSetStructure, variationRepositoryFindByStructure, structureEntityClass);

		return savedStructure;
	}

	public List<StructureDTO> replaceSegments(List<StructureDTO> segments, Integer moduleManualId) {
		BiConsumer<ModuleManualEntity, SectionEntity> moduleManualSetFirstSection = (moduleManual, section) -> moduleManual.setFirstSection(section);

		BiConsumer<VariationEntity, SectionEntity> variationSetSection = (variation, section) -> variation.setSegment(section);

		Function<SectionEntity, List<VariationEntity>> variationRepositoryFindBySection = section -> this.variationRepository.findBySegment(section);

		Consumer<Integer> duplicateSegmentsInRequestHandler = duplicateId -> {throw new DuplicateSegmentsInRequestException(duplicateId);};
		Consumer<Integer> segmentNotFoundHandler = notFoundId -> {throw new SegmentNotFoundException(notFoundId);};

		return this.replaceStructure(segments, moduleManualId, moduleManualSetFirstSection, this.sectionRepository, variationSetSection, variationRepositoryFindBySection, SectionEntity.class, duplicateSegmentsInRequestHandler, segmentNotFoundHandler);
	}

	public List<StructureDTO> replaceModuleTypes(@RequestBody List<StructureDTO> moduleTypes, @PathVariable Integer moduleManualId) {
		BiConsumer<ModuleManualEntity, TypeEntity> moduleManualSetFirstType = (moduleManual, type) -> moduleManual.setFirstType(type);

		BiConsumer<VariationEntity, TypeEntity> variationSetType = (variation, type) -> variation.setModuleType(type);

		Function<TypeEntity, List<VariationEntity>> variationRepositoryFindByType = type -> this.variationRepository.findByModuleType(type);

		Consumer<Integer> duplicateModuleTypesInRequestHandler = duplicateId -> {throw new DuplicateModuleTypesInRequestException(duplicateId);};
		Consumer<Integer> moduleTypeNotFoundHandler = notFoundId -> {throw new ModuleTypeNotFoundException(notFoundId);};

		return this.replaceStructure(moduleTypes, moduleManualId, moduleManualSetFirstType, this.typeRepository, variationSetType, variationRepositoryFindByType, TypeEntity.class, duplicateModuleTypesInRequestHandler, moduleTypeNotFoundHandler);
	}
}
