package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

public interface VariationRepository extends JpaRepository<VariationEntity, Integer> {
}
