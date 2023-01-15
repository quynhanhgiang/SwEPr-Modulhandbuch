package de.hscoburg.modulhandbuchbackend.unittests.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.AdmissionRequirementNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.DuplicateAdmissionRequirementsInRequestException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualEnumService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class ModuleManualEnumServiceTest {

	@Mock
	private AdmissionRequirementRepository mockAdmissionRequirementRepository;
	@Mock
	private ModuleManualRepository mockModuleManualRepository;
	@Mock
	private VariationRepository mockVariationRepository;
	@Spy
	ModulhandbuchBackendMapper modulhandbuchBackendMapper = new ModulhandbuchBackendMapper(null, null, null, null, null, null, null);
	@InjectMocks
	private ModuleManualEnumService moduleManualEnumServiceWithMocks;

	@Mock
	private ModuleManualEntity mockModuleManualEntity;
	@Mock
	private AdmissionRequirementEntity mockAdmissionRequirementEntityWithId1;
	@Mock
	private VariationEntity mockVariationEntity;
	
	@Test
	public void testReplaceModuleManualEnum() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			BiFunction<List<EnumDTO>, Integer, List<EnumDTO>> method;
			List<EnumDTO> moduleManualEnum;
			Integer moduleManualId;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			List<EnumDTO> expectedModuleManualEnum;
		}

		EnumDTO newElement = new EnumDTO();
		newElement.setId(null);
		newElement.setValue("New element");

		EnumDTO newElementAfterPersistence = new EnumDTO();
		newElementAfterPersistence.setId(null);
		newElementAfterPersistence.setValue(newElement.getValue());

		EnumDTO existingElement1 = new EnumDTO();
		existingElement1.setId(1);
		existingElement1.setValue("Existing element 1");

		EnumDTO notExistingElement10 = new EnumDTO();
		notExistingElement10.setId(10);
		notExistingElement10.setValue("Not existing element 10");

		EnumDTO duplicateExistingElement1 = new EnumDTO();
		duplicateExistingElement1.setId(1);
		duplicateExistingElement1.setValue("Duplicate existing element 1");

		List<EnumDTO> enumNormal = Arrays.asList(
			existingElement1,
			newElement
		);

		List<EnumDTO> expectedResultForEnumNormal = Arrays.asList(
			existingElement1,
			newElementAfterPersistence
		);

		List<EnumDTO> enumEmpty = Arrays.asList();

		List<EnumDTO> expectedResultForEnumEmpty = Arrays.asList();

		List<EnumDTO> enumWithNull = Arrays.asList(
			existingElement1,
			newElement,
			null
		);

		List<EnumDTO> expectedResultForEnumWithNull = Arrays.asList(
			existingElement1,
			newElementAfterPersistence
		);

		List<EnumDTO> enumWithAbsentId = Arrays.asList(
			existingElement1,
			newElement,
			notExistingElement10
		);

		List<EnumDTO> enumWithDuplicate = Arrays.asList(
			existingElement1,
			newElement,
			duplicateExistingElement1
		);

		Set<TestParameters> testData = Set.of(
			// replaceRequirements
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumNormal), 0, false, null, null, expectedResultForEnumNormal),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumNormal), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumNormal), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumNormal), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumEmpty), 0, false, null, null, expectedResultForEnumEmpty),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumEmpty), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumEmpty), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumEmpty), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithNull), 0, false, null, null, expectedResultForEnumWithNull),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithNull), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithNull), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithNull), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithAbsentId), 0, true, AdmissionRequirementNotFoundException.class, "Admission requirement with id 10 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithAbsentId), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithAbsentId), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithAbsentId), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithDuplicate), 0, true, DuplicateAdmissionRequirementsInRequestException.class, "Admission requirement with id 1 has duplicates in request.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithDuplicate), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithDuplicate), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), new LinkedList<>(enumWithDuplicate), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), null, 0, true, NullPointerException.class, "Cannot invoke \"java.util.List.removeIf(java.util.function.Predicate)\" because \"moduleManualEnum\" is null", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), null, 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), null, -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((requirements, id) -> this.moduleManualEnumServiceWithMocks.replaceRequirements(requirements, id), null, null, true, IllegalArgumentException.class, null, null)
		);

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// module manual with id 0 is present
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.mockModuleManualEntity));

		Mockito.when(this.mockVariationRepository.findByAdmissionRequirement(Mockito.any(AdmissionRequirementEntity.class))).thenReturn(List.of(this.mockVariationEntity));

		// initially no admission requirements are present
		Mockito.when(this.mockAdmissionRequirementRepository.existsByIdAndModuleManual(Mockito.anyInt(), Mockito.any())).thenReturn(false);
		// admission requirement with id 1 is present
		Mockito.when(this.mockAdmissionRequirementRepository.existsByIdAndModuleManual(Mockito.eq(1), Mockito.any())).thenReturn(true);

		Mockito.when(this.mockAdmissionRequirementRepository.findById(1)).thenReturn(Optional.of(this.mockAdmissionRequirementEntityWithId1));

		Mockito.when(this.mockAdmissionRequirementRepository.findByModuleManual(mockModuleManualEntity)).thenReturn(List.of(this.mockAdmissionRequirementEntityWithId1));

		// return input
		Mockito.when(this.mockAdmissionRequirementRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		Mockito.when(this.mockAdmissionRequirementEntityWithId1.getId()).thenReturn(1);
		Mockito.when(this.mockAdmissionRequirementEntityWithId1.getValue()).thenReturn(existingElement1.getValue());

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> testParameters.method.apply(testParameters.moduleManualEnum, testParameters.moduleManualId), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			List<EnumDTO> receivedModuleManualEnum = Assertions.assertDoesNotThrow(() -> testParameters.method.apply(testParameters.moduleManualEnum, testParameters.moduleManualId), testParameters.toString());

			Assertions.assertEquals(testParameters.expectedModuleManualEnum, receivedModuleManualEnum, testParameters.toString());
		}
	}
}
