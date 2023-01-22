package de.hscoburg.modulhandbuchbackend.model.entities;

import javax.persistence.MappedSuperclass;

/**
 * This class provides a common base for database tables representing an enum for a module manual.
 */
@MappedSuperclass
public abstract class ModuleManualEnumEntity<T extends ModuleManualEnumEntity<T>> extends EnumEntity<T> {
	public abstract ModuleManualEntity getModuleManual();
	public abstract void setModuleManual(ModuleManualEntity moduleManual);
}
