package de.hscoburg.modulhandbuchbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.hscoburg.modulhandbuchbackend.model.CollegeEmployeeEntity;
import de.hscoburg.modulhandbuchbackend.model.ModuleEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CollegeEmployeeRepository;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleRepository;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(ModuleRepository moduleRepository, CollegeEmployeeRepository collegeEmployeeRepository) {
		return args -> {
			CollegeEmployeeEntity pfeiffer = new CollegeEmployeeEntity("Volkhard", "Pfeiffer", "Prof.", CollegeEmployeeEntity.Gender.M);
			LoadDatabase.log.info("Preloading " + collegeEmployeeRepository.save(pfeiffer));

			LoadDatabase.log.info("Preloading " +  moduleRepository.save(new ModuleEntity(pfeiffer, "Programmieren 1", "Prog1", 4, 7, null, ModuleEntity.Cycle.ANNUALLY, ModuleEntity.Duration.ONESEMESTER, null, null, null, null, null, null, null, null, null, null)));

			LoadDatabase.log.info("Preloading " +  moduleRepository.save(new ModuleEntity(null, "Analysis", "Ana", 4, 7, null, null, null, null, null, null, null, null, null, null, null, null, null)));
		};
	}
}
