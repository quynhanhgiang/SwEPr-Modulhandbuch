package de.hscoburg.modulhandbuchbackend.services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.AdmissionRequirementNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateAdmissionRequirementsInRequestException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEnumEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import lombok.Data;

/**
 * This class is a service for retrieving and updating enum values assciated
 * with a module manual.
 */
@Data
@Service
public class ModuleManualEnumService {
	private final AdmissionRequirementRepository admissionRequirementRepository;
	private final ModuleManualRepository moduleManualRepository;
	private final VariationRepository variationRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	private <T extends ModuleManualEnumEntity<T>> void validateIds(List<EnumDTO> moduleManualEnum,
			ModuleManualEntity moduleManual, ModuleManualEnumRepository<T> repository,
			Consumer<Integer> duplicateElementsInRequestHandler, Consumer<Integer> elementNotFoundHandler) {
		Set<Integer> ids = new TreeSet<>();
		Iterator<EnumDTO> iterator = moduleManualEnum.iterator();
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

	private <T extends ModuleManualEnumEntity<T>> List<EnumDTO> updateModuleManualEnum(
			List<EnumDTO> moduleManualEnumDTO, ModuleManualEntity moduleManualEntity,
			ModuleManualEnumRepository<T> moduleManualEnumRepository,
			BiConsumer<VariationEntity, T> variationSetModuleManualEnum,
			Function<T, List<VariationEntity>> variationRepositoryFindByModuleManualEnum,
			Class<T> moduleManualEnumEntityClass) {
		List<T> moduleManualEnum = moduleManualEnumRepository.findByModuleManual(moduleManualEntity);

		// delete elements in database which are not in updated enum
		Set<Integer> moduleManualEnumElementsWithId = moduleManualEnumDTO.stream()
				.filter(element -> element.getId() != null)
				.map(element -> element.getId())
				.collect(Collectors.toSet());
		for (T moduleManualEnumElement : moduleManualEnum) {
			Integer moduleManualEnumElementId = moduleManualEnumElement.getId();
			if (moduleManualEnumElementsWithId.contains(moduleManualEnumElementId)) {
				continue;
			}

			List<VariationEntity> associatedVariations = variationRepositoryFindByModuleManualEnum
					.apply(moduleManualEnumElement);
			associatedVariations.stream()
					.peek(variation -> variationSetModuleManualEnum.accept(variation, null))
					.forEach(variation -> this.variationRepository.save(variation));

			moduleManualEnumRepository.delete(moduleManualEnumElement);
		}

		// update present elements and save new elements (values with id null)
		List<EnumDTO> updatedModuleManualEnumDTO = new LinkedList<>();
		for (EnumDTO moduleManualEnumDTOElement : moduleManualEnumDTO) {
			Integer currentEntityId = moduleManualEnumDTOElement.getId();
			T currentEntity;
			if (currentEntityId != null) {
				currentEntity = moduleManualEnumRepository.findById(currentEntityId).get();
			} else {
				currentEntity = this.modulhandbuchBackendMapper.map(moduleManualEnumDTOElement,
						moduleManualEnumEntityClass);
			}
			currentEntity.setValue(moduleManualEnumDTOElement.getValue());
			currentEntity.setModuleManual(moduleManualEntity);
			currentEntity = moduleManualEnumRepository.save(currentEntity);

			updatedModuleManualEnumDTO.add(this.modulhandbuchBackendMapper.map(currentEntity, EnumDTO.class));
		}

		return updatedModuleManualEnumDTO;
	}

	private <T extends ModuleManualEnumEntity<T>> List<EnumDTO> replaceModuleManualEnum(List<EnumDTO> moduleManualEnum,
			Integer moduleManualId, ModuleManualEnumRepository<T> moduleManualEnumRepository,
			BiConsumer<VariationEntity, T> variationSetModuleManualEnum,
			Function<T, List<VariationEntity>> variationRepositoryFindByModuleManualEnum,
			Class<T> moduleManualEnumEntityClass, Consumer<Integer> duplicateElementsInRequestHandler,
			Consumer<Integer> elementNotFoundHandler) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(moduleManualId)
				.orElseThrow(() -> new ModuleManualNotFoundException(moduleManualId));

		// remove all null values in given list
		moduleManualEnum.removeIf(element -> (element == null));

		// check if all given ids are associated with the given module manual or null
		// and there are no duplicates of ids in request
		this.validateIds(moduleManualEnum, moduleManual, moduleManualEnumRepository, duplicateElementsInRequestHandler,
				elementNotFoundHandler);

		// delete unused elements, add new elements, update other elements
		List<EnumDTO> savedEnum = this.updateModuleManualEnum(moduleManualEnum, moduleManual,
				moduleManualEnumRepository, variationSetModuleManualEnum, variationRepositoryFindByModuleManualEnum,
				moduleManualEnumEntityClass);

		return savedEnum;
	}

	/**
	 * This method replaces the values of the enum `admissionRequirement` with the
	 * passed ones for a specified module manual and
	 * returns the updated enum.
	 * 
	 * @param requirements   The new values of the enum `admissionRequirment` to
	 *                       replace the old ones with.
	 * @param moduleManualId The id of the module manual which the enum is
	 *                       associated with.
	 * @return The updated enum.
	 */
	public List<EnumDTO> replaceRequirements(List<EnumDTO> requirements, Integer moduleManualId) {
		Consumer<Integer> duplicateAdmissionRequirementsInRequestHandler = duplicateId -> {
			throw new DuplicateAdmissionRequirementsInRequestException(duplicateId);
		};
		Consumer<Integer> admissionRequirmentNotFoundHandler = notFoundId -> {
			throw new AdmissionRequirementNotFoundException(notFoundId);
		};

		BiConsumer<VariationEntity, AdmissionRequirementEntity> variationSetAdmissionRequirement = (variation,
				admissionRequirement) -> variation.setAdmissionRequirement(admissionRequirement);
		Function<AdmissionRequirementEntity, List<VariationEntity>> variationRepositoryFindByAdmissionRequirement = admissionRequirement -> this.variationRepository
				.findByAdmissionRequirement(admissionRequirement);

		return this.replaceModuleManualEnum(requirements, moduleManualId, this.admissionRequirementRepository,
				variationSetAdmissionRequirement, variationRepositoryFindByAdmissionRequirement,
				AdmissionRequirementEntity.class, duplicateAdmissionRequirementsInRequestHandler,
				admissionRequirmentNotFoundHandler);
	}
}
