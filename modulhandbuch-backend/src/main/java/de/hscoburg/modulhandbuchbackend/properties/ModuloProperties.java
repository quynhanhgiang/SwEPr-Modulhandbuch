package de.hscoburg.modulhandbuchbackend.properties;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

/**
 * This class provides access to the properties of the application.
 */
@Data
@ConfigurationProperties("modulo")
@ConfigurationPropertiesScan
public class ModuloProperties {

	@NotBlank
	private String filesDirectory;
}
