package de.hscoburg.modulhandbuchbackend.model.entities;

import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hscoburg.modulhandbuchbackend.converters.SpoEntityDegreeDatabaseConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Convert(converter = SpoEntityDegreeDatabaseConverter.class)
	@Column(name = "degree", nullable = false, columnDefinition = "ENUM('Bachelor', 'Master') DEFAULT 'Bachelor'")
	private Degree degree;

	public SpoEntity(String link, LocalDate startDate, LocalDate endDate, String course, Degree degree) {
		this.link = link;
		this.startDate = startDate;
		this.endDate = endDate;
		this.course = course;
		this.degree = degree;
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public enum Degree {
		BACHELOR("Bachelor"),
		MASTER("Master"),
		;

		private final String text;

		public static Degree fromString(String text) {
			return Arrays.stream(Degree.values())
				.filter(degree -> degree.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
}
