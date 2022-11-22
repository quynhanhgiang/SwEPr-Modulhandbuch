package de.hscoburg.modulhandbuchbackend.mappers;

import org.modelmapper.ModelMapper;

import de.hscoburg.modulhandbuchbackend.converters.CollegeEmployeeEntityGenderStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityCycleStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityDurationStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityLanguageStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityMaternityProtectionStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.SpoEntityDegreeStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.VariationEntityCategoryStringConverter;

public class ModulhandbuchBackendMapper extends ModelMapper {
	
	public ModulhandbuchBackendMapper() {
		super();
		super.addConverter(new CollegeEmployeeEntityGenderStringConverter());
		super.addConverter(new ModuleEntityCycleStringConverter());
		super.addConverter(new ModuleEntityDurationStringConverter());
		super.addConverter(new ModuleEntityMaternityProtectionStringConverter());
		super.addConverter(new ModuleEntityLanguageStringConverter());
		super.addConverter(new SpoEntityDegreeStringConverter());
		super.addConverter(new VariationEntityCategoryStringConverter());
	}
}
