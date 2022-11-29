package de.hscoburg.modulhandbuchbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.dto.CollegeEmployeeDTO;
import de.hscoburg.modulhandbuchbackend.mappers.ModulhandbuchBackendMapper;
import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/college-employees")
public class CollegeEmployeeController {
	private final CollegeEmployeeRepository collegeEmployeeRepository;
	private final ModulhandbuchBackendMapper modulhandbuchBackendMapper = new ModulhandbuchBackendMapper();

	@GetMapping("")
	List<CollegeEmployeeDTO> allCollegeEmployees() {
		List<CollegeEmployeeEntity> result = this.collegeEmployeeRepository.findAll();
		return result.stream().map((collegeEmployee) -> modulhandbuchBackendMapper.map(collegeEmployee, CollegeEmployeeDTO.class)).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	CollegeEmployeeDTO oneModule(@PathVariable Integer id) {
		CollegeEmployeeEntity result = this.collegeEmployeeRepository.findById(id)
			// TODO own exception and advice
			.orElseThrow(() -> new RuntimeException(String.format("Id %d for college employee not found.", id)));
		return modulhandbuchBackendMapper.map(result, CollegeEmployeeDTO.class);
	}
}
