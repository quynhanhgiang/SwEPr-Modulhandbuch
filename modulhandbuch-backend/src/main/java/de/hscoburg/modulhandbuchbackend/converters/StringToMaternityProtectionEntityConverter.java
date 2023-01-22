package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;
import de.hscoburg.modulhandbuchbackend.repositories.MaternityProtectionRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class converts a {@link String} to a {@link MaternityProtectionEntity}.
 */
@RequiredArgsConstructor
public class StringToMaternityProtectionEntityConverter extends AbstractConverter<String, MaternityProtectionEntity> {
	private final MaternityProtectionRepository maternityProtectionRepository;
	
	/**
	 * This method converts a {@link String} to a {@link MaternityProtectionEntity}.
	 * 
	 * @param source The {@link String} to convert from.
	 * @return The correlated {@link MaternityProtectionEntity}.
	 */
	@Override
	public MaternityProtectionEntity convert(String source) {
		List<MaternityProtectionEntity> maternityProtectionList = this.maternityProtectionRepository.findByValue(source);
		if (maternityProtectionList.isEmpty()) {
			return null;
		}

		return maternityProtectionList.get(0);
	}
}
