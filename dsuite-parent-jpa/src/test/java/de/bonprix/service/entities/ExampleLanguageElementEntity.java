package de.bonprix.service.entities;

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
import de.bonprix.service.I18NLanguageElementEntity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "EXAMPLE_L", schema = "TEST")
@SequenceGenerator(schema = "TEST", name = "SEQ_EXAMPLE_L", sequenceName = "TEST.SEQ_EXAMPLE_L", allocationSize = 1)
public class ExampleLanguageElementEntity implements
		I18NLanguageElementEntity<ExampleLanguageContainerEntity, ExampleLanguageElementEntity, ExampleLanguageContainer, ExampleLanguageElement> {

	@Id
	@GeneratedValue(generator = "SEQ_EXAMPLE_L", strategy = GenerationType.SEQUENCE)
	@Column(name = "EXAMPLE_L_ID")
	private Long id;

	@Column(name = "LANGUAGE_ID")
	private Long languageId;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAMPLE_ID")
	private ExampleLanguageContainerEntity exampleLanguageContainerEntity;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public Long getLanguageId() {
		return this.languageId;
	}

	@Override
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ExampleLanguageContainerEntity getExampleLanguageContainerEntity() {
		return this.exampleLanguageContainerEntity;
	}

	public void setExampleLanguageContainerEntity(ExampleLanguageContainerEntity exampleLanguageContainerEntity) {
		this.exampleLanguageContainerEntity = exampleLanguageContainerEntity;
	}

	@Override
	public void updateEntity(ExampleLanguageElement dto) {
		setName(dto.getName());
	}

	@Override
	public void updateDto(ExampleLanguageElement dto) {
		dto.setName(getName());
	}

	@Override
	public void setLanguageContainer(ExampleLanguageContainerEntity languageContainer) {
		setExampleLanguageContainerEntity(languageContainer);
	}

}
