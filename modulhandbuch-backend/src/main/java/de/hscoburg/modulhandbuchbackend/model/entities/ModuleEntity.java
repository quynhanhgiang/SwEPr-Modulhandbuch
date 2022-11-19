package de.hscoburg.modulhandbuchbackend.model.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityCycleDatabaseConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityDurationDatabaseConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityLanguageDatabaseConverter;
import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityMaternityProtectionDatabaseConverter;
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

	@Column(name = "abbreviation")
	private String abbreviation;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "module")
    private List<VariationEntity> variations = Collections.emptyList();

	@Convert(converter = ModuleEntityCycleDatabaseConverter.class)
	@Column(name = "cycle", nullable = false, columnDefinition = "ENUM('Jährlich', 'Halbjährlich') DEFAULT 'Jährlich'")
	private Cycle cycle;

	@Convert(converter = ModuleEntityDurationDatabaseConverter.class)
	@Column(name = "duration", nullable = false, columnDefinition = "ENUM('Einsemestrig') DEFAULT 'Einsemestrig'")
	private Duration duration;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_college_employee_pk_unique_id", nullable = false)
	private CollegeEmployeeEntity moduleOwner;

	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "modules")
    private List<CollegeEmployeeEntity> profs;

	@Convert(converter = ModuleEntityLanguageDatabaseConverter.class)
	@Column(name = "language", columnDefinition = "ENUM('Deutsch', 'Englisch', 'Französisch', 'Spanisch', 'Chinesisch', 'Russisch') DEFAULT 'NULL'")
	private Language language;

	@Column(name = "course_usage")
	private String usage;

	@Column(name = "knowledge_requirements")
	private String knowledgeRequirements;

	@Column(name = "skills")
	private String skills;

	@Column(name = "content")
	private String content;

	@Column(name = "exam_type", nullable = false)
	private String examType;

	@Column(name = "certificates")
	private String certificates;

	@Column(name = "media_type")
    private String mediaType;

	@Column(name = "literature")
	private String literature;

	@Convert(converter = ModuleEntityMaternityProtectionDatabaseConverter.class)
	@Column(name = "maternity_protection", nullable = false, columnDefinition = "ENUM('Rot', 'Grün', 'Gelb') DEFAULT 'Grün'")
	private MaternityProtection maternityProtection;

	public ModuleEntity(String moduleName, String abbreviation, List<VariationEntity> variations, Cycle cycle, Duration duration, CollegeEmployeeEntity moduleOwner, List<CollegeEmployeeEntity> profs, Language language, String usage, String knowledgeRequirements, String skills, String content, String examType, String certificates, String mediaType, String literature, MaternityProtection maternityProtection) {
		this.moduleName = moduleName;
		this.abbreviation = abbreviation;
		this.variations = variations;
		this.cycle = cycle;
		this.duration = duration;
		this.moduleOwner = moduleOwner;
		this.profs = profs;
		this.language = language;
		this.usage = usage;
		this.knowledgeRequirements = knowledgeRequirements;
		this.skills = skills;
		this.content = content;
		this.examType = examType;
		this.certificates = certificates;
		this.mediaType = mediaType;
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

		public static Cycle fromString(String text) {
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

		public static Duration fromString(String text) {
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

		public static Language fromString(String text) {
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
	
	@AllArgsConstructor
	@Getter
	public enum MaternityProtection {
		RED("Rot"),
		GREEN("Grün"),
		YELLOW("Gelb"),
		;

		private final String text;

		public static MaternityProtection fromString(String text) {
			return Arrays.stream(MaternityProtection.values())
				.filter(maternityProtection -> maternityProtection.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
}
