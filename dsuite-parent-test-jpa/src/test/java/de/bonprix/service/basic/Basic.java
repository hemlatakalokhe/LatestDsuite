package de.bonprix.service.basic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.Entity;
import de.bonprix.dto.HasOptlock;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basic extends Entity implements HasOptlock {

	private static final long serialVersionUID = 641040914541745025L;

	private Long optlock;
	private Long property;

	@Override
	public Long getOptlock() {
		return this.optlock;
	}

	@Override
	public void setOptlock(Long optlock) {
		this.optlock = optlock;
	}

	public Long getProperty() {
		return this.property;
	}

	public void setProperty(Long property) {
		this.property = property;
	}

}
