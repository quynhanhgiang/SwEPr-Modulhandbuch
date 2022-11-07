package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.ModuleEntity;

@Converter
public class ModuleEntityCycleConverter implements AttributeConverter<ModuleEntity.Cycle, String> {

	@Override
	public String convertToDatabaseColumn(ModuleEntity.Cycle attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.getText() : null;
	}

	@Override
	public ModuleEntity.Cycle convertToEntityAttribute(String dbData) {
		return ModuleEntity.Cycle.get(dbData);
	}
}
