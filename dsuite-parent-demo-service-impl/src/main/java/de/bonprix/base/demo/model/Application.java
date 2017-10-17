package de.bonprix.base.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")

@Entity
@Table(name = "MD_APPLICATION",
		schema = "DS_GLOBAL")
@SequenceGenerator(schema = "DS_GLOBAL",
		name = "SEQ_MD_APPLICATION",
		sequenceName = "DS_GLOBAL.SEQ_MD_APPLICATION",
		allocationSize = 1)
public class Application {

	@Id
	@GeneratedValue(generator = "SEQ_MD_APPLICATION",
			strategy = GenerationType.SEQUENCE)
	@Column(name = "MD_APPLICATION_ID")
	private Long id;

	@Column(name = "MD_APPLICATION_NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MD_APPLICATIONTYPE_ID")
	private ApplicationTypeEntity applicationType;
	
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ApplicationTypeEntity getApplicationType() {
		return this.applicationType;
	}

	public void setApplicationType(final ApplicationTypeEntity applicationType) {
		this.applicationType = applicationType;
	}
}
