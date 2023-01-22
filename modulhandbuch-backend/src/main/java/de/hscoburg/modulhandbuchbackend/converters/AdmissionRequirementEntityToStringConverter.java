package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;

/**
 * This class converts an {@link AdmissionRequirementEntity} to a {@link String}.
 */
public class AdmissionRequirementEntityToStringConverter extends AbstractConverter<AdmissionRequirementEntity, String> {
	
	/**
	 * This method converts an {@link AdmissionRequirementEntity} to a {@link String}.
	 * 
	 * @param source The {@link AdmissionRequirementEntity} to convert from.
	 * @return The value of the {@link AdmissionRequirementEntity}.
	 */
	@Override
	public String convert(AdmissionRequirementEntity source) {
		if (source == null) {
			return null;
		}

		return source.getValue();
	}
}
