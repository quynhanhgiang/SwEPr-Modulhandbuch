package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;
import de.hscoburg.modulhandbuchbackend.repositories.LanguageRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class converts a {@link String} to a {@link LanguageEntity}.
 */
@RequiredArgsConstructor
public class StringToLanguageEntityConverter extends AbstractConverter<String, LanguageEntity> {
	private final LanguageRepository languageRepository;
	
	/**
	 * This method converts a {@link String} to a {@link LanguageEntity}.
	 * 
	 * @param source The {@link String} to convert from.
	 * @return The correlated {@link LanguageEntity}.
	 */
	@Override
	public LanguageEntity convert(String source) {
		List<LanguageEntity> languageList = this.languageRepository.findByValue(source);
		if (languageList.isEmpty()) {
			return null;
		}

		return languageList.get(0);
	}
}
