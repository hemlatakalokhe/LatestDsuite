package de.bonprix.service.basiclanguage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.masterdata.SimpleApplicationTypeLanguage;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicLanguageLanguage extends SimpleApplicationTypeLanguage {

	private static final long serialVersionUID = 6011398851841106991L;

}
