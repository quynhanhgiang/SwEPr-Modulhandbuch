package de.hscoburg.modulhandbuchbackend.services;

import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import lombok.Data;

@Data
@Service
public class VariationService {
	private final AdmissionRequirementRepository admissionRequirementRepository;
	private final ModuleManualRepository moduleManualRepository;
	private final ModuleRepository moduleRepository;
	private final SectionRepository sectionRepository;
	private final TypeRepository typeRepository;
	
	public VariationEntity cleanEntity(VariationEntity variation) {
		if ((variation.getModule() == null) || (variation.getModule().getId() == null)) {
			return null;
		}

		Integer moduleId = variation.getModule().getId();
		variation.setModule(this.moduleRepository.findById(moduleId)
			.orElseThrow(() -> new ModuleNotFoundException(moduleId)));

		if ((variation.getModuleManual() == null) || variation.getModuleManual().getId() == null) {
			return null;
		}

		Integer moduleManualId = variation.getModuleManual().getId();
		this.moduleManualRepository.findById(moduleManualId)
			.orElseThrow(() -> new ModuleManualNotFoundException(moduleManualId));

		if ((variation.getType() != null) && (variation.getType().getId() != null)) {
			Integer typeId = variation.getType().getId();
			variation.setType(this.typeRepository.findById(typeId).orElse(null));
		} else {
			variation.setType(null);
		}

		if ((variation.getSection() != null) && (variation.getSection().getId() != null)) {
			Integer sectionId = variation.getSection().getId();
			variation.setSection(this.sectionRepository.findById(sectionId).orElse(null));
		} else {
			variation.setSection(null);
		}
		
		if ((variation.getAdmissionRequirement() != null) && (variation.getAdmissionRequirement().getId() != null)) {
			Integer admissionRequirementId = variation.getAdmissionRequirement().getId();
			variation.setAdmissionRequirement(this.admissionRequirementRepository.findById(admissionRequirementId).orElse(null));
		} else {
			variation.setAdmissionRequirement(null);
		}

		return variation;
	}
}
