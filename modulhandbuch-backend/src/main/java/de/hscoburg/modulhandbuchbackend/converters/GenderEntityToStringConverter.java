package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;

public class GenderEntityToStringConverter extends AbstractConverter<GenderEntity, String> {
	
	@Override
	public String convert(GenderEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
