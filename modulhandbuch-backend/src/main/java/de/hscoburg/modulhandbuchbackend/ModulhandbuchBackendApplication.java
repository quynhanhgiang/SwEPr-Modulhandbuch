package de.hscoburg.modulhandbuchbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This is the entrance for a Spring Boot application. It is configured to scan
 * for classes annotated with `@ConfigurationProperties` in
 * the package "de.hscoburg.modulhandbuchbackend.properties".
 */
@SpringBootApplication
@ConfigurationPropertiesScan("de.hscoburg.modulhandbuchbackend.properties")
public class ModulhandbuchBackendApplication extends SpringBootServletInitializer {

	/**
	 * This is the main method, the entry point for the application.
	 * 
	 * @param args The arguments passed on startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ModulhandbuchBackendApplication.class, args);
	}

	/**
	 * This method adds the sources (configuration classes and components) specified
	 * in this class to the
	 * application.
	 * 
	 * @param application The SpringApplicationBuilder class that is used to run the
	 *                    application.
	 * @return The passed SpringApplicationBuilder with the addition of the sources.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ModulhandbuchBackendApplication.class);
	}
}
