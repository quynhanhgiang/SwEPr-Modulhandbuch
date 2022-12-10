package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

// TODO reduce redundancy with inheritance
@Converter
public class ModuleEntityMaternityProtectionDatabaseConverter implements AttributeConverter<ModuleEntity.MaternityProtection, String> {

	@Override
	public String convertToDatabaseColumn(ModuleEntity.MaternityProtection attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.toString() : null;
	}

	@Override
	public ModuleEntity.MaternityProtection convertToEntityAttribute(String dbData) {
		return ModuleEntity.MaternityProtection.fromString(dbData);
	}
}
