package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.SectionEntity;

public interface SectionRepository extends JpaRepository<SectionEntity, Integer> {
}
