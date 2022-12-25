package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.EnumEntity;

public class EnumEntityToStringConverter extends AbstractConverter<EnumEntity<?>, String> {
	
	@Override
	public String convert(EnumEntity<?> source) {
		return source.getValue();
	}
}
