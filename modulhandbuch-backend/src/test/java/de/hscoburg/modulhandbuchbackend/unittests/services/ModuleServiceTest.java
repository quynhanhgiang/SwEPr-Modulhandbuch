package de.hscoburg.modulhandbuchbackend.unittests.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import de.hscoburg.modulhandbuchbackend.dto.ModuleFullDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleVariationDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.CollegeEmployeeNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleOwnerRequiredException;
import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.services.VariationService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ModuleServiceTest {

	@Mock
	private CollegeEmployeeRepository mockCollegeEmployeeRepository;
	@Mock
	private ModuleRepository mockModuleRepository;
	@Mock
	private VariationRepository mockVariationRepository;
	@Mock
	private VariationService mockVariationService;
	@Mock
	private ModulhandbuchBackendMapper mockModulhandbuchBackendMapper;
	@Spy
	@InjectMocks
	private ModuleService moduleServiceWithMocks;

	@Mock
	private ModuleEntity mockModuleEntityWithVariations;
	@Mock
	private ModuleEntity mockModuleEntityNoVariations;
	@Mock
	private VariationEntity mockVariationEntity;

	@Mock
	private CollegeEmployeeEntity mockCollegeEmployeeEntityNoId;
	@Mock
	private CollegeEmployeeEntity mockCollegeEmployeeEntityWithId0;
	@Mock
	private CollegeEmployeeEntity mockCollegeEmployeeEntityWithId1;

	@Mock
	private ModuleEntity mockModuleEntityNoModuleOwner;
	@Mock
	private ModuleEntity mockModuleEntityWithModuleOwnerNoId;
	@Mock
	private ModuleEntity mockModuleEntityWithModuleOwnerWithId1;
	@Mock
	private ModuleEntity mockModuleEntityNoProfs;
	@Mock
	private ModuleEntity mockModuleEntityWithEmptyProfs;
	@Mock
	private ModuleEntity mockModuleEntityWithProfsNoId;
	@Mock
	private ModuleEntity mockModuleEntityWithProfsWithId0;
	@Mock
	private ModuleEntity mockModuleEntityWithProfsWithId1;

	@Test
	public void testSaveModule() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			ModuleFullDTO moduleToSave;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			ModuleFullDTO expectedModuleFullDTO;
		}

		List<ModuleVariationDTO> variationDTOs = List.of(new ModuleVariationDTO());
		ModuleFullDTO moduleFullDTOWithVariations = new ModuleFullDTO();
		moduleFullDTOWithVariations.setVariations(variationDTOs);

		ModuleFullDTO moduleFullDTONoVariations = new ModuleFullDTO();

		Set<TestParameters> testData = Set.of(
			new TestParameters(moduleFullDTOWithVariations, false, null, null, moduleFullDTOWithVariations),
			new TestParameters(new ModuleFullDTO(), false, null, null, new ModuleFullDTO()),
			new TestParameters(null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity.getVariations()\" because \"moduleEntity\" is null", null)
		);

		Mockito.doAnswer(AdditionalAnswers.returnsFirstArg()).when(this.moduleServiceWithMocks).cleanEntity(Mockito.any());

		Mockito.when(this.mockModuleRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
		Mockito.when(this.mockVariationRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		Mockito.when(this.mockVariationService.cleanEntity(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

		Mockito.when(this.mockModulhandbuchBackendMapper.map(moduleFullDTOWithVariations, ModuleEntity.class)).thenReturn(this.mockModuleEntityWithVariations);
		Mockito.when(this.mockModulhandbuchBackendMapper.map(moduleFullDTONoVariations, ModuleEntity.class)).thenReturn(this.mockModuleEntityNoVariations);

		Mockito.when(this.mockModulhandbuchBackendMapper.map(this.mockModuleEntityWithVariations, ModuleFullDTO.class)).thenReturn(moduleFullDTOWithVariations);
		Mockito.when(this.mockModulhandbuchBackendMapper.map(this.mockModuleEntityNoVariations, ModuleFullDTO.class)).thenReturn(moduleFullDTONoVariations);

		Mockito.when(this.mockModuleEntityWithVariations.getVariations()).thenReturn(List.of(this.mockVariationEntity));
		Mockito.when(this.mockModuleEntityNoVariations.getVariations()).thenReturn(null);

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.moduleServiceWithMocks.saveModule(testParameters.moduleToSave), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			ModuleFullDTO receivedModuleFullDTO = Assertions.assertDoesNotThrow(() -> this.moduleServiceWithMocks.saveModule(testParameters.moduleToSave), testParameters.toString());

			Assertions.assertEquals(testParameters.expectedModuleFullDTO, receivedModuleFullDTO, testParameters.toString());
		}
	}

	@Test
	public void testCleanEntity() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			ModuleEntity module;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			ModuleEntity expectedModuleEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters(this.mockModuleEntityNoModuleOwner, true, ModuleOwnerRequiredException.class, "Module owner is required but is null.", null),
			new TestParameters(this.mockModuleEntityWithModuleOwnerNoId, true, ModuleOwnerRequiredException.class, "Module owner is required but is null.", null),
			new TestParameters(this.mockModuleEntityWithModuleOwnerWithId1, true, CollegeEmployeeNotFoundException.class, "College employee with id 1 not found.", null),
			
			new TestParameters(this.mockModuleEntityNoProfs, false, null, null, this.mockModuleEntityNoProfs),
			new TestParameters(this.mockModuleEntityWithEmptyProfs, false, null, null, this.mockModuleEntityWithEmptyProfs),
			new TestParameters(this.mockModuleEntityWithProfsNoId, false, null, null, this.mockModuleEntityWithProfsNoId),
			new TestParameters(this.mockModuleEntityWithProfsWithId0, false, null, null, this.mockModuleEntityWithProfsWithId0),
			new TestParameters(this.mockModuleEntityWithProfsWithId1, false, null, null, this.mockModuleEntityWithProfsWithId1),

			new TestParameters(null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity.getModuleOwner()\" because \"module\" is null", null)
		);

		// initially no college employees are present
		Mockito.when(this.mockCollegeEmployeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// college employee with id 0 is present
		Mockito.when(this.mockCollegeEmployeeRepository.findById(0)).thenReturn(Optional.of(this.mockCollegeEmployeeEntityWithId0));

		Mockito.when(this.mockCollegeEmployeeEntityNoId.getId()).thenReturn(null);
		Mockito.when(this.mockCollegeEmployeeEntityWithId0.getId()).thenReturn(0);
		Mockito.when(this.mockCollegeEmployeeEntityWithId1.getId()).thenReturn(1);

		// mocked modules
		Mockito.when(this.mockModuleEntityWithModuleOwnerNoId.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityNoId);

		Mockito.when(this.mockModuleEntityWithModuleOwnerWithId1.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityWithId1);

		Mockito.when(this.mockModuleEntityNoProfs.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityWithId0);
		Mockito.when(this.mockModuleEntityNoProfs.getProfs()).thenReturn(null);

		Mockito.when(this.mockModuleEntityWithEmptyProfs.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityWithId0);
		Mockito.when(this.mockModuleEntityWithEmptyProfs.getProfs()).thenReturn(List.of());

		Mockito.when(this.mockModuleEntityWithProfsNoId.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityWithId0);
		Mockito.when(this.mockModuleEntityWithProfsNoId.getProfs()).thenReturn(List.of(this.mockCollegeEmployeeEntityNoId));

		Mockito.when(this.mockModuleEntityWithProfsWithId1.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityWithId0);
		Mockito.when(this.mockModuleEntityWithProfsWithId1.getProfs()).thenReturn(List.of(this.mockCollegeEmployeeEntityWithId1));

		Mockito.when(this.mockModuleEntityWithProfsWithId0.getModuleOwner()).thenReturn(this.mockCollegeEmployeeEntityWithId0);
		Mockito.when(this.mockModuleEntityWithProfsWithId0.getProfs()).thenReturn(List.of(this.mockCollegeEmployeeEntityWithId0));
		
		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.moduleServiceWithMocks.cleanEntity(testParameters.module), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			ModuleEntity receivedModuleEntity = Assertions.assertDoesNotThrow(() -> this.moduleServiceWithMocks.cleanEntity(testParameters.module), testParameters.toString());

			Assertions.assertEquals(testParameters.expectedModuleEntity, receivedModuleEntity, testParameters.toString());
		}
	}
}
