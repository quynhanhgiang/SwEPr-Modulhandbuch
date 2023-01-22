package de.hscoburg.modulhandbuchbackend.model.entities;

import javax.persistence.MappedSuperclass;

/**
 * This class provides a common base for database tables representing an enum.
 */
@MappedSuperclass
public abstract class EnumEntity<T extends EnumEntity<T>> {
	public abstract Integer getId();
	public abstract void setId(Integer id);
	
	public abstract String getValue();
	public abstract void setValue(String value);
}
