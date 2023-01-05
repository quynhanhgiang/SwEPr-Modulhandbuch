package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;

public class AdmissionRequirementEntityToStringConverter extends AbstractConverter<AdmissionRequirementEntity, String> {
	
	@Override
	public String convert(AdmissionRequirementEntity source) {
		if (source == null) {
			return null;
		}

		return source.getValue();
	}
}
