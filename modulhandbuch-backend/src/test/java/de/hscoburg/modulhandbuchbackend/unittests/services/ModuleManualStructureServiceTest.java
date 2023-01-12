package de.hscoburg.modulhandbuchbackend.unittests.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualStructureService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class ModuleManualStructureServiceTest {

	@Mock
	private ModuleManualRepository mockModuleManualRepository;
	@Mock
	private SectionRepository mockSectionRepository;
	@Mock
	private TypeRepository mockTypeRepository;
	@Mock
	private VariationRepository mockVariationRepository;
	@Spy
	ModulhandbuchBackendMapper mapper = new ModulhandbuchBackendMapper(null, null, null, null, null, null, null);
	@InjectMocks
	private ModuleManualStructureService moduleManualStructureServiceWithMocks;

	@Mock
	private ModuleManualEntity mockModuleManualEntity;
	@Mock
	private SectionEntity mockSectionEntityWithId1;
	@Mock
	private TypeEntity mockTypeEntityWithId1;
	@Mock
	private VariationEntity mockVariationEntity;
	
	@Test
	public void testGetStructure() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters<T extends StructureEntity<T>> {
			Integer moduleManualId;
			Function<ModuleManualEntity, T> getFirstEntity;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			List<StructureDTO> expectedStructureDTOList;
		}

		StructureDTO element = new StructureDTO();
		element.setId(0);

		List<StructureDTO> expectedResult = List.of(element);

		Set<TestParameters<? extends StructureEntity<?>>> testData = Set.of(
			new TestParameters<SectionEntity>(0, moduleManual -> moduleManual.getFirstSection(), false, null, null, expectedResult),
			new TestParameters<TypeEntity>(0, moduleManual -> moduleManual.getFirstType(), false, null, null, expectedResult),
			
			new TestParameters<SectionEntity>(1, moduleManual -> moduleManual.getFirstSection(), true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters<TypeEntity>(1, moduleManual -> moduleManual.getFirstType(), true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),

			new TestParameters<SectionEntity>(-1, moduleManual -> moduleManual.getFirstSection(), true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters<TypeEntity>(-1, moduleManual -> moduleManual.getFirstType(), true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),

			new TestParameters<SectionEntity>(null, moduleManual -> moduleManual.getFirstSection(), true, IllegalArgumentException.class, null, null),
			new TestParameters<TypeEntity>(null, moduleManual -> moduleManual.getFirstType(), true, IllegalArgumentException.class, null, null)
		);

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// module manual with id 0 is present
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.mockModuleManualEntity));

		Mockito.when(this.mockModuleManualEntity.getFirstSection()).thenReturn(this.mockSectionEntityWithId1);
		Mockito.when(this.mockModuleManualEntity.getFirstType()).thenReturn(this.mockTypeEntityWithId1);

		for (TestParameters<?> testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.moduleManualStructureServiceWithMocks.getStructure(testParameters.moduleManualId, testParameters.getFirstEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			List<StructureDTO> receivedStructureDTOList = Assertions.assertDoesNotThrow(() -> this.moduleManualStructureServiceWithMocks.getStructure(testParameters.moduleManualId, testParameters.getFirstEntity));

			Assertions.assertEquals(testParameters.expectedStructureDTOList, receivedStructureDTOList, testParameters.toString());
		}
	}

	@Test
	public void testReplaceStructure() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			BiFunction<List<StructureDTO>, Integer, List<StructureDTO>> method;
			List<StructureDTO> structure;
			Integer id;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			List<StructureDTO> expectedStructureDTOList;
		}

		StructureDTO newElement = new StructureDTO();
		newElement.setId(null);
		newElement.setValue("New element");

		StructureDTO newElementAfterPersistence = new StructureDTO();
		newElementAfterPersistence.setId(null);
		newElementAfterPersistence.setValue(newElement.getValue());

		StructureDTO existingElement1 = new StructureDTO();
		existingElement1.setId(1);
		existingElement1.setValue("Existing element 1");

		StructureDTO notExistingElement10 = new StructureDTO();
		notExistingElement10.setId(10);
		notExistingElement10.setValue("Not existing element 10");

		StructureDTO duplicateExistingElement1 = new StructureDTO();
		duplicateExistingElement1.setId(1);
		duplicateExistingElement1.setValue("Duplicate existing element 1");

		List<StructureDTO> structureNormal = Arrays.asList(
			existingElement1,
			newElement
		);

		List<StructureDTO> expectedResultForStructureNormal = Arrays.asList(
			existingElement1,
			newElementAfterPersistence
		);

		List<StructureDTO> structureEmpty = Arrays.asList();

		List<StructureDTO> expectedResultForStructureEmpty = Arrays.asList();

		List<StructureDTO> structureWithNull = Arrays.asList(
			existingElement1,
			newElement,
			null
		);

		List<StructureDTO> expectedResultForStructureWithNull = Arrays.asList(
			existingElement1,
			newElementAfterPersistence
		);

		List<StructureDTO> structureWithAbsentId = Arrays.asList(
			existingElement1,
			newElement,
			notExistingElement10
		);

		List<StructureDTO> structureWithDuplicate = Arrays.asList(
			existingElement1,
			newElement,
			duplicateExistingElement1
		);

		Set<TestParameters> testData = Set.of(
			// replaceSegments
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureNormal), 0, false, null, null, expectedResultForStructureNormal),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureNormal), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureNormal), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureNormal), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureEmpty), 0, false, null, null, expectedResultForStructureEmpty),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureEmpty), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureEmpty), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureEmpty), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithNull), 0, false, null, null, expectedResultForStructureWithNull),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithNull), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithNull), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithNull), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithAbsentId), 0, true, SegmentNotFoundException.class, "Segment with id 10 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithAbsentId), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithAbsentId), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithAbsentId), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithDuplicate), 0, true, DuplicateSegmentsInRequestException.class, "Segment with id 1 has duplicates in request.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithDuplicate), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithDuplicate), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), new LinkedList<>(structureWithDuplicate), null, true, IllegalArgumentException.class, null, null),
			
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), null, 0, true, NullPointerException.class, "Cannot invoke \"java.util.List.removeIf(java.util.function.Predicate)\" because \"structure\" is null", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), null, 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), null, -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureServiceWithMocks.replaceSegments(segments, id), null, null, true, IllegalArgumentException.class, null, null),

			// replaceModuleTypes
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureNormal), 0, false, null, null, expectedResultForStructureNormal),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureNormal), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureNormal), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureNormal), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureEmpty), 0, false, null, null, expectedResultForStructureEmpty),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureEmpty), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureEmpty), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureEmpty), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithNull), 0, false, null, null, expectedResultForStructureWithNull),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithNull), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithNull), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithNull), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithAbsentId), 0, true, ModuleTypeNotFoundException.class, "Module type with id 10 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithAbsentId), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithAbsentId), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithAbsentId), null, true, IllegalArgumentException.class, null, null),

			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithDuplicate), 0, true, DuplicateModuleTypesInRequestException.class, "Module type with id 1 has duplicates in request.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithDuplicate), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithDuplicate), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structureWithDuplicate), null, true, IllegalArgumentException.class, null, null),
			
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), null, 0, true, NullPointerException.class, "Cannot invoke \"java.util.List.removeIf(java.util.function.Predicate)\" because \"structure\" is null", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), null, 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), null, -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureServiceWithMocks.replaceModuleTypes(moduleTypes, id), null, null, true, IllegalArgumentException.class, null, null)
		);

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// module manual with id 0 is present
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.mockModuleManualEntity));

		// return input
		Mockito.when(this.mockModuleManualRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		Mockito.when(this.mockVariationRepository.findBySegment(Mockito.any(SectionEntity.class))).thenReturn(List.of(this.mockVariationEntity));
		Mockito.when(this.mockVariationRepository.findByModuleType(Mockito.any(TypeEntity.class))).thenReturn(List.of(this.mockVariationEntity));

		// initially no sections are present
		Mockito.when(this.mockSectionRepository.existsByIdAndModuleManual(Mockito.anyInt(), Mockito.any())).thenReturn(false);
		// section with id 1 is present
		Mockito.when(this.mockSectionRepository.existsByIdAndModuleManual(Mockito.eq(1), Mockito.any())).thenReturn(true);

		Mockito.when(this.mockSectionRepository.findById(1)).thenReturn(Optional.of(this.mockSectionEntityWithId1));
		
		Mockito.when(this.mockSectionRepository.findByModuleManual(mockModuleManualEntity)).thenReturn(List.of(this.mockSectionEntityWithId1));

		// return input
		Mockito.when(this.mockSectionRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		Mockito.when(this.mockSectionEntityWithId1.getId()).thenReturn(1);
		Mockito.when(this.mockSectionEntityWithId1.getValue()).thenReturn(existingElement1.getValue());

		// initially no types are present
		Mockito.when(this.mockTypeRepository.existsByIdAndModuleManual(Mockito.anyInt(), Mockito.any())).thenReturn(false);
		// tpye with id 1 is present
		Mockito.when(this.mockTypeRepository.existsByIdAndModuleManual(Mockito.eq(1), Mockito.any())).thenReturn(true);

		Mockito.when(this.mockTypeRepository.findById(1)).thenReturn(Optional.of(this.mockTypeEntityWithId1));

		Mockito.when(this.mockTypeRepository.findByModuleManual(mockModuleManualEntity)).thenReturn(List.of(this.mockTypeEntityWithId1));

		// return input
		Mockito.when(this.mockTypeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		Mockito.when(this.mockTypeEntityWithId1.getId()).thenReturn(1);
		Mockito.when(this.mockTypeEntityWithId1.getValue()).thenReturn(existingElement1.getValue());

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> testParameters.method.apply(testParameters.structure, testParameters.id), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			List<StructureDTO> receivedStructureDTOList = Assertions.assertDoesNotThrow(() -> testParameters.method.apply(testParameters.structure, testParameters.id), testParameters.toString());

			Assertions.assertEquals(testParameters.expectedStructureDTOList, receivedStructureDTOList, testParameters.toString());
		}
	}
}
