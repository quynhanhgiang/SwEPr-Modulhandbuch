package de.hscoburg.modulhandbuchbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ModulhandbuchBackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ModulhandbuchBackendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ModulhandbuchBackendApplication.class);
	}
}
