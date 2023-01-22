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
import de.hscoburg.modulhandbuchbackend.exceptions.IdsViaPostRequestNotSupportedException;
import de.hscoburg.modulhandbuchbackend.exceptions.SpoNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.SpoEntity;
import de.hscoburg.modulhandbuchbackend.repositories.SpoRepository;
import de.hscoburg.modulhandbuchbackend.services.ModulhandbuchBackendMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is a REST controller that handles requests sent to the `/spos` endpoint.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/spos")
public class SpoController {
	private final SpoRepository spoRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper;

	/**
	 * This method handles GET requests to the `/spos` endpoint and returns a list of all spos.
	 * 
	 * @return A list of {@link SpoDTO}.
	 */
	@GetMapping("")
	public List<SpoDTO> allSpos() {
		List<SpoEntity> result = this.spoRepository.findAll();
		return result.stream().map(spo -> modulhandbuchBackendMapper.map(spo, SpoDTO.class)).collect(Collectors.toList());
	}

	/**
	 * This method handles GET requests to the `/spos/{id}` endpoint where id is a variable integer.
	 * It then uses the id to find the mapped data set in the database. If it finds one, it returns it as a
	 * {@link SpoDTO}. If it does not find one, it throws a {@link SpoNotFoundException}.
	 * 
	 * @param id The id of the spo to be retrieved.
	 * @return A {@link SpoDTO} with the found data.
	 */
	@GetMapping("/{id}")
	SpoDTO oneSpo(@PathVariable Integer id) {
		SpoEntity result = this.spoRepository.findById(id)
			.orElseThrow(() -> new SpoNotFoundException(id));
		return modulhandbuchBackendMapper.map(result, SpoDTO.class);
	}

	/**
	 * This method handles POST requests to the `/spos` endpoint and creates a new spo.
	 * The data of the newly created spo is then returned to the caller.
	 * 
	 * @param newSpo The object that is sent via the POST request.
	 * @return A {@link SpoDTO} with the data of the created spo.
	 */
	@PostMapping("")
	SpoDTO newSpo(@RequestBody SpoDTO newSpo) {
		if (newSpo.getId() != null) {
			throw new IdsViaPostRequestNotSupportedException();
		}

		SpoEntity spoEntity = modulhandbuchBackendMapper.map(newSpo, SpoEntity.class);

		SpoEntity result = this.spoRepository.save(spoEntity);
		return modulhandbuchBackendMapper.map(result, SpoDTO.class);
	}
}
