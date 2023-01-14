package de.hscoburg.modulhandbuchbackend.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.EnumDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.DegreeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.EnumEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.CycleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.DegreeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.DurationRepository;
import de.hscoburg.modulhandbuchbackend.repositories.EnumRepository;
import de.hscoburg.modulhandbuchbackend.repositories.GenderRepository;
import de.hscoburg.modulhandbuchbackend.repositories.LanguageRepository;
import de.hscoburg.modulhandbuchbackend.repositories.MaternityProtectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import lombok.Data;

@Data
@Service
public class EnumService {
	private final CollegeEmployeeRepository collegeEmployeeRepository;
	private final CycleRepository cycleRepository;
	private final DegreeRepository degreeRepository;
	private final DurationRepository durationRepository;
	private final GenderRepository genderRepository;
	private final LanguageRepository languageRepository;
	private final MaternityProtectionRepository maternityProtectionRepository;
	private final ModuleRepository moduleRepository;
	private final SpoRepository spoRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	private <T extends EnumEntity<T>> Set<String> getAllAsStringSet(EnumRepository<T> repository) {
		return repository.findAll().stream()
			.map(element -> this.modulhandbuchBackendMapper.map(element, String.class))
			.collect(Collectors.toSet());
	}

	public Set<String> allCycles() {
		return this.getAllAsStringSet(this.cycleRepository);
	}

	public Set<String> allDegrees() {
		return this.getAllAsStringSet(this.degreeRepository);
	}

	public Set<String> allDurations() {
		return this.getAllAsStringSet(this.durationRepository);
	}

	public Set<String> allGenders() {
		return this.getAllAsStringSet(this.genderRepository);
	}

	public Set<String> allLanguages() {
		return this.getAllAsStringSet(this.languageRepository);
	}
	
	public Set<String> allMaternityProtections() {
		return this.getAllAsStringSet(this.maternityProtectionRepository);
	}

	private EnumDTO getEnumDTOFromString(String value) {
		EnumDTO enumDTO = new EnumDTO();
		enumDTO.setValue(value);
		return enumDTO;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private <T extends EnumEntity<T>> Set<String> replaceEnum(Set<String> newEnum, EnumRepository<T> enumRepository, Map<Class<?>, JpaRepository> repositories, Map<Class<?>, Function<Object, T>> getEnums, Map<Class<?>, BiConsumer<Object, T>> setEnums, Class<T> enumEntityClass) {
		// remove all null values in given list
		newEnum.removeIf(element -> (element == null));

		// remove all mappings if mapped value is not present anymore
		for (Entry<Class<?>, Function<Object, T>> getEnumEntry : getEnums.entrySet()) {
			Class<?> key = getEnumEntry.getKey();
			JpaRepository repository = repositories.get(key);
			Function<Object, T> getEnumElement = getEnumEntry.getValue();
			BiConsumer<Object, T> setEnumElement = setEnums.get(key);

			List<?> entities = repository.findAll();
			
			entities.stream()
				// filter enum entities for deletion
				.filter(entity -> !newEnum.contains(getEnumElement.apply(entity).getValue()))
				// remove mapping for enum element
				.peek(entity -> setEnumElement.accept(entity, null))
				// TODO error handling: throw custom exception with repository.getClass().getSimpleName() and getEnumElement.apply(entity).getValue() if entity cannot be saved due to null constraint
				.forEach(entity -> repository.save(entity));
		}

		// delete elements in database which are not in new enum
		List<T> enumEntities = enumRepository.findAll();
		for (T enumElement : enumEntities) {
			if (!newEnum.contains(enumElement.getValue())) {
				enumRepository.delete(enumElement);
			}
		}
		
		// save all new enum elements
		newEnum.stream()
			.filter(element -> !enumRepository.existsByValue(element))
			.map(element -> this.getEnumDTOFromString(element))
			.map(element -> this.modulhandbuchBackendMapper.map(element, enumEntityClass))
			.forEach(element -> enumRepository.save(element));

		return newEnum;
	}

	@SuppressWarnings("rawtypes")
	public Set<String> replaceCycles(Set<String> newCycles) {
		Map<Class<?>, JpaRepository> repositories = Map.of(
			ModuleEntity.class, this.moduleRepository
		);
		Map<Class<?>, Function<Object, CycleEntity>> getCycles = Map.of(
			ModuleEntity.class, module -> ((ModuleEntity) module).getCycle()
		);
		Map<Class<?>, BiConsumer<Object, CycleEntity>> setCycles = Map.of(
			ModuleEntity.class, (module, cycle) -> ((ModuleEntity) module).setCycle(cycle)
		);

		return this.replaceEnum(newCycles, this.cycleRepository, repositories, getCycles, setCycles, CycleEntity.class);
	}

	@SuppressWarnings("rawtypes")
	public Set<String> replaceDegrees(Set<String> newDegrees) {
		Map<Class<?>, JpaRepository> repositories = Map.of(
			SpoEntity.class, this.spoRepository
		);
		Map<Class<?>, Function<Object, DegreeEntity>> getDegrees = Map.of(
			SpoEntity.class, spo -> ((SpoEntity) spo).getDegree()
		);
		Map<Class<?>, BiConsumer<Object, DegreeEntity>> setDegrees = Map.of(
			SpoEntity.class, (spo, degree) -> ((SpoEntity) spo).setDegree(degree)
		);

		return this.replaceEnum(newDegrees, this.degreeRepository, repositories, getDegrees, setDegrees, DegreeEntity.class);
	}

	@SuppressWarnings("rawtypes")
	public Set<String> replaceDuration(Set<String> newDurations) {
		Map<Class<?>, JpaRepository> repositories = Map.of(
			ModuleEntity.class, this.moduleRepository
		);
		Map<Class<?>, Function<Object, DurationEntity>> getDurations = Map.of(
			ModuleEntity.class, module -> ((ModuleEntity) module).getDuration()
		);
		Map<Class<?>, BiConsumer<Object, DurationEntity>> setDurations = Map.of(
			ModuleEntity.class, (module, duration) -> ((ModuleEntity) module).setDuration(duration)
		);

		return this.replaceEnum(newDurations, this.durationRepository, repositories, getDurations, setDurations, DurationEntity.class);
	}

	@SuppressWarnings("rawtypes")
	public Set<String> replaceGenders(Set<String> newGenders) {
		Map<Class<?>, JpaRepository> repositories = Map.of(
			CollegeEmployeeEntity.class, this.collegeEmployeeRepository
		);
		Map<Class<?>, Function<Object, GenderEntity>> getGenders = Map.of(
			CollegeEmployeeEntity.class, collegeEmployee -> ((CollegeEmployeeEntity) collegeEmployee).getGender()
		);
		Map<Class<?>, BiConsumer<Object, GenderEntity>> setGenders = Map.of(
			CollegeEmployeeEntity.class, (collegeEmployee, gender) -> ((CollegeEmployeeEntity) collegeEmployee).setGender(gender)
		);

		return this.replaceEnum(newGenders, this.genderRepository, repositories, getGenders, setGenders, GenderEntity.class);
	}

	@SuppressWarnings("rawtypes")
	public Set<String> replaceLanguages(Set<String> newLanguages) {
		Map<Class<?>, JpaRepository> repositories = Map.of(
			ModuleEntity.class, this.moduleRepository
		);
		Map<Class<?>, Function<Object, LanguageEntity>> getLanguages = Map.of(
			ModuleEntity.class, module -> ((ModuleEntity) module).getLanguage()
		);
		Map<Class<?>, BiConsumer<Object, LanguageEntity>> setLanguages = Map.of(
			ModuleEntity.class, (module, language) -> ((ModuleEntity) module).setLanguage(language)
		);

		return this.replaceEnum(newLanguages, this.languageRepository, repositories, getLanguages, setLanguages, LanguageEntity.class);
	}

	@SuppressWarnings("rawtypes")
	public Set<String> replaceMaternityProtections(Set<String> newMaternityProtections) {
		Map<Class<?>, JpaRepository> repositories = Map.of(
			ModuleEntity.class, this.moduleRepository
		);
		Map<Class<?>, Function<Object, MaternityProtectionEntity>> getMaternityProtections = Map.of(
			ModuleEntity.class, module -> ((ModuleEntity) module).getMaternityProtection()
		);
		Map<Class<?>, BiConsumer<Object, MaternityProtectionEntity>> setMaternityProtections = Map.of(
			ModuleEntity.class, (module, maternityProtection) -> ((ModuleEntity) module).setMaternityProtection(maternityProtection)
		);

		return this.replaceEnum(newMaternityProtections, this.maternityProtectionRepository, repositories, getMaternityProtections, setMaternityProtections, MaternityProtectionEntity.class);
	}
}
