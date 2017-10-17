package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.AbstractI18NLanguageContainer;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@GeneratePojoBuilder(intoPackage = "*.builder")
public class AbstractLanguageContainerEntity<ELEMENT extends SimpleLanguageLanguage>
		extends AbstractI18NLanguageContainer<ELEMENT> {

	private String isoShort;

	public String getIsoShort() {
		return this.isoShort;
	}

	public void setIsoShort(String isoShort) {
		this.isoShort = isoShort;
	}
}
