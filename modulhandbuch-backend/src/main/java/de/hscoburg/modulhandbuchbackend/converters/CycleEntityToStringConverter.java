package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;

/**
 * This class converts a {@link CycleEntity} to a {@link String}.
 */
public class CycleEntityToStringConverter extends AbstractConverter<CycleEntity, String> {
	
	/**
	 * This method converts a {@link CycleEntity} to a {@link String}.
	 * 
	 * @param source The {@link CycleEntity} to convert from.
	 * @return The value of the {@link CycleEntity}.
	 */
	@Override
	public String convert(CycleEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
