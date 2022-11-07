package de.hscoburg.modulhandbuchbackend.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "MediaType")
public class MediaTypeEntity {
	// TODO

	// TODO only temp id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_uniqueId")
	private Integer id;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "Module_has_MediaType")
    private List<ModuleEntity> modules;
}
