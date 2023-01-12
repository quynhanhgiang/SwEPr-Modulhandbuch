package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.NoRepositoryBean;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEnumEntity;

@NoRepositoryBean
public interface ModuleManualEnumRepository<T extends ModuleManualEnumEntity<T>> extends EnumRepository<T> {
	List<T> findByModuleManual(ModuleManualEntity moduleManual);
	@Transactional
	List<T> deleteByModuleManual(ModuleManualEntity moduleManual);
	boolean existsByIdAndModuleManual(Integer id, ModuleManualEntity moduleManual);
}
