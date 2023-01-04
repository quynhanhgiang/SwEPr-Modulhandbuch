package de.hscoburg.modulhandbuchbackend.model.entities;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
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

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_college_employee_pk_unique_id", nullable = false)
	private CollegeEmployeeEntity moduleOwner;

	@Column(name = "module_name", nullable = false)
	private String moduleName;

	@Column(name = "abbreviation")
	private String abbreviation;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_cycle_pk_unique_id", nullable = false)
	private CycleEntity cycle;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_duration_pk_unique_id", nullable = false)
	private DurationEntity duration;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_language_pk_unique_id")
	private LanguageEntity language;

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

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_maternity_protection_pk_unique_id", nullable = false)
	private MaternityProtectionEntity maternityProtection;

	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "modules")
    private List<CollegeEmployeeEntity> profs;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "module")
    private List<VariationEntity> variations = Collections.emptyList();
}
