package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

public interface VariationRepository extends JpaRepository<VariationEntity, Integer> {
	List<VariationEntity> findBySpoAndModuleAndSemester(SpoEntity spo, ModuleEntity module, Integer semester);
}
