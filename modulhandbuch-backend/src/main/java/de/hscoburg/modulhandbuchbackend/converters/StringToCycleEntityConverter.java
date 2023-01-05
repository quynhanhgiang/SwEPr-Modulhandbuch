package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CycleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringToCycleEntityConverter extends AbstractConverter<String, CycleEntity> {
	private final CycleRepository cycleRepository;
	
	@Override
	public CycleEntity convert(String source) {
		List<CycleEntity> cycleList = this.cycleRepository.findByValue(source);
		if (cycleList.isEmpty()) {
			return null;
		}

		return cycleList.get(0);
	}
}
