package de.hscoburg.modulhandbuchbackend.model.entities;

import java.time.LocalDate;

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

/**
 * This class is the representation of the database table `spo`.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "spo")
public class SpoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_unique_id")
	private Integer id;

	@Column(name = "link", nullable = false)
	private String link;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "course", nullable = false)
	private String course;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_degree_pk_unique_id", nullable = false)
	private DegreeEntity degree;
}
