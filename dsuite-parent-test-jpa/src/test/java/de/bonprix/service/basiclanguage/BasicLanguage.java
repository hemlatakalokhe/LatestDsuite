package de.bonprix.service.basiclanguage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.masterdata.AbstractApplicationType;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicLanguage extends AbstractApplicationType<BasicLanguageLanguage> {

	private static final long serialVersionUID = 641040914541745025L;

	private Long property;

	public Long getProperty() {
		return this.property;
	}

	public void setProperty(Long property) {
		this.property = property;
	}

}
