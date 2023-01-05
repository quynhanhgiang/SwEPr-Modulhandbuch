package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;
import de.hscoburg.modulhandbuchbackend.repositories.GenderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringToGenderEntityConverter extends AbstractConverter<String, GenderEntity> {
	private final GenderRepository genderRepository;
	
	@Override
	public GenderEntity convert(String source) {
		List<GenderEntity> genderList = this.genderRepository.findByValue(source);
		if (genderList.isEmpty()) {
			return null;
		}

		return genderList.get(0);
	}
}
