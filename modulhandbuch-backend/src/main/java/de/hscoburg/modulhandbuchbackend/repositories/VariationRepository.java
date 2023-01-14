package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

public interface VariationRepository extends JpaRepository<VariationEntity, Integer> {
	List<VariationEntity> findByModuleManual(ModuleManualEntity moduleManual);
	List<VariationEntity> findByModuleManualNot(ModuleManualEntity moduleManual);
	List<VariationEntity> findBySegmentAndModuleType(SectionEntity segment, TypeEntity type);
}
