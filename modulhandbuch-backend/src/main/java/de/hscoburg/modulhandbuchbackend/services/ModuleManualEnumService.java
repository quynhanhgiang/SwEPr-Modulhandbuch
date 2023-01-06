package de.hscoburg.modulhandbuchbackend.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEnumEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository;
import lombok.Data;

@Data
@Service
public class ModuleManualEnumService {
	
	public <T extends ModuleManualEnumEntity<T>> void validateIds(List<EnumDTO> enumDto, ModuleManualEntity moduleManual, ModuleManualEnumRepository<T> repository, Consumer<Integer> duplicateElementsInRequestHandler, Consumer<Integer> elementNotFoundHandler) {
		Set<Integer> ids = new TreeSet<>();
		Iterator<EnumDTO> iterator = enumDto.iterator();
		while (iterator.hasNext()) {
			EnumDTO currentElement = iterator.next();
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
}
