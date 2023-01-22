package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleManualDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleManualVariationDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.IdsViaPostRequestNotSupportedException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.SpoNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.services.VariationService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is a REST controller that handles requests sent to the `/modules` endpoint.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals")
public class ModuleManualController {
	private final ModuleManualRepository moduleManualRepository;
	private final ModuleRepository moduleRepository;
	private final SpoRepository spoRepository;
	private final VariationRepository variationRepository;
	private final VariationService variationService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;
	
	/**
	 * This method handles GET requests to the `/module-manuals` endpoint and returns a list of all module manuals.
	 * 
	 * @return A list of {@link ModuleManualDTO}.
	 */
	@GetMapping("")
	public List<ModuleManualDTO> allModuleManuals() {
		List<ModuleManualEntity> result = this.moduleManualRepository.findAll();
		return result.stream().map(moduleManual -> modulhandbuchBackendMapper.map(moduleManual, ModuleManualDTO.class)).collect(Collectors.toList());
	}

	/**
	 * This method handles GET requests to the `/module-manuals/{id}` endpoint where id is variable integer.
	 * It then uses the id to find the mapped data set in the database. If it finds one, it returns it as a
	 * {@link ModuleManualDTO}. If it does not find one, it throws a {@link ModuleManualNotFoundException}.
	 * 
	 * @param id The id of the module manual to be retrieved.
	 * @return A {@link ModuleManualDTO} with the found data.
	 */
	@GetMapping("/{id}")
	ModuleManualDTO oneModuleManual(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	/**
	 * This method handles GET requests to the `/module-manuals/{id}/modules` endpoint where id is variable integer.
	 * It then uses the id to find the mapped data set in the database. If it finds one, it returns a list of all modules and their variations mapped to this module manual. If it does not find one, it throws a {@link ModuleManualNotFoundException}.
	 * 
	 * @return A list of {@link ModuleManualVariationDTO} mapped to the module manual.
	 */
	@GetMapping("/{id}/modules")
	public List<ModuleManualVariationDTO> allAssociatedModules(@PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		List<VariationEntity> result = this.variationRepository.findByModuleManual(moduleManual);
		return result.stream().map(variation -> modulhandbuchBackendMapper.map(variation, ModuleManualVariationDTO.class)).collect(Collectors.toList());
	}

	/**
	 * This method handles POST requests to the `/module-manuals` endpoint and creates a new module manual.
	 * The data of the newly created module manual is then returned to the caller.
	 * 
	 * @param newModuleManual The object that is sent via the POST request.
	 * @return A {@link ModuleManualDTO} with the data of the created module manual.
	 */
	@PostMapping("")
	ModuleManualDTO newModuleManual(@RequestBody ModuleManualDTO newModuleManual) {
		if (newModuleManual.getId() != null) {
			throw new IdsViaPostRequestNotSupportedException();
		}

		ModuleManualEntity moduleManualEntity = modulhandbuchBackendMapper.map(newModuleManual, ModuleManualEntity.class);

		if (moduleManualEntity.getSpo() != null) {
			if (moduleManualEntity.getSpo().getId() == null) {
				// if id of spo is null a new entry for it should be created
				SpoEntity result = this.spoRepository.save(moduleManualEntity.getSpo());
				moduleManualEntity.setSpo(result);
			} else {
				// extract only id from spo and replace other contents of spo with data from 
				Integer spoId = moduleManualEntity.getSpo().getId();
				moduleManualEntity.setSpo(
					this.spoRepository.findById(spoId)
						.orElseThrow(() -> new SpoNotFoundException(spoId))
				);
			}
		}

		ModuleManualEntity result = this.moduleManualRepository.save(moduleManualEntity);
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	/**
	 * This method handles PUT requests to the `/module-manuals` endpoint and updates an existing module manual.
	 * The data of the updated module manual is then returned to the caller.
	 * 
	 * @param updatedModuleManual The object that is sent via the PUT request.
	 * @param id The id to identify the module manual to update.
	 * @return A {@link ModuleManualDTO} with the data of the updated module manual.
	 */
	@PutMapping("/{id}")
	ModuleManualDTO replaceModuleManual(@RequestBody ModuleManualDTO updatedModuleManual, @PathVariable Integer id) {
		this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		updatedModuleManual.setId(id);
		ModuleManualEntity moduleManualEntity = modulhandbuchBackendMapper.map(updatedModuleManual, ModuleManualEntity.class);

		// extract only id from spo and replace other contents of spo with data from database
		if ((moduleManualEntity.getSpo() != null) && (moduleManualEntity.getSpo().getId() != null)) {
			Integer spoId = moduleManualEntity.getSpo().getId();
			moduleManualEntity.setSpo(
				this.spoRepository.findById(spoId)
					.orElseThrow(() -> new SpoNotFoundException(spoId)));
		}

		ModuleManualEntity result = this.moduleManualRepository.save(moduleManualEntity);
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	/**
	 * This method handles PUT requests to the `/module-manuals/{id}/modules` endpoint where id is variable integer.
	 * It then uses the id to find the mapped data set in the database. If it finds one, it updates the mapped modules and their variations to this module manual. If it does not find one, it throws a {@link ModuleManualNotFoundException}.
	 * The data of the updated module manual is then returned to the caller.
	 * 
	 * @param variations The object that is sent via the PUT request.
	 * @param id The id to identify the module manual for the new mappings.
	 * @return A list of the updated {@link ModuleManualVariationDTO} mapped to the module manual.
	 */
	@PutMapping("/{id}/modules")
	public List<ModuleManualVariationDTO> replaceVariations(@RequestBody List<ModuleManualVariationDTO> variations, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		// delete previous mappings
		this.variationRepository.deleteByModuleManual(moduleManual);
		
		Stream<VariationEntity> variationEntities = variations.stream()
			.map(variation -> this.modulhandbuchBackendMapper.map(variation, VariationEntity.class))
			.peek(variationEntity -> variationEntity.setModuleManual(moduleManual));

		// validation
		variationEntities = variationEntities
			.map(variationEntity -> this.variationService.cleanEntity(variationEntity))
			.filter(variationEntity -> variationEntity != null);
		
		return variationEntities
			.map(variationEntity -> this.variationRepository.save(variationEntity))
			.map(variationEntity -> this.modulhandbuchBackendMapper.map(variationEntity, ModuleManualVariationDTO.class))
			.collect(Collectors.toList());
	}
}
