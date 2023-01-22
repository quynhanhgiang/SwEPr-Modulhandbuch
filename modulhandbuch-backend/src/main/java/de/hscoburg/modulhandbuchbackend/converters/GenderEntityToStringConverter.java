package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;

/**
 * This class converts a {@link GenderEntity} to a {@link String}.
 */
public class GenderEntityToStringConverter extends AbstractConverter<GenderEntity, String> {

	/**
	 * This method converts a {@link GenderEntity} to a {@link String}.
	 * 
	 * @param source The {@link GenderEntity} to convert from.
	 * @return The value of the {@link GenderEntity}.
	 */
	@Override
	public String convert(GenderEntity source) {
		if (source == null) {
			return null;
		}

		return source.getValue();
	}
}
