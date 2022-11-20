package de.hscoburg.modulhandbuchbackend.model.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import de.hscoburg.modulhandbuchbackend.converters.CollegeEmployeeEntityGenderDatabaseConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "college_employee")
public class CollegeEmployeeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "title")
	private String title;

	@Convert(converter = CollegeEmployeeEntityGenderDatabaseConverter.class)
	@Column(name = "gender", columnDefinition = "ENUM('Herr', 'Frau', NULL) DEFAULT NULL")
	private Gender gender;

	@Column(name = "email")
	private String email;

	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
		name = "prof",
		joinColumns = @JoinColumn(name = "pk_college_employee_pk_unique_id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "pk_module_pk_unique_id", nullable = false))
    private List<ModuleEntity> modules;

	public CollegeEmployeeEntity(String firstName, String lastName, String title, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", Objects.toString(this.title, ""), this.firstName, this.lastName).trim();
	}

	@AllArgsConstructor
	@Getter
	public enum Gender {
		M("Herr"),
		F("Frau"),
		D(""),
		;

		private String text;

		public static Gender fromString(String text) {
			return Arrays.stream(Gender.values())
				.filter(gender -> gender.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
}
