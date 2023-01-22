package de.hscoburg.modulhandbuchbackend.model.entities;

import javax.persistence.MappedSuperclass;

/**
 * This class provides a common base for database tables representing a structure.
 */
@MappedSuperclass
public abstract class StructureEntity<T extends StructureEntity<T>> {
	public abstract Integer getId();
	public abstract void setId(Integer id);

	public abstract T getNext();
	public abstract void setNext(T next);

	public abstract ModuleManualEntity getModuleManual();
	public abstract void setModuleManual(ModuleManualEntity moduleManual);
	
	public abstract String getValue();
	public abstract void setValue(String value);
}
