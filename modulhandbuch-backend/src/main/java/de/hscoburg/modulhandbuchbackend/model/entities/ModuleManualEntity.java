package de.hscoburg.modulhandbuchbackend.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class is the representation of the database table `module_manual`.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "module_manual")
public class ModuleManualEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_spo_pk_unique_id", nullable = false)
	private SpoEntity spo;

	@Column(name = "preliminary_note")
	private String preliminaryNoteLink;

	@Column(name = "generated_pdf")
	private String generatedPdfLink;

	@Column(name = "semester", nullable = false)
	private String semester;

	@Column(name = "module_plan")
	private String modulePlanLink;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_section_pk_unique_id")
	private SegmentEntity firstSegment;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_type_pk_unique_id")
	private ModuleTypeEntity firstModuleType;
}
