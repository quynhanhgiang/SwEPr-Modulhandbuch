package de.hscoburg.modulhandbuchbackend.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CollegeEmployee")
public class CollegeEmployeeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_uniqueId")
	private Integer id;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Title")
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(name = "Gender", columnDefinition = "ENUM('M', 'F', 'D')")
	private Gender gender;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "Profs")
    private List<ModuleEntity> modules;

	public CollegeEmployeeEntity(String firstName, String lastName, String title, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.gender = gender;
	}

	public enum Gender {
		M,
		F,
		D,
	}
}
