package de.hscoburg.modulhandbuchbackend.model.ids;

import java.io.Serializable;

import lombok.Data;

@Data
public class VariationEntityId implements Serializable {
	private Integer module;
	private Integer spo;
	private Integer semester;
}
