package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.AbstractI18NLanguageElement;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApplicationGroupLanguage extends AbstractI18NLanguageElement {

	private static final long serialVersionUID = 3743537449619077358L;

}
