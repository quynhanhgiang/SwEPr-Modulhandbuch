package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

public class ModuleEntityMaternityProtectionStringConverter extends AbstractConverter<String, ModuleEntity.MaternityProtection> {
	
	@Override
	public ModuleEntity.MaternityProtection convert(String source) {
		return source == null ? null : ModuleEntity.MaternityProtection.fromString(source);
	}
}
