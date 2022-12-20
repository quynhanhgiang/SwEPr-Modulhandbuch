package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;

public interface StructureRepository<T> extends JpaRepository<T, Integer> {
	boolean existsByIdAndModuleManual(Integer id, ModuleManualEntity moduleManual);
}
