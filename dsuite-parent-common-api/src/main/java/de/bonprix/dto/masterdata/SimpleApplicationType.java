package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApplicationType extends AbstractApplicationType<SimpleApplicationTypeLanguage> {

	private static final long serialVersionUID = -1462726420674980718L;

}
