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

		Set<TestParameters> testData = Set.of(
			// empty list, null list, list with null, list with double id
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
