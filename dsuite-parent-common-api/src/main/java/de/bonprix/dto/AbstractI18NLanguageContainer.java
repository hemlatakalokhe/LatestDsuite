package de.bonprix.dto;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Abstract class for containing language translation elements
 * 
 * @author thacht
 *
 * @param <ELEMENT>
 *            language translation element
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractI18NLanguageContainer<ELEMENT extends I18NLanguageElement> extends Entity
		implements I18NLanguageContainer<ELEMENT> {
	private static final long serialVersionUID = 1L;

	private List<ELEMENT> languageElements;

	private transient boolean initialized = false;
	private transient Map<Long, ELEMENT> languageIdElementMap;

	@Override
	public ELEMENT getLanguageElement(long languageId) {
		if (!this.initialized) {
			initialize();
		}

		return this.languageIdElementMap.get(languageId);
	}

	private void initialize() {
		this.languageIdElementMap = new HashMap<>();

		if (getLanguageElements() != null) {
			for (ELEMENT element : getLanguageElements()) {
				this.languageIdElementMap.put(element.getLanguageId(), element);
			}
		}

		this.initialized = true;
	}

	@Override
	public Long getId() {
		return super.getId();
	}

	@Override
	public void addI18NLanguageElement(ELEMENT bean) {
		if (getLanguageElements() == null) {
			setLanguageElements(new ArrayList<>());
		}
		if (!this.initialized) {
			initialize();
		}
		getLanguageElements().add(bean);
		this.languageIdElementMap.put(bean.getLanguageId(), bean);
	}

	@Override
	public List<ELEMENT> getLanguageElements() {
		return this.languageElements;
	}

	@Override
	public void setLanguageElements(List<ELEMENT> languageElements) {
		this.languageElements = languageElements;
	}

	@Override
	public String toString() {
		ELEMENT element = getLanguageElement(301);

		if (element == null) {
			return MessageFormat.format("missing translation for id: {0}", getId());
		}
		return element.getName();
	}

}
