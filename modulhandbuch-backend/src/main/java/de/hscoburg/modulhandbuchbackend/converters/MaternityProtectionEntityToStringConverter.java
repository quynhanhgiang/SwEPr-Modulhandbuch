package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;

/**
 * This class converts a {@link MaternityProtectionEntity} to a {@link String}.
 */
public class MaternityProtectionEntityToStringConverter extends AbstractConverter<MaternityProtectionEntity, String> {
	
	/**
	 * This method converts a {@link MaternityProtectionEntity} to a {@link String}.
	 * 
	 * @param source The {@link MaternityProtectionEntity} to convert from.
	 * @return The value of the {@link MaternityProtectionEntity}.
	 */
	@Override
	public String convert(MaternityProtectionEntity source) {
		if (source == null) {
			return null;
		}
		
		return source.getValue();
	}
}
