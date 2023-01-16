package de.hscoburg.modulhandbuchbackend.unittests.services;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SegmentEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleTypeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SegmentRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleTypeRepository;
import de.hscoburg.modulhandbuchbackend.services.VariationService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class VariationServiceTest {
	
	@Mock
	private AdmissionRequirementRepository mockAdmissionRequirementRepository;
	@Mock
	private ModuleManualRepository mockModuleManualRepository;
	@Mock
	private ModuleRepository mockModuleRepository;
	@Mock
	private SegmentRepository mockSegmentRepository;
	@Mock
	private ModuleTypeRepository mockModuleTypeRepository;
	@InjectMocks
	private VariationService variationServiceWithMocks;

	@Mock
	private ModuleEntity mockModuleEntityNoId;
	@Mock
	private ModuleEntity mockModuleEntityWithId0;
	@Mock
	private ModuleEntity mockModuleEntityWithId1;
	@Mock
	private ModuleManualEntity mockModuleManualEntityNoId;
	@Mock
	private ModuleManualEntity mockModuleManualEntityWithId0;
	@Mock
	private ModuleManualEntity mockModuleManualEntityWithId1;
	@Mock
	private ModuleTypeEntity mockModuleTypeEntityNoId;
	@Mock
	private ModuleTypeEntity mockModuleTypeEntityWithId0;
	@Mock
	private SegmentEntity mockSegmentEntityNoId;
	@Mock
	private SegmentEntity mockSegmentEntityWithId0;
	@Mock
	private AdmissionRequirementEntity mockAdmissionRequirementEntityNoId;
	@Mock
	private AdmissionRequirementEntity mockAdmissionRequirementEntityWithId0;

	@Mock
	private VariationEntity mockVariationEntityNoModule;
	@Mock
	private VariationEntity mockVariationEntityWithModuleNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleWithId1;
	@Mock
	private VariationEntity mockVariationEntityNoModuleManual;
	@Mock
	private VariationEntity mockVariationEntityWithModuleManualNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleManualWithId1;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0;

	@Test
	public void testCleanEntity() {
		// TODO compare result
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			VariationEntity variationEntity;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			// VariationEntity expectedVariationEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters(this.mockVariationEntityNoModule, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleWithId1, true, ModuleNotFoundException.class, "Module with id 1 not found."),

			new TestParameters(this.mockVariationEntityNoModuleManual, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleManualNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleManualWithId1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found."),

			// no admission requirement
			new TestParameters(this.mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement, false, null, null),

			new TestParameters(this.mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement, false, null, null),
			
			new TestParameters(this.mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement, false, null, null),

			// admission requirement with no id
			new TestParameters(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId, false, null, null),

			new TestParameters(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId, false, null, null),
			
			new TestParameters(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId, false, null, null),

			// admission requirement with id 0
			new TestParameters(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0, false, null, null),

			new TestParameters(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0, false, null, null),
			
			new TestParameters(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0, false, null, null),

			new TestParameters(null, true, NullPointerException.class, "Cannot invoke \"de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity.getModule()\" because \"variation\" is null")
		);

		// initially no modules are present
		Mockito.when(this.mockModuleRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// module with id 0 is present
		Mockito.when(this.mockModuleRepository.findById(0)).thenReturn(Optional.of(this.mockModuleEntityWithId0));

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// module manual with id 0 is present
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.mockModuleManualEntityWithId0));

		// initially no module types are present
		Mockito.when(this.mockModuleTypeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// module type with id 0 is present
		Mockito.when(this.mockModuleTypeRepository.findById(0)).thenReturn(Optional.of(this.mockModuleTypeEntityWithId0));

		// initially no segments are present
		Mockito.when(this.mockSegmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// segment with id 0 is present
		Mockito.when(this.mockSegmentRepository.findById(0)).thenReturn(Optional.of(this.mockSegmentEntityWithId0));

		// initially no admission Requirements are present
		Mockito.when(this.mockAdmissionRequirementRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// admission requirement with id 0 is present
		Mockito.when(this.mockAdmissionRequirementRepository.findById(0)).thenReturn(Optional.of(this.mockAdmissionRequirementEntityWithId0));

		Mockito.when(this.mockModuleEntityWithId0.getId()).thenReturn(0);
		Mockito.when(this.mockModuleEntityWithId1.getId()).thenReturn(1);

		Mockito.when(this.mockModuleManualEntityWithId0.getId()).thenReturn(0);
		Mockito.when(this.mockModuleManualEntityWithId1.getId()).thenReturn(1);

		Mockito.when(this.mockModuleTypeEntityWithId0.getId()).thenReturn(0);

		Mockito.when(this.mockSegmentEntityWithId0.getId()).thenReturn(0);

		Mockito.when(this.mockAdmissionRequirementEntityWithId0.getId()).thenReturn(0);

		// mocked variations
		Mockito.when(this.mockVariationEntityNoModule.getModule()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleNoId.getModule()).thenReturn(this.mockModuleEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleWithId1.getModule()).thenReturn(this.mockModuleEntityWithId1);

		Mockito.when(this.mockVariationEntityNoModuleManual.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleManual.getModuleManual()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleManualNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleManualNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleManualWithId1.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleManualWithId1.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId1);

		// no segment, no admission requirement
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		// segment with no id, no admission requirement
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		// segment with id 0, no admission requirement
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0NoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0NoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0NoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		// no segment, admission requirement with no id
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSegmentWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		// segment with no id, admission requirement with no id
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		// segment with id 0, admission requirement with no id
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		// no segment, admission requirement with id 0
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeNoSegmentWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdNoSegmentWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0NoSegmentWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		// segment with no id, admission requirement with id 0
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentNoIdWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentNoIdWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSegmentEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentNoIdWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		// segment with id 0, admission requirement with id 0
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityNoModuleTypeWithSegmentWithId0WithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockModuleTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeNoIdWithSegmentWithId0WithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockModuleTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSegmentEntityWithId0);
		Mockito.when(this.mockVariationEntityWithModuleTypeWithId0WithSegmentWithId0WithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.variationServiceWithMocks.cleanEntity(testParameters.variationEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertDoesNotThrow(() -> this.variationServiceWithMocks.cleanEntity(testParameters.variationEntity));
		}
	}
}
