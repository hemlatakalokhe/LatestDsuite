package de.bonprix.service.basiclanguage;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import de.bonprix.service.BasicTableDtoConverter;
import de.bonprix.service.I18NLanguageContainerEntity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "BASIC_LANGUAGE", schema = "TEST")
@SequenceGenerator(schema = "TEST", name = "SEQ_BASIC_LANGUAGE", sequenceName = "TEST.SEQ_BASIC_LANGUAGE", allocationSize = 1)
public class BasicLanguageEntity implements
		I18NLanguageContainerEntity<BasicLanguageEntity, BasicLanguageLanguageEntity, BasicLanguage, BasicLanguageLanguage>,
		BasicTableDtoConverter<BasicLanguage> {

	@Id
	@GeneratedValue(generator = "SEQ_BASIC_LANGUAGE", strategy = GenerationType.SEQUENCE)
	@Column(name = "BASIC_LANGUAGE_ID")
	private Long id;

	@Column(name = "PROPERTY")
	private Long property;

	@OneToMany(orphanRemoval = true, mappedBy = "basicLanguage", cascade = CascadeType.ALL)
	private Set<BasicLanguageLanguageEntity> basicLanguageLanguages;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public Long getProperty() {
		return this.property;
	}

	public void setProperty(Long property) {
		this.property = property;
	}

	public Set<BasicLanguageLanguageEntity> getBasicLanguageLanguages() {
		return this.basicLanguageLanguages;
	}

	public void setBasicLanguageLanguages(Set<BasicLanguageLanguageEntity> basicLanguageLanguages) {
		this.basicLanguageLanguages = basicLanguageLanguages;
	}

	@Override
	public Set<BasicLanguageLanguageEntity> getLanguageElementEntities() {
		return getBasicLanguageLanguages();
	}

	@Override
	public void setLanguageElementEntities(Set<BasicLanguageLanguageEntity> languageElements) {
		setBasicLanguageLanguages(languageElements);
	}

	@Override
	public void updateDto(BasicLanguage containerDto) {
		containerDto.setProperty(getProperty());
	}

	@Override
	public void updateEntity(BasicLanguage containerDto) {
		setProperty(containerDto.getProperty());
	}

}
