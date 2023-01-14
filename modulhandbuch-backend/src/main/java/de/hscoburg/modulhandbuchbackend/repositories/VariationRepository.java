package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

public interface VariationRepository extends JpaRepository<VariationEntity, Integer> {
	List<VariationEntity> findByModule(ModuleEntity module);

	List<VariationEntity> findByModuleManual(ModuleManualEntity moduleManual);
	List<VariationEntity> findByModuleManualNot(ModuleManualEntity moduleManual);
	@Transactional
	List<VariationEntity> deleteByModuleManual(ModuleManualEntity moduleManual);

	List<VariationEntity> findBySegment(SectionEntity segment);
	List<VariationEntity> findByModuleType(TypeEntity moduleType);
	List<VariationEntity> findBySegmentAndModuleType(SectionEntity segment, TypeEntity type);
	List<VariationEntity> findByAdmissionRequirement(AdmissionRequirementEntity admissionRequirement);
}
