package de.hscoburg.modulhandbuchbackend.unittests.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.controllers.ModuleManualStructureController;
import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
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

	@Test
	public void testPutMappings() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			BiFunction<List<StructureDTO>, Integer, List<StructureDTO>> method;
			List<StructureDTO> structure;
			Integer id;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
			List<StructureDTO> expectedStructureDTOList;
		}

		StructureDTO newElement = new StructureDTO();
		newElement.setId(null);
		newElement.setValue("New element");

		// StructureDTO

		Set<TestParameters> testData = Set.of(
			// // oneModulePlan
			// new TestParameters((id, structure) -> this.moduleManualDocumentControllerWithMocks.replaceSegments(id, structure), 0, false, null, null, this.fileInfoModulePlan),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), 1, false, null, null, new FileInfoDTO()),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), 2, true, RuntimeException.class, "Id 2 for module manual not found.", null),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), -1, true, RuntimeException.class, "Id -1 for module manual not found.", null),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), null, true, IllegalArgumentException.class, null, null),

			// // onePreliminaryNote
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), 0, false, null, null, this.fileInfoPreliminaryNote),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), 1, false, null, null, new FileInfoDTO()),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), 2, true, RuntimeException.class, "Id 2 for module manual not found.", null),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), -1, true, RuntimeException.class, "Id -1 for module manual not found.", null),
			// new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), null, true, IllegalArgumentException.class, null, null)
		);

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// TODO
		// // module manual with id 0 is present and is linked to documents
		// Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.moduleManualEntityWithContent));
		// // module manual with id 1 is present but is not linked to documents
		// Mockito.when(this.mockModuleManualRepository.findById(1)).thenReturn(Optional.of(this.mockModuleManualEntity));

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
