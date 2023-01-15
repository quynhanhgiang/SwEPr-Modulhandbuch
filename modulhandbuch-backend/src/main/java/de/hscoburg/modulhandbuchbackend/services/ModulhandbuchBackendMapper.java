package de.hscoburg.modulhandbuchbackend.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.converters.AdmissionRequirementEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.CycleEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.DegreeEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.DurationEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.GenderEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.LanguageEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.MaternityProtectionEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToAdmissionRequirementEntityConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToCycleEntityConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToDegreeEntityConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToDurationEntityConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToGenderEntityConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToLanguageEntityConverter;
import de.hscoburg.modulhandbuchbackend.converters.StringToMaternityProtectionEntityConverter;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.CycleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.DegreeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.DurationRepository;
import de.hscoburg.modulhandbuchbackend.repositories.GenderRepository;
import de.hscoburg.modulhandbuchbackend.repositories.LanguageRepository;
import de.hscoburg.modulhandbuchbackend.repositories.MaternityProtectionRepository;

@Service
public class ModulhandbuchBackendMapper extends ModelMapper {

	public ModulhandbuchBackendMapper(AdmissionRequirementRepository admissionRequirementRepository, CycleRepository cycleRepository, DegreeRepository degreeRepository, DurationRepository durationRepository, GenderRepository genderRepository, LanguageRepository languageRepository, MaternityProtectionRepository maternityProtectionRepository) {
		super();
		super.addConverter(new AdmissionRequirementEntityToStringConverter());
		super.addConverter(new CycleEntityToStringConverter());
		super.addConverter(new DegreeEntityToStringConverter());
		super.addConverter(new DurationEntityToStringConverter());
		super.addConverter(new GenderEntityToStringConverter());
		super.addConverter(new LanguageEntityToStringConverter());
		super.addConverter(new MaternityProtectionEntityToStringConverter());

		super.addConverter(new StringToAdmissionRequirementEntityConverter(admissionRequirementRepository));
		super.addConverter(new StringToCycleEntityConverter(cycleRepository));
		super.addConverter(new StringToDegreeEntityConverter(degreeRepository));
		super.addConverter(new StringToDurationEntityConverter(durationRepository));
		super.addConverter(new StringToGenderEntityConverter(genderRepository));
		super.addConverter(new StringToLanguageEntityConverter(languageRepository));
		super.addConverter(new StringToMaternityProtectionEntityConverter(maternityProtectionRepository));
	}
}
