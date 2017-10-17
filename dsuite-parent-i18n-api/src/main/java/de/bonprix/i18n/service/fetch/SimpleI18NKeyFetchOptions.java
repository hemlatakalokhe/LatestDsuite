package de.bonprix.i18n.service.fetch;

import javax.ws.rs.QueryParam;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class SimpleI18NKeyFetchOptions {

	/**
	 * @description fetch applications property of returning i18n key(s)
	 */
	@QueryParam("fetchTranslations")
	private boolean fetchTranslations = false;

	public boolean isFetchTranslations() {
		return this.fetchTranslations;
	}

	public void setFetchTranslations(boolean fetchTranslations) {
		this.fetchTranslations = fetchTranslations;
	}

	public SimpleI18NKeyFetchOptions withFetchAll() {
		setFetchTranslations(true);
		return this;
	}

}