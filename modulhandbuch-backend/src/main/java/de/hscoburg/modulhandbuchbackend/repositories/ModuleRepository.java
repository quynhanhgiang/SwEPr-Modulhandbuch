package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
	List<ModuleEntity> findByModuleManual(ModuleManualEntity moduleManual);
	List<ModuleEntity> findByModuleManualNot(ModuleManualEntity moduleManual);
}
