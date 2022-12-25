package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.repository.NoRepositoryBean;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEnumEntity;

@NoRepositoryBean
public interface ModuleManualEnumRepository<T extends ModuleManualEnumEntity<T>> extends EnumRepository<T> {
	boolean existsByIdAndModuleManual(Integer id, ModuleManualEntity moduleManual);
}
