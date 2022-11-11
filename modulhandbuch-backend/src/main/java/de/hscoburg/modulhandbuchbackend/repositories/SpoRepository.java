package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;

public interface SpoRepository extends JpaRepository<SpoEntity, Integer> {
}
