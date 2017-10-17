package de.bonprix.i18n.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleI18NTranslation extends Entity {

	private static final long serialVersionUID = -8503867790989047389L;

	private Long languageId;

	private String translation;

	private Long optlock;

	public Long getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getTranslation() {
		return this.translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public Long getOptlock() {
		return this.optlock;
	}

	public void setOptlock(Long optlock) {
		this.optlock = optlock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getLanguageId() == null) ? 0 : this.getLanguageId()
			.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SimpleI18NTranslation other = (SimpleI18NTranslation) obj;

		return Objects.equals(this.getLanguageId(), other.getLanguageId());
	}

}
