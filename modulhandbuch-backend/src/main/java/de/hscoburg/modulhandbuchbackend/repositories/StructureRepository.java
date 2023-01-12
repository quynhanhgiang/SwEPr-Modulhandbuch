package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.StructureEntity;

@NoRepositoryBean
public interface StructureRepository<T extends StructureEntity<T>> extends JpaRepository<T, Integer> {
	List<T> findByModuleManual(ModuleManualEntity moduleManual);
	boolean existsByIdAndModuleManual(Integer id, ModuleManualEntity moduleManual);
}
