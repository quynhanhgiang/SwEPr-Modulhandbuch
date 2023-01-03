package de.hscoburg.modulhandbuchbackend.unittests.controllers;

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
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.controllers.ModuleManualStructureController;
import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualStructureService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class ModuleManualStructureControllerTest {
	
	@Mock
	private ModuleManualRepository mockModuleManualRepository;
	@Mock
	private SectionRepository mockSectionRepository;
	@Mock
	private TypeRepository mockTypeRepository;
	@Mock
	private ModuleManualStructureService mockModuleManualStructureService;
	@InjectMocks
	private ModuleManualStructureController moduleManualStructureControllerWithMocks;

	@Mock
	private ModuleManualEntity mockModuleManualEntity;

	@Test
	public void testPutMappings() {
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

		StructureDTO existingElement1 = new StructureDTO();
		existingElement1.setId(1);
		existingElement1.setValue("Existing element 1");

		StructureDTO existingElement2 = new StructureDTO();
		existingElement2.setId(2);
		existingElement2.setValue("Existing element 2");

		List<StructureDTO> structure = Arrays.asList(
			existingElement1,
			newElement,
			existingElement2,
			null
		);

		List<StructureDTO> expectedResultForStructure = Arrays.asList(
			existingElement1,
			newElement,
			existingElement2,
			null
		);

		Set<TestParameters> testData = Set.of(
			// replaceSegments
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(structure), 0, false, null, null, expectedResultForStructure),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(structure), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(structure), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(structure), null, true, IllegalArgumentException.class, null, null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(), 0, false, null, null, new LinkedList<>()),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), new LinkedList<>(), null, true, IllegalArgumentException.class, null, null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), null, 0, false, null, null, null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), null, 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), null, -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((segments, id) -> this.moduleManualStructureControllerWithMocks.replaceSegments(segments, id), null, null, true, IllegalArgumentException.class, null, null),

			// replaceModuleTypes
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structure), 0, false, null, null, expectedResultForStructure),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structure), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structure), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(structure), null, true, IllegalArgumentException.class, null, null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(), 0, false, null, null, new LinkedList<>()),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(), 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), new LinkedList<>(), null, true, IllegalArgumentException.class, null, null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), null, 0, false, null, null, null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), null, 1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), null, -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((moduleTypes, id) -> this.moduleManualStructureControllerWithMocks.replaceModuleTypes(moduleTypes, id), null, null, true, IllegalArgumentException.class, null, null)
		);

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// module manual with id 0 is present
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.mockModuleManualEntity));
		
		Mockito.when(this.mockModuleManualStructureService.updateStructure(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> testParameters.method.apply(testParameters.structure, testParameters.id), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertEquals(testParameters.expectedStructureDTOList, testParameters.method.apply(testParameters.structure, testParameters.id), testParameters.toString());
		}
	}
}
