package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@GeneratePojoBuilder(intoPackage = "*.builder")
public class SimpleLanguage extends AbstractLanguageContainerEntity<SimpleLanguageLanguage> {

}
