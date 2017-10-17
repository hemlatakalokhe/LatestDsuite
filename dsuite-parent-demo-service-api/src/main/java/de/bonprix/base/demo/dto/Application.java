package de.bonprix.base.demo.dto;

import de.bonprix.dto.Entity;
import de.bonprix.model.PropertyDescriptor;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class Application extends Entity {

	private static final long serialVersionUID = 1L;

	public static final PropertyDescriptor ID = PropertyDescriptor.of("id");
	public static final PropertyDescriptor NAME = PropertyDescriptor.of("name");
	public static final PropertyDescriptor APPLICATION_TYPE_NAME_KEY = PropertyDescriptor.of("applicationType.nameKey");

	private String name;

	private String applicationTypeNameKey;

	private Long optlock;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getApplicationTypeNameKey() {
		return this.applicationTypeNameKey;
	}

	public void setApplicationTypeNameKey(final String applicationTypeNameKey) {
		this.applicationTypeNameKey = applicationTypeNameKey;
	}

	public Long getOptlock() {
		return this.optlock;
	}

	public void setOptlock(final Long optlock) {
		this.optlock = optlock;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
