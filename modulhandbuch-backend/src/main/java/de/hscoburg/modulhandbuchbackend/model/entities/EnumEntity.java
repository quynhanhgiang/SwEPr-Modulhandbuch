package de.hscoburg.modulhandbuchbackend.model.entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EnumEntity<T extends EnumEntity<T>> {
	public abstract Integer getId();
	public abstract void setId(Integer id);
	
	public abstract String getValue();
	public abstract void setValue(String value);
}
