package de.hscoburg.modulhandbuchbackend.model.entities;

public interface StructureEntity<T extends StructureEntity<T>> {
	Integer getId();
	T getNext();
	void setNext(T next);
	void setModuleManual(ModuleManualEntity moduleManual);
}
