package de.hscoburg.modulhandbuchbackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Entity
// @Table(name = "module_has_spo")
public class VariationEntity {
	// TODO

	// @ManyToOne(cascade = CascadeType.MERGE)
	// @JoinColumn(name = "pk_module_pk_unique_id", nullable = false)
	// private ModuleEntity module;
}
