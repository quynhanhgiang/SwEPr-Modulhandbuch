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
import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
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
	private SectionRepository mockSectionRepository;
	@Mock
	private TypeRepository mockTypeRepository;
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
	private TypeEntity mockTypeEntityNoId;
	@Mock
	private TypeEntity mockTypeEntityWithId0;
	@Mock
	private SectionEntity mockSectionEntityNoId;
	@Mock
	private SectionEntity mockSectionEntityWithId0;
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
	private VariationEntity mockVariationEntityNoTypeNoSectionNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement;
	@Mock
	private VariationEntity mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId;
	@Mock
	private VariationEntity mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0;
	@Mock
	private VariationEntity mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0;

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
			new TestParameters(this.mockVariationEntityWithModuleWithId1, true, ModuleNotFoundException.class, "Could not find module 1"),

			new TestParameters(this.mockVariationEntityNoModuleManual, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleManualNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithModuleManualWithId1, true, ModuleManualNotFoundException.class, "Module manual with id 1 not found."),

			// no admission requirement
			new TestParameters(this.mockVariationEntityNoTypeNoSectionNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement, false, null, null),

			new TestParameters(this.mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement, false, null, null),
			
			new TestParameters(this.mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement, false, null, null),

			// admission requirement with no id
			new TestParameters(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId, false, null, null),

			new TestParameters(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId, false, null, null),
			
			new TestParameters(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId, false, null, null),

			// admission requirement with id 0
			new TestParameters(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0, false, null, null),

			new TestParameters(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0, false, null, null),
			
			new TestParameters(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0, false, null, null),
			new TestParameters(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0, false, null, null),

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

		// initially no types are present
		Mockito.when(this.mockTypeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// type with id 0 is present
		Mockito.when(this.mockTypeRepository.findById(0)).thenReturn(Optional.of(this.mockTypeEntityWithId0));

		// initially no sections are present
		Mockito.when(this.mockSectionRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// section with id 0 is present
		Mockito.when(this.mockSectionRepository.findById(0)).thenReturn(Optional.of(this.mockSectionEntityWithId0));

		// initially no admission Requirements are present
		Mockito.when(this.mockAdmissionRequirementRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// admission requirement with id 0 is present
		Mockito.when(this.mockAdmissionRequirementRepository.findById(0)).thenReturn(Optional.of(this.mockAdmissionRequirementEntityWithId0));

		Mockito.when(this.mockModuleEntityWithId0.getId()).thenReturn(0);
		Mockito.when(this.mockModuleEntityWithId1.getId()).thenReturn(1);

		Mockito.when(this.mockModuleManualEntityWithId0.getId()).thenReturn(0);
		Mockito.when(this.mockModuleManualEntityWithId1.getId()).thenReturn(1);

		Mockito.when(this.mockTypeEntityWithId0.getId()).thenReturn(0);

		Mockito.when(this.mockSectionEntityWithId0.getId()).thenReturn(0);

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

		// no section, no admission requirement
		Mockito.when(this.mockVariationEntityNoTypeNoSectionNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionNoAdmissionRequirement.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionNoAdmissionRequirement.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		// section with no id, no admission requirement
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdNoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		// section with id 0, no admission requirement
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0NoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0NoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0NoAdmissionRequirement.getAdmissionRequirement()).thenReturn(null);

		// no section, admission requirement with no id
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		// section with no id, admission requirement with no id
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		// section with id 0, admission requirement with no id
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementNoId.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityNoId);

		// no section, admission requirement with id 0
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeNoSectionWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeNoIdNoSectionWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0.getSegment()).thenReturn(null);
		Mockito.when(this.mockVariationEntityWithTypeWithId0NoSectionWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		// section with no id, admission requirement with id 0
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionNoIdWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionNoIdWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSectionEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionNoIdWithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		// section with id 0, admission requirement with id 0
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0.getModuleType()).thenReturn(null);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityNoTypeWithSectionWithId0WithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockTypeEntityNoId);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeNoIdWithSectionWithId0WithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0.getModule()).thenReturn(this.mockModuleEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0.getModuleManual()).thenReturn(this.mockModuleManualEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0.getModuleType()).thenReturn(this.mockTypeEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0.getSegment()).thenReturn(this.mockSectionEntityWithId0);
		Mockito.when(this.mockVariationEntityWithTypeWithId0WithSectionWithId0WithAdmissionRequirementWithId0.getAdmissionRequirement()).thenReturn(this.mockAdmissionRequirementEntityWithId0);

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
