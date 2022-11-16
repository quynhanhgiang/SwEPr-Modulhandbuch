package de.hscoburg.modulhandbuchbackend.model.entities;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.hscoburg.modulhandbuchbackend.converters.VariationEntityCategoryDatabaseConverter;
import de.hscoburg.modulhandbuchbackend.model.ids.VariationEntityId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(VariationEntityId.class)
@Table(name = "module_has_spo")
public class VariationEntity {

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pk_module_pk_unique_id", nullable = false)
	private ModuleEntity module;

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pk_spo_pk_unique_id", nullable = false)
	private SpoEntity spo;

	@Id
	@Column(name = "pk_semester", nullable = false)
	private Integer semester;

	@Column(name = "sws", nullable = false)
	private Integer sws;

	@Column(name = "ects", nullable = false)
	private Integer ects;

	@Column(name = "workload")
	private String workLoad;

	@Convert(converter = VariationEntityCategoryDatabaseConverter.class)
	@Column(name = "category", columnDefinition = "ENUM('Pflichtfach', 'Wahlpflichtfach', 'Schlüsselqualifikation' DEFAULT NULL")
	private Category category;

	@AllArgsConstructor
	@Getter
	public enum Category {
		MANDATORY("Pflichtfach"),
		ELECTIVE("Wahlpflichtfach"),
		OPTIONAL("Schlüsselqualifikation"),
		;

		private final String text;

		public static Category fromString(String text) {
			return Arrays.stream(Category.values())
				.filter(category -> category.text.equals(text))
				.findAny()
				.orElse(null);
		}

		@Override
		public String toString() {
			return this.text;
		}
	}
}
