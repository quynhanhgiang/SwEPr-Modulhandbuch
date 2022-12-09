package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

public class ModuleEntityCycleStringConverter extends AbstractConverter<String, ModuleEntity.Cycle> {
	
	@Override
	public ModuleEntity.Cycle convert(String source) {
		return source == null ? null : ModuleEntity.Cycle.fromString(source);
	}
}
