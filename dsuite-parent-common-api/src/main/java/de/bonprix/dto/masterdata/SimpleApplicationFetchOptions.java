package de.bonprix.dto.masterdata;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.FetchAllOptions;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleApplicationFetchOptions implements FetchAllOptions {

	/**
	 * @description fetch applicationType property of returning application(s)
	 */
	@QueryParam("fetchApplicationType")
	private boolean fetchApplicationType = false;

	/**
	 * @description fetch applicationStatus property of returning application(s)
	 */
	@QueryParam("fetchApplicationStatus")
	private boolean fetchApplicationStatus = false;

	/**
	 * @description fetch applicationGroup property of returning application(s)
	 */
	@QueryParam("fetchApplicationGroup")
	private boolean fetchApplicationGroup = false;

	public boolean isFetchApplicationType() {
		return this.fetchApplicationType;
	}

	public void setFetchApplicationType(boolean fetchApplicationType) {
		this.fetchApplicationType = fetchApplicationType;
	}

	public boolean isFetchApplicationStatus() {
		return this.fetchApplicationStatus;
	}

	public void setFetchApplicationStatus(boolean fetchApplicationStatus) {
		this.fetchApplicationStatus = fetchApplicationStatus;
	}

	public boolean isFetchApplicationGroup() {
		return this.fetchApplicationGroup;
	}

	public void setFetchApplicationGroup(boolean fetchApplicationGroup) {
		this.fetchApplicationGroup = fetchApplicationGroup;
	}

	public SimpleApplicationFetchOptions withFetchAll() {
		setFetchAll();
		return this;
	}

	@Override
	public void setFetchAll() {
		setFetchApplicationType(true);
		setFetchApplicationStatus(true);
		setFetchApplicationGroup(true);
	}

}
