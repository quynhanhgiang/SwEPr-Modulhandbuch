package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.ModuleEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
}
