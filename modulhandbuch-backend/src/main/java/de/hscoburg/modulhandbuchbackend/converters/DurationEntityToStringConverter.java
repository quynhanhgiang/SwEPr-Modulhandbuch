package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;

/**
 * This class converts a {@link DurationEntity} to a {@link String}.
 */
public class DurationEntityToStringConverter extends AbstractConverter<DurationEntity, String> {
	
	/**
	 * This method converts a {@link DurationEntity} to a {@link String}.
	 * 
	 * @param source The {@link DurationEntity} to convert from.
	 * @return The value of the {@link DurationEntity}.
	 */
	@Override
	public String convert(DurationEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
