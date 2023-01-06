package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DegreeEntity;

public class DegreeEntityToStringConverter extends AbstractConverter<DegreeEntity, String> {
	
	@Override
	public String convert(DegreeEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
