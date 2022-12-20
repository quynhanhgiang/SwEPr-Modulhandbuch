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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.StructureEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.StructureRepository;
import lombok.Data;

@Data
@Service
public class ModuleManualStructureService {
	private final ModuleManualRepository moduleManualRepository;
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

	public <T extends StructureEntity<T>> void validateIds(List<StructureDTO> structure, ModuleManualEntity moduleManual, StructureRepository<T> repository, Consumer<Integer> duplicateElementsInRequestHandler, Consumer<Integer> elementNotFoundHandler) {
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

	public <T extends StructureEntity<T>> void deleteCurrentStructure(T firstEntity, JpaRepository<T, Integer> repository) {
		T currentEntity = firstEntity;
		while (currentEntity != null) {
			repository.deleteById(currentEntity.getId());
			currentEntity = currentEntity.getNext();
		}
	}

	public <T extends StructureEntity<T>> List<StructureDTO> saveStructure(List<StructureDTO> structure, ModuleManualEntity moduleManual, JpaRepository<T, Integer> structureRepository, ModuleManualRepository moduleManualepository, Class<T> entityClass, BiConsumer<ModuleManualEntity, T> setFirstEntity) {
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
		moduleManualRepository.save(moduleManual);

		return savedStructure;
	}
}
