package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.DurationRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class converts a {@link String} to a {@link DurationEntity}.
 */
@RequiredArgsConstructor
public class StringToDurationEntityConverter extends AbstractConverter<String, DurationEntity> {
	private final DurationRepository durationRepository;

	/**
	 * This method converts a {@link String} to a {@link DurationEntity}.
	 * 
	 * @param source The {@link String} to convert from.
	 * @return The correlated {@link DurationEntity}.
	 */
	@Override
	public DurationEntity convert(String source) {
		List<DurationEntity> durationList = this.durationRepository.findByValue(source);
		if (durationList.isEmpty()) {
			return null;
		}

		return durationList.get(0);
	}
}
