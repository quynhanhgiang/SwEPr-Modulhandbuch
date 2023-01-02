package de.hscoburg.modulhandbuchbackend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.EnumEntity;
import de.hscoburg.modulhandbuchbackend.repositories.EnumRepository;
import lombok.Data;

@Data
@Service
public class EnumService {
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;
	
	public <T extends EnumEntity<T>> T getEnumEntityFromString(String value, EnumRepository<T> repository) {
		List<T> enumList = repository.findByValue(value);
		if (enumList.isEmpty()) {
			return null;
		}

		return enumList.get(0);
	}

	public EnumDTO getEnumDTOFromString(String value) {
		EnumDTO enumDTO = new EnumDTO();
		enumDTO.setValue(value);
		return enumDTO;
	}

	public <T extends EnumEntity<T>> List<String> getAllAsStringList(EnumRepository<T> repository) {
		return repository.findAll().stream()
			.map(element -> this.modulhandbuchBackendMapper.map(element, String.class))
			.collect(Collectors.toList());
	}

	public <T extends EnumEntity<T>> List<String> replaceEnum(List<String> newEnum, Class<T> enumEntityClass, EnumRepository<T> repository) {
		// remove all null values in given list
		newEnum.removeIf(element -> (element == null));

		repository.deleteAll();
		
		newEnum.stream()
			.map(enumName -> this.getEnumDTOFromString(enumName))
			.forEach(enumDTO -> {
				T enumEntity = this.modulhandbuchBackendMapper.map(enumDTO, enumEntityClass);
				repository.save(enumEntity);
			});

		return newEnum;
	}
}
