package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

// TODO reduce redundancy with inheritance
@Converter
public class ModuleEntityDurationDatabaseConverter implements AttributeConverter<ModuleEntity.Duration, String> {

	@Override
	public String convertToDatabaseColumn(ModuleEntity.Duration attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.getText() : null;
	}

	@Override
	public ModuleEntity.Duration convertToEntityAttribute(String dbData) {
		return ModuleEntity.Duration.fromString(dbData);
	}
}