package de.hscoburg.modulhandbuchbackend.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;

// TODO reduce redundancy with inheritance
@Converter
public class CollegeEmployeeEntityGenderDatabaseConverter implements AttributeConverter<CollegeEmployeeEntity.Gender, String> {
	
	@Override
	public String convertToDatabaseColumn(CollegeEmployeeEntity.Gender attribute) {
		// TODO maybe remove check and make field notnull?
		return (attribute != null) ? attribute.toString() : null;
	}

	@Override
	public CollegeEmployeeEntity.Gender convertToEntityAttribute(String dbData) {
		return CollegeEmployeeEntity.Gender.fromString(dbData);
	}
}
