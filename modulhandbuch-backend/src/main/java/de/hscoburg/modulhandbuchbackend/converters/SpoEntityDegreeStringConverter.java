package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;

public class SpoEntityDegreeStringConverter extends AbstractConverter<String, SpoEntity.Degree> {
	
	@Override
	public SpoEntity.Degree convert(String source) {
		return source == null ? null : SpoEntity.Degree.fromString(source);
	}
}
