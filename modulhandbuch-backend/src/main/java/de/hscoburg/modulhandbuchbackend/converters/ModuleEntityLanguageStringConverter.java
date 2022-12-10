package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

public class ModuleEntityLanguageStringConverter extends AbstractConverter<String, ModuleEntity.Language> {
	
	@Override
	public ModuleEntity.Language convert(String source) {
		return source == null ? null : ModuleEntity.Language.fromString(source);
	}
}
