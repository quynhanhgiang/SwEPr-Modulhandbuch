package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleFlatDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleFullDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.IdsViaPostRequestNotSupportedException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModuleService;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.services.VariationService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is a REST controller that handles requests sent to the `/modules`
 * endpoint.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/modules")
public class ModuleController {
	private final ModuleRepository moduleRepository;
	private final CollegeEmployeeRepository collegeEmployeeRepository;
	private final ModuleManualRepository moduleManualRepository;
	private final VariationRepository variationRepository;
	private final ModuleService moduleService;
	private final VariationService variationService;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	/**
	 * This method handles GET requests to the `/modules` endpoint and returns a
	 * list of all modules by default. If the parameter `flat` is set to the value
	 * `true`, a list of all modules is returned too, but the modules in the list
	 * have a reduced set of fields.
	 * If the parameter `flat` is set to the value `true` and the parameter
	 * `not-in-manual` is set to a number (for `not-in-manual` is `flat=true`
	 * required, otherwise nothing happens), a list of all modules in reduced form
	 * is returned which are not in the module manual specified by the given id.
	 * 
	 * @param flat                       If set to value `true`, the response will
	 *                                   be a list of {@link ModuleFlatDTO}.
	 * @param moduleManualToIgnoreString The id of the module manual to ignore. This
	 *                                   value is ignored if flat is not `true`.
	 * @return A list of all modules by default. If moduleManualToIgnoreString is
	 *         set to a number, a list of all modules in reduced form is returned
	 *         which are not in the module manual specified by the given id.
	 */
	@GetMapping("")
	public List<ModuleDTO> allModules(@RequestParam(name = "flat", required = false, defaultValue = "") String flat,
			@RequestParam(name = "not-in-manual", required = false, defaultValue = "") String moduleManualToIgnoreString) {
		if (!flat.equals("true")) {
			List<ModuleEntity> result = this.moduleRepository.findAll();
			return result.stream()
					.map(module -> this.modulhandbuchBackendMapper.map(module, ModuleFullDTO.class))
					.collect(Collectors.toList());
		}

		try {
			// on NumberFormatException moduleManualToIgnore was not set (or not a number)
			// so return all modules
			Integer moduleManualToIgnoreId = Integer.valueOf(moduleManualToIgnoreString);

			List<ModuleEntity> allModules = this.moduleRepository.findAll();
			List<ModuleEntity> modulesNotInModuleManual = new LinkedList<>();
			for (ModuleEntity module : allModules) {
				List<VariationEntity> variations = this.variationRepository.findByModule(module);
				long countOfVariationWithModuleManualToIgnore = variations.stream()
						.filter(variation -> variation.getModuleManual().getId() == moduleManualToIgnoreId)
						.count();

				if (countOfVariationWithModuleManualToIgnore == 0) {
					modulesNotInModuleManual.add(module);
				}
			}

			return modulesNotInModuleManual.stream()
					.map(module -> this.modulhandbuchBackendMapper.map(module, ModuleFlatDTO.class))
					.collect(Collectors.toList());

		} catch (NumberFormatException ignored) {
		}

		List<ModuleEntity> result = this.moduleRepository.findAll();
		return result.stream()
				.map(module -> this.modulhandbuchBackendMapper.map(module, ModuleFlatDTO.class))
				.collect(Collectors.toList());
	}

	/**
	 * This method handles GET requests to the `/modules/{id}` endpoint where id is
	 * variable integer. It then uses the id to find the mapped data set in the
	 * database. If it finds one, it returns it as a {@link ModuleFullDTO}. If it
	 * does not find one, it throws a {@link ModuleNotFoundException}.
	 * 
	 * @param id The id of the module to be retrieved.
	 * @return A {@link ModuleFullDTO} with the found data.
	 */
	@GetMapping("/{id}")
	ModuleFullDTO oneModule(@PathVariable Integer id) {
		ModuleEntity result = this.moduleRepository.findById(id)
				.orElseThrow(() -> new ModuleNotFoundException(id));
		return modulhandbuchBackendMapper.map(result, ModuleFullDTO.class);
	}

	/**
	 * This method handles POST requests to the `/modules` endpoint and creates a
	 * new module.
	 * The data of the newly created module is then returned to the caller.
	 * 
	 * @param newModule The object that is sent via the POST request.
	 * @return A {@link ModuleFullDTO} with the data of the created module.
	 */
	@PostMapping("")
	ModuleFullDTO newModule(@RequestBody ModuleFullDTO newModule) {
		if (newModule.getId() != null) {
			throw new IdsViaPostRequestNotSupportedException();
		}

		return this.moduleService.saveModule(newModule);
	}

	/**
	 * This method handles PUT requests to the `/modules` endpoint and updates an
	 * existing module. The data of the updated module is then returned to the
	 * caller.
	 * 
	 * @param updatedModule The object that is sent via the PUT request.
	 * @param id            The id to identify the module to update.
	 * @return A {@link ModuleFullDTO} with the data of the created module.
	 */
	@PutMapping("/{id}")
	ModuleFullDTO replaceModule(@RequestBody ModuleFullDTO updatedModule, @PathVariable Integer id) {
		updatedModule.setId(id);

		ModuleEntity moduleToUpdate = this.moduleRepository.findById(id)
				.orElseThrow(() -> new ModuleNotFoundException(id));

		// delete current variations
		List<VariationEntity> currentVariations = moduleToUpdate.getVariations();
		currentVariations.stream()
				.forEach(variation -> this.variationRepository.delete(variation));
		currentVariations = null;
		moduleToUpdate.setVariations(currentVariations);

		return this.moduleService.saveModule(updatedModule);
	}
}
