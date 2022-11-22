package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;

public interface ModuleManualRepository extends JpaRepository<ModuleManualEntity, Integer> {
}
