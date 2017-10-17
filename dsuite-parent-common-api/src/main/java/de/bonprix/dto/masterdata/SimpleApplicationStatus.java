package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApplicationStatus extends AbstractApplicationStatus<SimpleApplicationStatusLanguage> {

	private static final long serialVersionUID = 5789923235376675350L;

}
