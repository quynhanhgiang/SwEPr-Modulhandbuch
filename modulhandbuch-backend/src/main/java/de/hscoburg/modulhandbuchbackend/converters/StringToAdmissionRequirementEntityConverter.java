package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringToAdmissionRequirementEntityConverter extends AbstractConverter<String, AdmissionRequirementEntity> {
	private final AdmissionRequirementRepository admissionRequirementRepository;
	
	@Override
	public AdmissionRequirementEntity convert(String source) {
		List<AdmissionRequirementEntity> admissionRequirementList = this.admissionRequirementRepository.findByValue(source);
		if (admissionRequirementList.isEmpty()) {
			return null;
		}

		return admissionRequirementList.get(0);
	}
}
