package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.ModuleManualDTO;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals")
public class ModuleManualController {
	
	@GetMapping("")
	public List<ModuleManualDTO> allModuleManuals() {
		return Collections.emptyList();
	}
	
}
