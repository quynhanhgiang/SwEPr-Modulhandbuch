package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;

public interface AdmissionRequirementRepository extends ModuleManualEnumRepository<AdmissionRequirementEntity> {
	List<AdmissionRequirementEntity> findByModuleManual(ModuleManualEntity moduleManual);
	List<AdmissionRequirementEntity> deleteByModuleManual(ModuleManualEntity moduleManual);
}
