package de.bonprix.service.fetchlanguage;

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
import de.bonprix.service.FetchTableDtoConverter;
import de.bonprix.service.I18NLanguageContainerEntity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "BASIC_LANGUAGE", schema = "TEST")
@SequenceGenerator(schema = "TEST", name = "SEQ_BASIC_LANGUAGE", sequenceName = "TEST.SEQ_BASIC_LANGUAGE", allocationSize = 1)
public class FetchLanguageEntity implements
		I18NLanguageContainerEntity<FetchLanguageEntity, FetchLanguageLanguageEntity, FetchLanguage, FetchLanguageLanguage>,
		FetchTableDtoConverter<FetchLanguage, FetchLanguageFetchOptions> {

	@Id
	@GeneratedValue(generator = "SEQ_BASIC_LANGUAGE", strategy = GenerationType.SEQUENCE)
	@Column(name = "BASIC_LANGUAGE_ID")
	private Long id;

	@Column(name = "PROPERTY")
	private Long property;

	@OneToMany(orphanRemoval = true, mappedBy = "fetchLanguage", cascade = CascadeType.ALL)
	private Set<FetchLanguageLanguageEntity> fetchLanguageLanguages;

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

	public Set<FetchLanguageLanguageEntity> getFetchLanguageLanguages() {
		return this.fetchLanguageLanguages;
	}

	public void setFetchLanguageLanguages(Set<FetchLanguageLanguageEntity> fetchLanguageLanguages) {
		this.fetchLanguageLanguages = fetchLanguageLanguages;
	}

	@Override
	public Set<FetchLanguageLanguageEntity> getLanguageElementEntities() {
		return getFetchLanguageLanguages();
	}

	@Override
	public void setLanguageElementEntities(Set<FetchLanguageLanguageEntity> languageElements) {
		setFetchLanguageLanguages(languageElements);
	}

	@Override
	public void updateDto(FetchLanguage dto) {
		updateDto(dto, new FetchLanguageFetchOptions());
	}

	@Override
	public void updateEntity(FetchLanguage dto) {
		setProperty(dto.getProperty());
	}

	@Override
	public void updateDto(FetchLanguage dto, FetchLanguageFetchOptions fetchOptions) {
		dto.setProperty(getProperty());
	}

}
