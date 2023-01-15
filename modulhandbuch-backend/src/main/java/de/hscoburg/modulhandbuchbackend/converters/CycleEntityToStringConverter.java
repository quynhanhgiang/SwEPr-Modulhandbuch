package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;

public class CycleEntityToStringConverter extends AbstractConverter<CycleEntity, String> {
	
	@Override
	public String convert(CycleEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
