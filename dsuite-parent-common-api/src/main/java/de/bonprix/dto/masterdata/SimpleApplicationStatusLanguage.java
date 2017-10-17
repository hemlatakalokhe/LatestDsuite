package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.AbstractI18NLanguageElement;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApplicationStatusLanguage extends AbstractI18NLanguageElement {

	private static final long serialVersionUID = 252124959193796372L;

}
