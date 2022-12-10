package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;

// TODO reduce redundancy with inheritance
@Converter
public class SpoEntityDegreeDatabaseConverter implements AttributeConverter<SpoEntity.Degree, String> {
	
	@Override
	public String convertToDatabaseColumn(SpoEntity.Degree attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.toString() : null;
	}

	@Override
	public SpoEntity.Degree convertToEntityAttribute(String dbData) {
		return SpoEntity.Degree.fromString(dbData);
	}
}
