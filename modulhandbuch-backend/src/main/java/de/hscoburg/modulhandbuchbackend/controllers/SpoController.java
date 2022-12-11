package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.SpoDTO;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/spos")
public class SpoController {
	private final SpoRepository spoRepository;
	
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	@GetMapping("")
	public List<SpoDTO> allSpos() {
		List<SpoEntity> result = this.spoRepository.findAll();
		return result.stream().map(spo -> modulhandbuchBackendMapper.map(spo, SpoDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	SpoDTO oneSpo(@PathVariable Integer id) {
		SpoEntity result = this.spoRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for spo not found.", id)));
		return modulhandbuchBackendMapper.map(result, SpoDTO.class);
	}

	@PostMapping("")
	SpoDTO newSpo(@RequestBody SpoDTO newSpo) {
		if (newSpo.getId() != null) {
			// TODO own exception and advice
			throw new RuntimeException("Sending IDs via POST requests is not supported. Please consider to use a PUT request or set the ID to null");
		}

		SpoEntity spoEntity = modulhandbuchBackendMapper.map(newSpo, SpoEntity.class);

		SpoEntity result = this.spoRepository.save(spoEntity);
		return modulhandbuchBackendMapper.map(result, SpoDTO.class);
	}
}
