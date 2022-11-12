package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

// TODO reduce redundancy with inheritance
@Converter
public class VariationEntityCategoryDatabaseConverter implements AttributeConverter<VariationEntity.Category, String> {

	@Override
	public String convertToDatabaseColumn(VariationEntity.Category attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.getText() : null;
	}

	@Override
	public VariationEntity.Category convertToEntityAttribute(String dbData) {
		return VariationEntity.Category.fromString(dbData);
	}
}
