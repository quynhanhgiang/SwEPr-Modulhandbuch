package de.hscoburg.modulhandbuchbackend.model.entities;

import java.sql.Blob;

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
@Table(name= "module_manual")
public class ModuleManualEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_spo_pk_unique_id", nullable = false)
	private SpoEntity spo;

	@Column(name = "first_page")
	private Blob firstPage;

	@Column(name = "preliminary_note")
	private String preliminaryNote;

	@Column(name = "generated_pdf")
	private Blob generatedPdf;

	@Column(name = "semester", nullable = false)
	private String semester;


	public ModuleManualEntity(SpoEntity spo, Blob firstPage, String preliminaryNote, Blob generatedPdf, String semester) {
		this.spo = spo;
		this.firstPage = firstPage;
		this.preliminaryNote = preliminaryNote;
		this.generatedPdf = generatedPdf;
		this.semester = semester;
	}	
}
