package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;
import de.hscoburg.modulhandbuchbackend.repositories.GenderRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class converts a {@link String} to a {@link GenderEntity}.
 */
@RequiredArgsConstructor
public class StringToGenderEntityConverter extends AbstractConverter<String, GenderEntity> {
	private final GenderRepository genderRepository;

	/**
	 * This method converts a {@link String} to a {@link GenderEntity}.
	 * 
	 * @param source The {@link String} to convert from.
	 * @return The correlated {@link GenderEntity}.
	 */
	@Override
	public GenderEntity convert(String source) {
		List<GenderEntity> genderList = this.genderRepository.findByValue(source);
		if (genderList.isEmpty()) {
			return null;
		}

		return genderList.get(0);
	}
}
