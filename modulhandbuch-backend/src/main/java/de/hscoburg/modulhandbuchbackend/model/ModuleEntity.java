package de.hscoburg.modulhandbuchbackend.model;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityCycleConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityDurationConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityLanguageConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "module")
public class ModuleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@Column(name = "module_name", nullable = false)
	private String moduleName;

	@Column(name = "abbreviation", nullable = false)
	private String abbreviation;

	// TODO implementation of VariationEntity
	@Transient
	// @OneToMany(cascade = CascadeType.MERGE, mappedBy = "module")
    private List<VariationEntity> variations = Collections.emptyList();

	@Convert(converter = ModuleEntityCycleConverter.class)
	@Column(name = "cycle", nullable = false, columnDefinition = "ENUM('Jährlich', 'Halbjährlich') DEFAULT 'Jährlich'")
	private Cycle cycle;

	@Convert(converter = ModuleEntityDurationConverter.class)
	@Column(name = "duration", nullable = false, columnDefinition = "ENUM('Einsemestrig') DEFAULT 'Einsemestrig'")
	private Duration duration;

	// TODO revert to MERGE because at the moment every moduleOwner gets a new entry even if it already exists
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_college_employee_pk_unique_id", nullable = false)
	private CollegeEmployeeEntity moduleOwner;

	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "modules")
    private List<CollegeEmployeeEntity> profs;

	@Convert(converter = ModuleEntityLanguageConverter.class)
	@Column(name = "language", nullable = false, columnDefinition = "ENUM('Deutsch', 'Englisch', 'Französisch', 'Spanisch', 'Chinesisch', 'Russisch') DEFAULT 'Deutsch'")
	private Language language;

	// TODO convert Blob to String and vice versa
	@Column(name = "course_usage")
	private Blob usage;

	@Column(name = "admission_requirements")
	private Blob admissionRequirements;

	@Column(name = "knowledge_requirements")
	private Blob knowledgeRequirements;

	@Column(name = "skills")
	private Blob skills;

	@Column(name = "content")
	private Blob content;

	@Column(name = "exam_type", nullable = false)
	private String examType;

	@Column(name = "certificates")
	private String certificates;

	@Column(name = "media_type")
    private Blob mediaTypes;

	@Column(name = "literature")
	private Blob literature;

	@Enumerated(EnumType.STRING)
	@Column(name = "maternity_protection", columnDefinition = "ENUM('R', 'G', 'Y') DEFAULT 'G'")
	private MaternityProtection maternityProtection;

	public ModuleEntity(String moduleName, String abbreviation, List<VariationEntity> variations, Cycle cycle,
			Duration duration, CollegeEmployeeEntity moduleOwner, List<CollegeEmployeeEntity> profs, Language language,
			Blob usage, Blob admissionRequirements, Blob knowledgeRequirements, Blob skills, Blob content,
			String examType, String certificates, Blob mediaTypes, Blob literature,
			MaternityProtection maternityProtection) {
		this.moduleName = moduleName;
		this.abbreviation = abbreviation;
		this.variations = variations;
		this.cycle = cycle;
		this.duration = duration;
		this.moduleOwner = moduleOwner;
		this.profs = profs;
		this.language = language;
		this.usage = usage;
		this.admissionRequirements = admissionRequirements;
		this.knowledgeRequirements = knowledgeRequirements;
		this.skills = skills;
		this.content = content;
		this.examType = examType;
		this.certificates = certificates;
		this.mediaTypes = mediaTypes;
		this.literature = literature;
		this.maternityProtection = maternityProtection;
	}	

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public enum Cycle {
		ANNUALLY("Jährlich"),
		SEMIANUALLY("Halbjährlich"),
		;

		private final String text;

		public static Cycle get(String text) {
			return Arrays.stream(Cycle.values())
				.filter(cycle -> cycle.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public enum Duration {
		ONESEMESTER("Einsemestrig"),
		;

		private final String text;

		public static Duration get(String text) {
			return Arrays.stream(Duration.values())
				.filter(duration -> duration.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
	
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public enum Language {
		GERMAN("Deutsch"),
		ENGLISH("English"),
		FRENCH("Französisch"),
		SPANISH("Spanisch"),
		CHINESE("Chinesisch"),
		RUSSIAN("Russisch"),
		;

		private final String text;

		public static Language get(String text) {
			return Arrays.stream(Language.values())
				.filter(language -> language.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
	
	public enum MaternityProtection {
		R,
		G,
		Y,
	}
}
