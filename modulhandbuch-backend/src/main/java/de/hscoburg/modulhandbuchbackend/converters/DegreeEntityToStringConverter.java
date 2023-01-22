package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DegreeEntity;

/**
 * This class converts a {@link DegreeEntity} to a {@link String}.
 */
public class DegreeEntityToStringConverter extends AbstractConverter<DegreeEntity, String> {

	/**
	 * This method converts a {@link DegreeEntity} to a {@link String}.
	 * 
	 * @param source The {@link DegreeEntity} to convert from.
	 * @return The value of the {@link DegreeEntity}.
	 */
	@Override
	public String convert(DegreeEntity source) {
		if (source == null) {
			return null;
		}

		return source.getValue();
	}
}
