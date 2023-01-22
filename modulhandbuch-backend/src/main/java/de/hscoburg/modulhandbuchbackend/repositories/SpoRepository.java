package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;

/**
 * This class is the interface for the database communication regarding {@link SpoEntity}.
 */
public interface SpoRepository extends JpaRepository<SpoEntity, Integer> {
}
