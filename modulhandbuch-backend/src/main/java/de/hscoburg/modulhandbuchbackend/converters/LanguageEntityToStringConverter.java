package de.hscoburg.modulhandbuchbackend.converters;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;

/**
 * This class converts a {@link LanguageEntity} to a {@link String}.
 */
public class LanguageEntityToStringConverter extends AbstractConverter<LanguageEntity, String> {

	/**
	 * This method converts a {@link LanguageEntity} to a {@link String}.
	 * 
	 * @param source The {@link LanguageEntity} to convert from.
	 * @return The value of the {@link LanguageEntity}.
	 */
	@Override
	public String convert(LanguageEntity source) {
		if (source == null) {
			return null;
		}

		return source.getValue();
	}
}
