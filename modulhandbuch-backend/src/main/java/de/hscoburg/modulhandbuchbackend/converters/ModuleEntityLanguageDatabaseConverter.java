package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;

// TODO reduce redundancy with inheritance
@Converter
public class ModuleEntityLanguageDatabaseConverter implements AttributeConverter<ModuleEntity.Language, String> {

	@Override
	public String convertToDatabaseColumn(ModuleEntity.Language attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.getText() : null;
	}

	@Override
	public ModuleEntity.Language convertToEntityAttribute(String dbData) {
		return ModuleEntity.Language.fromString(dbData);
	}
}
