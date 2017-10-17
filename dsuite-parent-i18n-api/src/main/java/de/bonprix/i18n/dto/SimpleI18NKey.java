package de.bonprix.i18n.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleI18NKey extends AbstractI18NKey<SimpleI18NTranslation> {

	private static final long serialVersionUID = 4789918119947677501L;

	private String key;

	private boolean global;

	private Set<SimpleI18NTranslation> translations;

	private Set<Long> applicationIds;

	private Long optlock;

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public boolean isGlobal() {
		return this.global;
	}

	@Override
	public void setGlobal(boolean global) {
		this.global = global;
	}

	@Override
	public Set<SimpleI18NTranslation> getTranslations() {
		return this.translations;
	}

	@Override
	public void setTranslations(Set<SimpleI18NTranslation> translations) {
		this.translations = translations;
	}

	@Override
	public Set<Long> getApplicationIds() {
		return this.applicationIds;
	}

	@Override
	public void setApplicationIds(Set<Long> applicationIds) {
		this.applicationIds = applicationIds;
	}

	@Override
	public Long getOptlock() {
		return this.optlock;
	}

	@Override
	public void setOptlock(Long optlock) {
		this.optlock = optlock;
	}

}
