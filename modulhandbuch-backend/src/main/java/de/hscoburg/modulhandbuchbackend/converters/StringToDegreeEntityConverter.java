package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DegreeEntity;
import de.hscoburg.modulhandbuchbackend.repositories.DegreeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringToDegreeEntityConverter extends AbstractConverter<String, DegreeEntity> {
	private final DegreeRepository degreeRepository;
	
	@Override
	public DegreeEntity convert(String source) {
		List<DegreeEntity> degreeList = this.degreeRepository.findByValue(source);
		if (degreeList.isEmpty()) {
			return null;
		}

		return degreeList.get(0);
	}
}
