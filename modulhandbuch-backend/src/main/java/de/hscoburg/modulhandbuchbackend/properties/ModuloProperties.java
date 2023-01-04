package de.hscoburg.modulhandbuchbackend.properties;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@Data
@ConfigurationProperties("modulo")
@ConfigurationPropertiesScan
public class ModuloProperties {

	@NotBlank
	private String filesDirectory;
}
