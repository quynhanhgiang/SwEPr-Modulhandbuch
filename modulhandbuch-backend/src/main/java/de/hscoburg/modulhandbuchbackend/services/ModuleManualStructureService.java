package de.hscoburg.modulhandbuchbackend.services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.StructureEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import lombok.Data;

@Data
@Service
public class ModuleManualStructureService {
	private final ModuleManualRepository moduleManualRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;
	
	public <T extends StructureEntity<T>, R extends StructureDTO> List<R> getStructure(Integer id, Class<R> dtoClass, Function<ModuleManualEntity, T> getFirstEntity) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
			
		List<R> structure = new LinkedList<>();

		T currentEntity = getFirstEntity.apply(moduleManual);
		while (currentEntity != null) {
			structure.add(this.modulhandbuchBackendMapper.map(currentEntity, dtoClass));

			currentEntity = currentEntity.getNext();
		}

		return structure;
	}

	public <T extends StructureEntity<T>, U extends StructureDTO> void validateIds(Iterator<U> iterator, JpaRepository<T, Integer> repository) {
		while (iterator.hasNext()) {
			U currentElement = iterator.next();
			if (currentElement.getId() == null) {
				continue;
			}

			if (repository.existsById(currentElement.getId())) {
				continue;
			}

			// TODO own exception and advice
			throw new RuntimeException(String.format("Id %d for class %s not found.", currentElement.getId(), currentElement.getClass().getSimpleName()));
		}
	}

	public <T extends StructureEntity<T>> void deleteCurrentStructure(T firstEntity, JpaRepository<T, Integer> repository) {
		T currentEntity = firstEntity;
		while (currentEntity != null) {
			repository.deleteById(currentEntity.getId());
			currentEntity = currentEntity.getNext();
		}
	}

	public <T extends StructureEntity<T>, R extends StructureDTO> List<R> saveStructure(List<R> structure, ModuleManualEntity moduleManual, JpaRepository<T, Integer> structureRepository, ModuleManualRepository moduleManualepository, Class<T> entityClass, Class<R> dtoClass, BiConsumer<ModuleManualEntity, T> setFirstEntity) {
		// save structure in reverse order to retrieve id of successor easily
		ListIterator<R> iterator = structure.listIterator(structure.size());
		LinkedList<R> savedStructure = new LinkedList<>();
		T successor = null;
		while (iterator.hasPrevious()) {
			T currentEntity = this.modulhandbuchBackendMapper.map(iterator.previous(), entityClass);
			currentEntity.setNext(successor);
			currentEntity = structureRepository.save(currentEntity);

			savedStructure.addFirst(this.modulhandbuchBackendMapper.map(currentEntity, dtoClass));

			successor = currentEntity;
		}

		setFirstEntity.accept(moduleManual, successor);
		moduleManualRepository.save(moduleManual);

		return savedStructure;
	}
}
