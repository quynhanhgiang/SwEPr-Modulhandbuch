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

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualStructureController {
	private final ModuleManualStructureService moduleManualStructureService;

	@GetMapping("/segments")
	public List<StructureDTO> allSegements(@PathVariable Integer id) {
		return this.moduleManualStructureService.getStructure(id, moduleManual -> moduleManual.getFirstSection());
	}

	@GetMapping("/module-types")
	public List<StructureDTO> allModuleTypes(@PathVariable Integer id) {
		return this.moduleManualStructureService.getStructure(id, moduleManual -> moduleManual.getFirstType());
	}

	@PutMapping("/segments")
	public List<StructureDTO> replaceSegments(@RequestBody List<StructureDTO> segments, @PathVariable Integer id) {
		return this.moduleManualStructureService.replaceSegments(segments, id);
	}

	@PutMapping("/module-types")
	public List<StructureDTO> replaceModuleTypes(@RequestBody List<StructureDTO> moduleTypes, @PathVariable Integer id) {
		return this.moduleManualStructureService.replaceModuleTypes(moduleTypes, id);
	}
}
