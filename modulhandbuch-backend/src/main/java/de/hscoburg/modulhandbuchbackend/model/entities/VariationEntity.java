package de.hscoburg.modulhandbuchbackend.model.entities;

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
@Table(name = "module_has_module_manual")
public class VariationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_module_manual_pk_unique_id", nullable = false)
	private ModuleManualEntity moduleManual;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_module_pk_unique_id", nullable = false)
	private ModuleEntity module;

	@Column(name = "semester", nullable = false)
	private Integer semester;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_section_pk_unique_id")
	private SectionEntity section;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_type_pk_unique_id")
	private TypeEntity type;

	@Column(name = "sws")
	private Integer sws;

	@Column(name = "ects", nullable = false)
	private Integer ects;

	@Column(name = "workload")
	private String workLoad;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_admission_requirement_pk_unique_id")
	private AdmissionRequirementEntity admissionRequirement;
}
