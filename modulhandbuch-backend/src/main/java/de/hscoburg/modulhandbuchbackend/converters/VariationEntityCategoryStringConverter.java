package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;

public class VariationEntityCategoryStringConverter extends AbstractConverter<String, VariationEntity.Category> {
	
	@Override
	public VariationEntity.Category convert(String source) {
		return source == null ? null : VariationEntity.Category.fromString(source);
	}
}
