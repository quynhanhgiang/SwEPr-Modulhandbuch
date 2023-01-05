package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;

public class DurationEntityToStringConverter extends AbstractConverter<DurationEntity, String> {
	
	@Override
	public String convert(DurationEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
