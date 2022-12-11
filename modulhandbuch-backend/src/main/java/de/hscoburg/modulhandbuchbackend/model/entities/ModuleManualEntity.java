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

	public ModuleManualEntity(SpoEntity spo, String preliminaryNoteLink, String generatedPdfLink, String semester, String modulePlanLink) {
		this.spo = spo;
		this.preliminaryNoteLink = preliminaryNoteLink;
		this.generatedPdfLink = generatedPdfLink;
		this.semester = semester;
		this.modulePlanLink = modulePlanLink;
	}	
}
