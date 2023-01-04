package de.hscoburg.modulhandbuchbackend.model.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ModuleManualEnumEntity<T extends ModuleManualEnumEntity<T>> extends EnumEntity<T> {
	public abstract ModuleManualEntity getModuleManual();
	public abstract void setModuleManual(ModuleManualEntity moduleManual);
}
