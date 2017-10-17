package de.bonprix.i18n.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.Entity;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractI18NKey<TRANSLATION extends SimpleI18NTranslation> extends Entity {

	private static final long serialVersionUID = 4789918119947677501L;

	private String key;

	private boolean global;

	private Set<TRANSLATION> translations;

	private Set<Long> applicationIds;

	private Long optlock;

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isGlobal() {
		return this.global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public Set<TRANSLATION> getTranslations() {
		return this.translations;
	}

	public void setTranslations(Set<TRANSLATION> translations) {
		this.translations = translations;
	}

	public Set<Long> getApplicationIds() {
		return this.applicationIds;
	}

	public void setApplicationIds(Set<Long> applicationIds) {
		this.applicationIds = applicationIds;
	}

	public Long getOptlock() {
		return this.optlock;
	}

	public void setOptlock(Long optlock) {
		this.optlock = optlock;
	}

}
