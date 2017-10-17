package de.bonprix.service.fetchlanguage;

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
import javax.persistence.Version;

import de.bonprix.service.I18NLanguageElementEntity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "BASIC_LANGUAGE_L", schema = "TEST")
@SequenceGenerator(schema = "TEST", name = "SEQ_BASIC_LANGUAGE_L", sequenceName = "TEST.SEQ_BASIC_LANGUAGE_L", allocationSize = 1)
public class FetchLanguageLanguageEntity implements
		I18NLanguageElementEntity<FetchLanguageEntity, FetchLanguageLanguageEntity, FetchLanguage, FetchLanguageLanguage> {

	@Id
	@GeneratedValue(generator = "SEQ_BASIC_LANGUAGE_L", strategy = GenerationType.SEQUENCE)
	@Column(name = "BASIC_LANGUAGE_L_ID")
	private Long id;

	@Column(name = "LANGUAGE_ID")
	private Long languageId;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BASIC_LANGUAGE_ID")
	private FetchLanguageEntity fetchLanguage;

	@Version
	@Column(name = "OPTLOCK", nullable = false, columnDefinition = "Number(18) default '0'")
	private Long optlock;

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

	public FetchLanguageEntity getFetchLanguage() {
		return this.fetchLanguage;
	}

	public void setFetchLanguage(FetchLanguageEntity fetchLanguage) {
		this.fetchLanguage = fetchLanguage;
	}

	@Override
	public void updateEntity(FetchLanguageLanguage dto) {
		setName(dto.getName());
	}

	@Override
	public void updateDto(FetchLanguageLanguage dto) {
		dto.setName(getName());
	}

	@Override
	public void setLanguageContainer(FetchLanguageEntity languageContainer) {
		setFetchLanguage(languageContainer);
	}

}
