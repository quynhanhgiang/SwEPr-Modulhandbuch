package de.hscoburg.modulhandbuchbackend.converters;

import java.util.List;

import org.modelmapper.AbstractConverter;

import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.DurationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringToDurationEntityConverter extends AbstractConverter<String, DurationEntity> {
	private final DurationRepository durationRepository;
	
	@Override
	public DurationEntity convert(String source) {
		List<DurationEntity> durationList = this.durationRepository.findByValue(source);
		if (durationList.isEmpty()) {
			return null;
		}

		return durationList.get(0);
	}
}
