package de.hscoburg.modulhandbuchbackend.model;

import java.sql.Blob;
import java.util.Arrays;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.hscoburg.modulhandbuchbackend.converters.ModuleEntityCycleConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Module")
public class ModuleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_uniqueId")
	private Integer id;

	@Column(name = "ModuleName")
	private String moduleName;

	@Column(name = "Abbreviation")
	private String abbreviation;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "module")
    private List<VariationEntity> variations;

	@Convert(converter = ModuleEntityCycleConverter.class)
	@Column(name = "Cycle", columnDefinition = "ENUM('Jährlich', 'Halbjährlich')")
	private Cycle cycle;

	@Enumerated(EnumType.STRING)
	@Column(name = "Duration", columnDefinition = "ENUM('ONESEMESTER')")
	private Duration duration;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "FK_CollegeEmployee_PK_uniqueId")
	private CollegeEmployeeEntity moduleOwner;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "Profs")
    private List<CollegeEmployeeEntity> profs;

	@Enumerated(EnumType.STRING)
	@Column(name = "Language", columnDefinition = "ENUM('GERMAN', 'ENGLISH')")
	private Language language;

	// TODO rename column
	@Column(name = "Usage2")
	private String usage;

	@Column(name = "AdmissionRequirements")
	private Blob admissionRequirements;

	@Column(name = "KnowledgeRequirements")
	private Blob knowledgeRequirements;

	@Column(name = "Skills")
	private Blob skills;

	@Column(name = "Content")
	private Blob content;

	@Column(name = "ExamType")
	private String examType;

	@Column(name = "Certificates")
	private String certificates;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "Module_has_MediaType")
    private List<MediaTypeEntity> mediaTypes;

	@Column(name = "Literature")
	private Blob literature;

	@Enumerated(EnumType.STRING)
	@Column(name = "MaternityProtection")
	private MaternityProtection maternityProtection;

	public ModuleEntity(CollegeEmployeeEntity collegeEmployee, String name, String abbreviation, Integer sws, Integer ects, Blob workload,
			Cycle cycle, Duration duration, Language language, String usage, Blob admissionRequirements,
			Blob knowledgeRequirements, Blob skills, Blob content, String examType, String certificates,
			Blob literature, MaternityProtection maternityProtection) {
		this.moduleOwner = collegeEmployee;
		this.moduleName = name;
		this.abbreviation = abbreviation;
		this.cycle = cycle;
		this.duration = duration;
		this.language = language;
		this.usage = usage;
		this.admissionRequirements = admissionRequirements;
		this.knowledgeRequirements = knowledgeRequirements;
		this.skills = skills;
		this.content = content;
		this.examType = examType;
		this.certificates = certificates;
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
	}

	// TODO converter
	public enum Duration {
		ONESEMESTER,
	}
	
	// TODO converter
	public enum Language {
		GERMAN,
		ENGLISH,
	}
	
	public enum MaternityProtection {
		R,
		G,
		Y,
	}
}
