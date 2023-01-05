package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;
import de.hscoburg.modulhandbuchbackend.repositories.MaternityProtectionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringToMaternityProtectionEntityConverter extends AbstractConverter<String, MaternityProtectionEntity> {
	private final MaternityProtectionRepository maternityProtectionRepository;
	
	@Override
	public MaternityProtectionEntity convert(String source) {
		List<MaternityProtectionEntity> maternityProtectionList = this.maternityProtectionRepository.findByValue(source);
		if (maternityProtectionList.isEmpty()) {
			return null;
		}

		return maternityProtectionList.get(0);
	}
}
