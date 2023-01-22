package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class converts a {@link String} to an {@link AdmissionRequirementEntity}.
 */
@RequiredArgsConstructor
public class StringToAdmissionRequirementEntityConverter extends AbstractConverter<String, AdmissionRequirementEntity> {
	private final AdmissionRequirementRepository admissionRequirementRepository;

	/**
	 * This method converts a {@link String} to an {@link AdmissionRequirementEntity}.
	 * 
	 * @param source The {@link String} to convert from.
	 * @return The correlated {@link AdmissionRequirementEntity}.
	 */
	@Override
	public AdmissionRequirementEntity convert(String source) {
		List<AdmissionRequirementEntity> admissionRequirementList = this.admissionRequirementRepository.findByValue(source);
		if (admissionRequirementList.isEmpty()) {
			return null;
		}

		return admissionRequirementList.get(0);
	}
}
