package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

public class ModuleEntityDurationStringConverter extends AbstractConverter<String, ModuleEntity.Duration> {
	
	@Override
	public ModuleEntity.Duration convert(String source) {
		return source == null ? null : ModuleEntity.Duration.fromString(source);
	}
}
