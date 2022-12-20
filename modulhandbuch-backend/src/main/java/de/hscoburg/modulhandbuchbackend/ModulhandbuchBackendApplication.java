package de.hscoburg.modulhandbuchbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ConfigurationPropertiesScan("de.hscoburg.modulhandbuchbackend.properties")
public class ModulhandbuchBackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ModulhandbuchBackendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ModulhandbuchBackendApplication.class);
	}
}
