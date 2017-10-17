package de.bonprix.vaadin.fluentui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import de.bonprix.I18N;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.SimpleLanguageLanguage;

/**
 * Provides a fluent API to create a components array containing language
 * textfields for specific properties
 * 
 * @author thacht
 */
public class LanguageTextFields<CONFIG extends LanguageTextFields<CONFIG, CONTAINER, ELEMENT>, CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTextFields.class);

	enum SortMode {
		PROPERTY_LANGUAGE, LANGUAGE_PROPERTY;
	}

	private final Class<ELEMENT> propertyClazz;
	private final BeanFieldGroup<CONTAINER> fieldGroup;
	private HashMap<String, String> propertyNameCaptionPrefixMap = new HashMap<>();
	private SortMode sortMode = SortMode.PROPERTY_LANGUAGE;

	protected LanguageTextFields(Class<ELEMENT> propertyClazz, final BeanFieldGroup<CONTAINER> fieldGroup) {
		this.propertyClazz = propertyClazz;
		this.fieldGroup = fieldGroup;
	}

	@SuppressWarnings("unchecked")
	public CONFIG bind(final String propertyName, final String captionPrefixKey, Object... objects) {
		this.propertyNameCaptionPrefixMap.put(propertyName, I18N.get(captionPrefixKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG bind(final String propertyName) {
		this.propertyNameCaptionPrefixMap.put(propertyName, "");
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG sortMode(final SortMode sortMode) {
		this.sortMode = sortMode;
		return (CONFIG) this;
	}

	public Component[] get() {
		CONTAINER container = this.fieldGroup.getItemDataSource()
			.getBean();
		// list for textfields sorted by language, property
		List<Component> components = new ArrayList<>();
		// map for textfields sorted by property, language
		Map<String, List<TextField>> propertyTextFieldMap = new HashMap<>();
		for (SimpleLanguage language : I18N.getAvailableLanguages()) {
			ELEMENT languageElement = getLanguageElement(container, language);
			BeanFieldGroup<ELEMENT> languageFieldGroup = new BeanFieldGroup<>(this.propertyClazz);
			languageFieldGroup.setBuffered(false);
			languageFieldGroup.setItemDataSource(languageElement);
			for (Entry<String, String> propertyNameCaptionPrefixEntry : this.propertyNameCaptionPrefixMap.entrySet()) {
				String propertyName = propertyNameCaptionPrefixEntry.getKey();
				String captionPrefix = propertyNameCaptionPrefixEntry.getValue();

				TextField textField = FluentUI.textField()
					.caption(captionPrefix + " " + I18N.get(language, SimpleLanguageLanguage::getName))
					.bind(languageFieldGroup, propertyName)
					.get();

				if (propertyTextFieldMap.get(propertyName) == null) {
					propertyTextFieldMap.put(propertyName, new ArrayList<>());
				}
				propertyTextFieldMap.get(propertyName)
					.add(textField);
				components.add(textField);
			}
		}

		if (SortMode.PROPERTY_LANGUAGE.equals(this.sortMode)) {
			components.clear();
			for (Entry<String, List<TextField>> propertyTextFieldEntry : propertyTextFieldMap.entrySet()) {
				for (TextField textField : propertyTextFieldEntry.getValue()) {
					components.add(textField);
				}
			}
		}

		return components.toArray(new Component[components.size()]);
	}

	private ELEMENT getLanguageElement(CONTAINER container, SimpleLanguage language) {
		ELEMENT languageElement = container.getLanguageElement(language.getId());
		if (languageElement == null) {
			try {
				languageElement = this.propertyClazz.getConstructor()
					.newInstance();
				languageElement.setLanguageId(language.getId());
				container.addI18NLanguageElement(languageElement);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				LOGGER.error(	"Couldn't create languageElement of beantype ELEMENT for languageId: " + language.getId(),
								e);
			}
		}
		return languageElement;
	}

}
