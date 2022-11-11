package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

// TODO reduce redundancy with inheritance
@Converter
public class ModuleEntityCycleDatabaseConverter implements AttributeConverter<ModuleEntity.Cycle, String> {

	@Override
	public String convertToDatabaseColumn(ModuleEntity.Cycle attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.toString() : null;
	}

	@Override
	public ModuleEntity.Cycle convertToEntityAttribute(String dbData) {
		return ModuleEntity.Cycle.fromString(dbData);
	}
}
