package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

/**
 * This class is the interface for the database communication regarding {@link ModuleEntity}.
 */
public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
}
