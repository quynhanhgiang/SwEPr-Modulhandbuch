package de.hscoburg.modulhandbuchbackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Module_has_SPO")
public class VariationEntity {
	// TODO

	// TODO only temp id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_uniqueId")
	private Integer id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PK_Module_PK_uniqueId")
	private ModuleEntity module;
}
