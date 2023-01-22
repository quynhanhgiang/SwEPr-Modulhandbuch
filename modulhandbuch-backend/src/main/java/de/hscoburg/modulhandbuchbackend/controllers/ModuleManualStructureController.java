package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.StructureDTO;
import de.hscoburg.modulhandbuchbackend.services.ModuleManualStructureService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is a REST controller that handles requests regarding the structure of a module manual.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualStructureController {
	private final ModuleManualStructureService moduleManualStructureService;

	/**
	 * This method handles GET requests to the `/module-manuals/{id}/segments` endpoint and returns a list of all the segments of a module manual.
	 * 
	 * @param id The id of the module manual.
	 * @return A list of all segments of the module manual.
	 */
	@GetMapping("/segments")
	public List<StructureDTO> allSegements(@PathVariable Integer id) {
		return this.moduleManualStructureService.getStructure(id, moduleManual -> moduleManual.getFirstSegment());
	}

	/**
	 * This method handles GET requests to the `/module-manuals/{id}/module-types` endpoint and returns a list of all the module types of a module manual.
	 * 
	 * @param id The id of the module manual.
	 * @return A list of all module types of the module manual.
	 */
	@GetMapping("/module-types")
	public List<StructureDTO> allModuleTypes(@PathVariable Integer id) {
		return this.moduleManualStructureService.getStructure(id, moduleManual -> moduleManual.getFirstModuleType());
	}

	/**
	 * This method handles PUT requests to the `/module-manuals/{id}/segments` endpoint and replaces the segments of a module manual. It the returns the updated segments.
	 * 
	 * @param segments The updated segments to replace the existing ones with.
	 * @param id The id of the module manual.
	 * @return A list of the updated segments of the module manual.
	 */
	@PutMapping("/segments")
	public List<StructureDTO> replaceSegments(@RequestBody List<StructureDTO> segments, @PathVariable Integer id) {
		return this.moduleManualStructureService.replaceSegments(segments, id);
	}

		/**
	 * This method handles PUT requests to the `/module-manuals/{id}/module-types` endpoint and replaces the module types of a module manual. It the returns the updated module types.
	 * 
	 * @param moduleTypes The updated module types to replace the existing ones with.
	 * @param id The id of the module manual.
	 * @return A list of the updated module types of the module manual.
	 */
	@PutMapping("/module-types")
	public List<StructureDTO> replaceModuleTypes(@RequestBody List<StructureDTO> moduleTypes, @PathVariable Integer id) {
		return this.moduleManualStructureService.replaceModuleTypes(moduleTypes, id);
	}
}
