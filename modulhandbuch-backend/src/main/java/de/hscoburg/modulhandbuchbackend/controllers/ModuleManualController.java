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

import de.hscoburg.modulhandbuchbackend.dto.ModuleFullDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleManualDTO;
import de.hscoburg.modulhandbuchbackend.dto.ModuleManualVariationDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.model.entities.VariationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SectionRepository;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import de.hscoburg.modulhandbuchbackend.repositories.TypeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.VariationRepository;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals")
public class ModuleManualController {
	private final AdmissionRequirementRepository admissionRequirementRepository;
	private final ModuleManualRepository moduleManualRepository;
	private final ModuleRepository moduleRepository;
	private final SpoRepository spoRepository;
	private final SectionRepository sectionRepository;
	private final TypeRepository typeRepository;
	private final VariationRepository variationRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;
	
	@GetMapping("")
	public List<ModuleManualDTO> allModuleManuals() {
		List<ModuleManualEntity> result = this.moduleManualRepository.findAll();
		return result.stream().map(moduleManual -> modulhandbuchBackendMapper.map(moduleManual, ModuleManualDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	ModuleManualDTO oneModuleManual(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	@GetMapping("/{id}/modules")
	public List<ModuleFullDTO> allAssociatedModules(@PathVariable Integer id) {
		List<ModuleEntity> result = this.moduleRepository.findAll();
		return result.stream().map((module) -> modulhandbuchBackendMapper.map(module, ModuleFullDTO.class)).collect(Collectors.toList());
	}

	@PostMapping("")
	ModuleManualDTO newModuleManual(@RequestBody ModuleManualDTO newModuleManual) {
		if (newModuleManual.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
		}

		ModuleManualEntity moduleManualEntity = modulhandbuchBackendMapper.map(newModuleManual, ModuleManualEntity.class);

		if (moduleManualEntity.getSpo() != null) {
			if (moduleManualEntity.getSpo().getId() == null) {
				// if id of spo is null a new entry for it should be created
				SpoEntity result = this.spoRepository.save(moduleManualEntity.getSpo());
				moduleManualEntity.setSpo(result);
			} else {
				// extract only id from spo and replace other contents of spo with data from database
				moduleManualEntity.setSpo(
					// TODO own exception
					this.spoRepository.findById(moduleManualEntity.getSpo().getId()).orElseThrow(() -> new RuntimeException("Id for spo not found"))
				);
			}
		}

		ModuleManualEntity result = this.moduleManualRepository.save(moduleManualEntity);
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	@PutMapping("/{id}")
	ModuleManualDTO replaceModuleManual(@RequestBody ModuleManualDTO updatedModuleManual, @PathVariable Integer id) {
		this.moduleManualRepository.findById(id).orElseThrow(() -> {
			// TODO own exception and advice
			throw new RuntimeException(String.format("ID %d is not mapped for any module manual. For creating a new module manual please use a POST request.", id));
		});

		updatedModuleManual.setId(id);
		ModuleManualEntity moduleManualEntity = modulhandbuchBackendMapper.map(updatedModuleManual, ModuleManualEntity.class);

		// extract only id from spo and replace other contents of spo with data from database
		if ((moduleManualEntity.getSpo() != null) && (moduleManualEntity.getSpo().getId() != null)) {
			// TODO own exception
			moduleManualEntity.setSpo(this.spoRepository.findById(moduleManualEntity.getSpo().getId()).orElseThrow(() -> new RuntimeException("Id for spo not found")));
		}

		ModuleManualEntity result = this.moduleManualRepository.save(moduleManualEntity);
		return modulhandbuchBackendMapper.map(result, ModuleManualDTO.class);
	}

	@PutMapping("/{id}/modules")
	public List<ModuleManualVariationDTO> replaceVariations(@RequestBody List<ModuleManualVariationDTO> variations, @PathVariable Integer id) {
		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));
		
		Stream<VariationEntity> variationEntities = variations.stream()
			.map(variation -> this.modulhandbuchBackendMapper.map(variation, VariationEntity.class))
			.peek(variationEntity -> variationEntity.setModuleManual(moduleManual));

		// validation
		variationEntities = variationEntities
			.filter(variationEntity -> variationEntity.getModule() != null)
			.filter(variationEntity -> variationEntity.getModule().getId() != null)
			.peek(variationEntity -> {
				Integer moduleId = variationEntity.getModule().getId();
				variationEntity.setModule(this.moduleRepository.findById(moduleId)
						.orElseThrow(() -> new ModuleNotFoundException(moduleId)));
			})
			.peek(variationEntity -> {
				if ((variationEntity.getType() != null) && (variationEntity.getType().getId() != null)) {
					Integer typeId = variationEntity.getType().getId();
					variationEntity.setType(this.typeRepository.findById(typeId).orElse(null));
				} else {
					variationEntity.setType(null);
				}
			})
			.peek(variationEntity -> {
				if ((variationEntity.getSection() != null) && (variationEntity.getSection().getId() != null)) {
					Integer sectionId = variationEntity.getSection().getId();
					variationEntity.setSection(this.sectionRepository.findById(sectionId).orElse(null));
				} else {
					variationEntity.setSection(null);
				}
			})
			.peek(variationEntity -> {
				if ((variationEntity.getAdmissionRequirement() != null) && (variationEntity.getAdmissionRequirement().getId() != null)) {
					Integer admissionRequirementId = variationEntity.getAdmissionRequirement().getId();
					variationEntity.setAdmissionRequirement(this.admissionRequirementRepository.findById(admissionRequirementId).orElse(null));
				} else {
					variationEntity.setAdmissionRequirement(null);
				}
			});

		return variationEntities
			.map(variationEntity -> this.variationRepository.save(variationEntity))
			.map(variationEntity -> this.modulhandbuchBackendMapper.map(variationEntity, ModuleManualVariationDTO.class))
			.collect(Collectors.toList());
	}
}
