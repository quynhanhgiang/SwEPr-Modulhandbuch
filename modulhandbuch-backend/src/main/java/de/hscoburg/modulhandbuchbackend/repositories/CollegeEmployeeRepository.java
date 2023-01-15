package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.entities.CollegeEmployeeEntity;

public interface CollegeEmployeeRepository extends JpaRepository<CollegeEmployeeEntity, Integer> {
	List<CollegeEmployeeEntity> findByEmail(String email);
}
