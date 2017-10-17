package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.Entity;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractApplication<TYPE extends AbstractApplicationType<TYPELANGUAGE>, TYPELANGUAGE extends SimpleApplicationTypeLanguage, STATUS extends AbstractApplicationStatus<STATUSLANGUAGE>, STATUSLANGUAGE extends SimpleApplicationStatusLanguage, GROUP extends AbstractApplicationGroup<GROUPLANGUAGE>, GROUPLANGUAGE extends SimpleApplicationGroupLanguage>
		extends Entity {

	private static final long serialVersionUID = 1L;

	private String name;

	private TYPE applicationType;

	private STATUS applicationStatus;

	private GROUP applicationGroup;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public TYPE getApplicationType() {
		return this.applicationType;
	}

	public void setApplicationType(TYPE applicationType) {
		this.applicationType = applicationType;
	}

	public STATUS getApplicationStatus() {
		return this.applicationStatus;
	}

	public void setApplicationStatus(STATUS applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public GROUP getApplicationGroup() {
		return this.applicationGroup;
	}

	public void setApplicationGroup(GROUP applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

}
