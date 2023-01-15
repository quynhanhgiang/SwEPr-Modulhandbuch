package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;

public class MaternityProtectionEntityToStringConverter extends AbstractConverter<MaternityProtectionEntity, String> {
	
	@Override
	public String convert(MaternityProtectionEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
