package de.hscoburg.modulhandbuchbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hscoburg.modulhandbuchbackend.model.CollegeEmployeeEntity;

public interface CollegeEmployeeRepository extends JpaRepository<CollegeEmployeeEntity, Integer> {
}
