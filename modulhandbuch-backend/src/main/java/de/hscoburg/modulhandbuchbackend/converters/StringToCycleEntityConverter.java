package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CycleRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class converts a {@link String} to a {@link CycleEntity}.
 */
@RequiredArgsConstructor
public class StringToCycleEntityConverter extends AbstractConverter<String, CycleEntity> {
	private final CycleRepository cycleRepository;
	
	/**
	 * This method converts a {@link String} to a {@link CycleEntity}.
	 * 
	 * @param source The {@link String} to convert from.
	 * @return The correlated {@link CycleEntity}.
	 */
	@Override
	public CycleEntity convert(String source) {
		List<CycleEntity> cycleList = this.cycleRepository.findByValue(source);
		if (cycleList.isEmpty()) {
			return null;
		}

		return cycleList.get(0);
	}
}
