package de.hscoburg.modulhandbuchbackend.repositories;

import de.hscoburg.modulhandbuchbackend.model.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationRepository extends JpaRepository<VariationEntity, Integer> {
    List<VariationEntity> findBySectionAndType(SectionEntity section, TypeEntity type);
}
