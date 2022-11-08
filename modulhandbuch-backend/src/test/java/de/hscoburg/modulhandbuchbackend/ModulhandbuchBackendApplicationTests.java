package de.hscoburg.modulhandbuchbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hscoburg.modulhandbuchbackend.model.ModuleEntity;

@SpringBootTest
public class ModulhandbuchBackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testModule() {
		ModuleEntity prog1 = new ModuleEntity("Programmieren 1", "Prog1", null, ModuleEntity.Cycle.ANNUALLY, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		ModuleEntity ana = new ModuleEntity("Analysis", "Ana", null, ModuleEntity.Cycle.ANNUALLY, ModuleEntity.Duration.ONESEMESTER, null, null, ModuleEntity.Language.GERMAN, null, null, null, null, null, null, null, null, null, null);
	}
}
