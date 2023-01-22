package de.hscoburg.modulhandbuchbackend.services;

import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SegmentRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleTypeRepository;
import lombok.Data;

/**
 * This class is a service for recuring tasks regarding {@link VariationEntity}.
 */
@Data
@Service
public class VariationService {
	private final AdmissionRequirementRepository admissionRequirementRepository;
	private final ModuleManualRepository moduleManualRepository;
	private final ModuleRepository moduleRepository;
	private final SegmentRepository segmentRepository;
	private final ModuleTypeRepository moduleTypeRepository;
	
	/**
	 * This method is used for bringing a variation in a consistent state.
	 * 
	 * @param variation The {@link VariationEntity} to be cleaned.
	 * @return The cleaned {@link VariationEntity}.
	 */
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

		if ((variation.getModuleType() != null) && (variation.getModuleType().getId() != null)) {
			Integer typeId = variation.getModuleType().getId();
			variation.setModuleType(this.moduleTypeRepository.findById(typeId).orElse(null));
		} else {
			variation.setModuleType(null);
		}

		if ((variation.getSegment() != null) && (variation.getSegment().getId() != null)) {
			Integer segmentId = variation.getSegment().getId();
			variation.setSegment(this.segmentRepository.findById(segmentId).orElse(null));
		} else {
			variation.setSegment(null);
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
