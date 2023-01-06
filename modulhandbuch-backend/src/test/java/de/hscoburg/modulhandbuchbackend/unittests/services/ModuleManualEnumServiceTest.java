package de.hscoburg.modulhandbuchbackend.unittests.services;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.AdmissionRequirementNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateAdmissionRequirementsInRequestException;
import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEnumEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualEnumService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class ModuleManualEnumServiceTest {

	@InjectMocks
	private ModuleManualEnumService moduleManualEnumServiceWithMocks;

	@Mock
	private ModuleManualEntity mockModuleManualEntity;
	@Mock
	private AdmissionRequirementRepository mockAdmissionRequirementRepository;
	
	@Test
	public void testValidateIds() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters<T extends ModuleManualEnumEntity<T>> {
			List<EnumDTO> enumDto;
			ModuleManualEntity moduleManual;
			ModuleManualEnumRepository<T> repository;
			Consumer<Integer> duplicateElementsInRequestHandler;
			Consumer<Integer> elementNotFoundHandler;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
		}

		EnumDTO newElement = new EnumDTO();
		newElement.setId(null);
		newElement.setValue("New element");

		EnumDTO existingElement1 = new EnumDTO();
		existingElement1.setId(1);
		existingElement1.setValue("Existing element 1");

		EnumDTO notExistingElement2 = new EnumDTO();
		notExistingElement2.setId(2);
		notExistingElement2.setValue("Not existing element 2");

		EnumDTO duplicateExistingElement1 = new EnumDTO();
		duplicateExistingElement1.setId(1);
		duplicateExistingElement1.setValue("Duplicate existing element 1");

		List<EnumDTO> enumNormal = Arrays.asList(
			existingElement1,
			newElement
		);

		List<EnumDTO> enumEmpty = Arrays.asList();

		List<EnumDTO> enumWithNull = Arrays.asList(
			existingElement1,
			newElement,
			null
		);

		List<EnumDTO> enumWithAbsentId = Arrays.asList(
			existingElement1,
			newElement,
			notExistingElement2
		);

		List<EnumDTO> enumWithDuplicate = Arrays.asList(
			existingElement1,
			newElement,
			duplicateExistingElement1
		);

		Consumer<Integer> duplicateAdmissionRequirementsInRequestHandler = id -> {throw new DuplicateAdmissionRequirementsInRequestException(id);};
		Consumer<Integer> admissionRequirementNotFoundHandler = id -> {throw new AdmissionRequirementNotFoundException(id);};

		// AdmissionRequirementEntity
		Set<TestParameters<AdmissionRequirementEntity>> testDataAdmissionRequirementEntity = Set.of(
			// enumNormal
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, false, null, null),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, null, false, null, null),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumNormal, this.mockModuleManualEntity, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			new TestParameters<>(enumNormal, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumNormal, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, false, null, null),
			new TestParameters<>(enumNormal, null, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumNormal, null, this.mockAdmissionRequirementRepository, null, null, false, null, null),
			new TestParameters<>(enumNormal, null, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumNormal, null, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumNormal, null, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumNormal, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			// enumEmpty
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, null, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, null, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, null, null, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, this.mockModuleManualEntity, null, null, null, false, null, null),

			new TestParameters<>(enumEmpty, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, false, null, null),
			new TestParameters<>(enumEmpty, null, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, null, this.mockAdmissionRequirementRepository, null, null, false, null, null),
			new TestParameters<>(enumEmpty, null, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, null, null, duplicateAdmissionRequirementsInRequestHandler, null, false, null, null),
			new TestParameters<>(enumEmpty, null, null, null, admissionRequirementNotFoundHandler, false, null, null),
			new TestParameters<>(enumEmpty, null, null, null, null, false, null, null),

			// enumWithNull
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithNull, this.mockModuleManualEntity, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			new TestParameters<>(enumWithNull, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, null, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, null, this.mockAdmissionRequirementRepository, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.dto.EnumDTO.getId()\" because \"currentElement\" is null"),
			new TestParameters<>(enumWithNull, null, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithNull, null, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithNull, null, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithNull, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			// enumWithAbsentId
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, AdmissionRequirementNotFoundException.class, "Admission requirement with id 2 not found."),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"elementNotFoundHandler\" is null"),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, true, AdmissionRequirementNotFoundException.class, "Admission requirement with id 2 not found."),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, null, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"elementNotFoundHandler\" is null"),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithAbsentId, this.mockModuleManualEntity, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			new TestParameters<>(enumWithAbsentId, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, AdmissionRequirementNotFoundException.class, "Admission requirement with id 2 not found."),
			new TestParameters<>(enumWithAbsentId, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"elementNotFoundHandler\" is null"),
			new TestParameters<>(enumWithAbsentId, null, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, true, AdmissionRequirementNotFoundException.class, "Admission requirement with id 2 not found."),
			new TestParameters<>(enumWithAbsentId, null, this.mockAdmissionRequirementRepository, null, null, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"elementNotFoundHandler\" is null"),
			new TestParameters<>(enumWithAbsentId, null, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithAbsentId, null, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithAbsentId, null, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithAbsentId, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			// enumWithDuplicate
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, DuplicateAdmissionRequirementsInRequestException.class, "Admission requirement with id 1 has duplicates in request."),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, true, DuplicateAdmissionRequirementsInRequestException.class, "Admission requirement with id 1 has duplicates in request."),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"duplicateElementsInRequestHandler\" is null"),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, this.mockAdmissionRequirementRepository, null, null, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"duplicateElementsInRequestHandler\" is null"),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithDuplicate, this.mockModuleManualEntity, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),

			new TestParameters<>(enumWithDuplicate, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, DuplicateAdmissionRequirementsInRequestException.class, "Admission requirement with id 1 has duplicates in request."),
			new TestParameters<>(enumWithDuplicate, null, this.mockAdmissionRequirementRepository, duplicateAdmissionRequirementsInRequestHandler, null, true, DuplicateAdmissionRequirementsInRequestException.class, "Admission requirement with id 1 has duplicates in request."),
			new TestParameters<>(enumWithDuplicate, null, this.mockAdmissionRequirementRepository, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"duplicateElementsInRequestHandler\" is null"),
			new TestParameters<>(enumWithDuplicate, null, this.mockAdmissionRequirementRepository, null, null, true, NullPointerException.class, "Cannot invoke \"java.util.function.Consumer.accept(Object)\" because \"duplicateElementsInRequestHandler\" is null"),
			new TestParameters<>(enumWithDuplicate, null, null, duplicateAdmissionRequirementsInRequestHandler, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithDuplicate, null, null, duplicateAdmissionRequirementsInRequestHandler, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithDuplicate, null, null, null, admissionRequirementNotFoundHandler, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null"),
			new TestParameters<>(enumWithDuplicate, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.repositories.ModuleManualEnumRepository.existsByIdAndModuleManual(java.lang.Integer, de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity)\" because \"repository\" is null")
		);

		// initially no admission requirements are present
		Mockito.when(this.mockAdmissionRequirementRepository.existsByIdAndModuleManual(Mockito.anyInt(), Mockito.any())).thenReturn(false);
		// admission requirement with id 1 is present
		Mockito.when(this.mockAdmissionRequirementRepository.existsByIdAndModuleManual(Mockito.eq(1), Mockito.any())).thenReturn(true);

		for (TestParameters<AdmissionRequirementEntity> testParameters : testDataAdmissionRequirementEntity) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException,() -> this.moduleManualEnumServiceWithMocks.validateIds(testParameters.enumDto, testParameters.moduleManual, testParameters.repository, testParameters.duplicateElementsInRequestHandler, testParameters.elementNotFoundHandler), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertDoesNotThrow(() -> this.moduleManualEnumServiceWithMocks.validateIds(testParameters.enumDto, testParameters.moduleManual, testParameters.repository, testParameters.duplicateElementsInRequestHandler, testParameters.elementNotFoundHandler), testParameters.toString());
		}
	}
}
