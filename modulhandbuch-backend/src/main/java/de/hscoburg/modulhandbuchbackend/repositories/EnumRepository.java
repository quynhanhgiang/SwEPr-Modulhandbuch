package de.hscoburg.modulhandbuchbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import de.hscoburg.modulhandbuchbackend.model.entities.EnumEntity;

@NoRepositoryBean
public interface EnumRepository<T extends EnumEntity<T>> extends JpaRepository<T, Integer> {
	List<T> findByValue(String value);
}
