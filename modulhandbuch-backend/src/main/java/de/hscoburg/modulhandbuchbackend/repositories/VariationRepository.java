package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SegmentEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleTypeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

/**
 * This class is the interface for the database communication regarding {@link VariationEntity}.
 */
public interface VariationRepository extends JpaRepository<VariationEntity, Integer> {
	List<VariationEntity> findByModule(ModuleEntity module);

	List<VariationEntity> findByModuleManual(ModuleManualEntity moduleManual);
	List<VariationEntity> findByModuleManualNot(ModuleManualEntity moduleManual);
	@Transactional
	List<VariationEntity> deleteByModuleManual(ModuleManualEntity moduleManual);

	List<VariationEntity> findBySegment(SegmentEntity segment);
	List<VariationEntity> findByModuleType(ModuleTypeEntity moduleType);
	List<VariationEntity> findByAdmissionRequirement(AdmissionRequirementEntity admissionRequirement);
}
