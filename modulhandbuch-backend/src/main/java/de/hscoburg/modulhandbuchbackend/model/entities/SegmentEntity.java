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

/**
 * This class is the representation of the database table `section`.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "section")
public class SegmentEntity extends StructureEntity<SegmentEntity> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_section_pk_unique_id")
	private SegmentEntity next;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_module_manual_pk_unique_id", nullable = false)
	private ModuleManualEntity moduleManual;

	@Column(name = "name", nullable = false)
	private String value;
}
