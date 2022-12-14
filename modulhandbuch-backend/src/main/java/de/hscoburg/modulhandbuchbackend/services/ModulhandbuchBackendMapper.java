package de.hscoburg.modulhandbuchbackend.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.converters.CollegeEmployeeEntityGenderStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityCycleStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityDurationStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityLanguageStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityMaternityProtectionStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.SpoEntityDegreeStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.VariationEntityCategoryStringConverter;

@Service
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
