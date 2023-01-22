package de.hscoburg.modulhandbuchbackend.model.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is the representation of the database table `college_employee`.
 */
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

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_gender_pk_unique_id", nullable = false)
	private GenderEntity gender;

	@Column(name = "email", nullable = false)
	private String email;

	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "profs")
	private List<ModuleEntity> modules;

	public String toString() {
		return String.format("%s %s %s", Objects.toString(this.title, ""), this.firstName, this.lastName).trim();
	}
}
