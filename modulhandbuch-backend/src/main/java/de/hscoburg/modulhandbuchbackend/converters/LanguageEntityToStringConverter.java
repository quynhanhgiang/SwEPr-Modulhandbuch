package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;

public class LanguageEntityToStringConverter extends AbstractConverter<LanguageEntity, String> {
	
	@Override
	public String convert(LanguageEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
