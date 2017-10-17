package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApplication extends
		AbstractApplication<SimpleApplicationType, SimpleApplicationTypeLanguage, SimpleApplicationStatus, SimpleApplicationStatusLanguage, SimpleApplicationGroup, SimpleApplicationGroupLanguage> {

	private static final long serialVersionUID = 3917738127473465167L;

}
