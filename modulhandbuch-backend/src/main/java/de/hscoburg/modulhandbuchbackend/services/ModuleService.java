package de.hscoburg.modulhandbuchbackend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.dto.ModuleFullDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.CollegeEmployeeNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleOwnerRequiredException;
import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import lombok.Data;

/**
 * This class is a service for recuring tasks regarding {@link ModuleEntity} and {@link de.hscoburg.modulhandbuchbackend.dto.ModuleDTO}.
 */
@Data
@Service
public class ModuleService {
	private final CollegeEmployeeRepository collegeEmployeeRepository;
	private final ModuleRepository moduleRepository;
	private final VariationRepository variationRepository;
	private final VariationService variationService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	/**
	 * This method is used for saving a module and its variations.
	 * 
	 * @param moduleToSave The module to save.
	 * @return A saved module.
	 */
	public ModuleFullDTO saveModule(ModuleFullDTO moduleToSave) {
		ModuleEntity moduleEntity = this.modulhandbuchBackendMapper.map(moduleToSave, ModuleEntity.class);
		List<VariationEntity> newVariations = moduleEntity.getVariations();
		moduleEntity.setVariations(null);

		moduleEntity = this.cleanEntity(moduleEntity);

		ModuleEntity result = this.moduleRepository.save(moduleEntity);

		if (newVariations != null) {
			List<VariationEntity> newVariationsCleaned = newVariations.stream()
				.peek(variation -> variation.setModule(result))
				.map(variationEntity -> this.variationService.cleanEntity(variationEntity))
				.filter(variationEntity -> (variationEntity != null))
				.map(variation -> this.variationRepository.save(variation))
				.collect(Collectors.toList());

			result.setVariations(newVariationsCleaned);
		}

		return modulhandbuchBackendMapper.map(result, ModuleFullDTO.class);
	}

	/**
	 * This method is used for bringing a module in a consistent state.
	 * 
	 * @param module The {@link ModuleEntity} to be cleaned.
	 * @return The cleaned {@link ModuleEntity}.
	 */
	public ModuleEntity cleanEntity(ModuleEntity module) {
		if ((module.getModuleOwner() == null) || (module.getModuleOwner().getId() == null)) {
			throw new ModuleOwnerRequiredException();
		}

		Integer moduleOwnerId = module.getModuleOwner().getId();
		module.setModuleOwner(
			this.collegeEmployeeRepository.findById(moduleOwnerId)
				.orElseThrow(() -> new CollegeEmployeeNotFoundException(moduleOwnerId))
		);

		if (module.getProfs() != null) {
			List<CollegeEmployeeEntity> updatedProfs = module.getProfs().stream()
				.filter(prof -> (prof.getId() != null))
				.map(prof -> this.collegeEmployeeRepository.findById(prof.getId()).orElse(null))
				.filter(prof -> (prof != null))
				.collect(Collectors.toList());

			module.setProfs(updatedProfs);
		}

		return module;
	}
}
