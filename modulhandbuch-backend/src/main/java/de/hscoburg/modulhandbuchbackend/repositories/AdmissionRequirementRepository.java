package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;

public interface AdmissionRequirementRepository extends JpaRepository<AdmissionRequirementEntity, Integer> {
	List<AdmissionRequirementEntity> findByModuleManual(ModuleManualEntity moduleManual);
	List<AdmissionRequirementEntity> deleteByModuleManual(ModuleManualEntity moduleManual);
}
