package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;

public class CollegeEmployeeEntityGenderStringConverter extends AbstractConverter<String, CollegeEmployeeEntity.Gender> {
	
	@Override
	public CollegeEmployeeEntity.Gender convert(String source) {
		return source == null ? null : CollegeEmployeeEntity.Gender.fromString(source);
	}
}
