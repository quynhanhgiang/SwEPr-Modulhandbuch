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
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "type")
public class TypeEntity implements StructureEntity<TypeEntity> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_type_pk_unique_id")
	private TypeEntity next;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_module_manual_pk_unique_id", nullable = false)
	private ModuleManualEntity moduleManual;

	@Column(name = "name", nullable = false)
	private String name;
}
