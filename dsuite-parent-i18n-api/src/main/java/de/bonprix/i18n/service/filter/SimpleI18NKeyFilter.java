package de.bonprix.i18n.service.filter;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import de.bonprix.model.Paged;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class SimpleI18NKeyFilter extends Paged {

	/**
	 * @description i18nKeyIds filter list
	 */
	@QueryParam("filterI18nKeyIds")
	private List<Long> i18NKeyIds;

	/**
	 * @description applicationIds filter list
	 */
	@QueryParam("filterApplicationIds")
	private List<Long> applicationIds;

	/**
	 * @description languageIds filter list
	 */
	@QueryParam("filterLanguageIds")
	private List<Long> languageIds;

	/**
	 * @description keys filter list
	 */
	@QueryParam("filterKeys")
	private List<String> keys;

	public SimpleI18NKeyFilter() {
		super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
	}

	public SimpleI18NKeyFilter(Integer page, Integer pageSize) {
		super(page, pageSize);
	}

	public List<Long> getI18NKeyIds() {
		if (this.i18NKeyIds == null) {
			this.i18NKeyIds = new ArrayList<>();
		}
		return this.i18NKeyIds;
	}

	public void setI18NKeyIds(List<Long> i18NKeyIds) {
		this.i18NKeyIds = i18NKeyIds;
	}

	public List<Long> getApplicationIds() {
		if (this.applicationIds == null) {
			this.applicationIds = new ArrayList<>();
		}
		return this.applicationIds;
	}

	public void setApplicationIds(List<Long> applicationIds) {
		this.applicationIds = applicationIds;
	}

	public List<Long> getLanguageIds() {
		if (this.languageIds == null) {
			this.languageIds = new ArrayList<>();
		}
		return this.languageIds;
	}

	public void setLanguageIds(List<Long> languageIds) {
		this.languageIds = languageIds;
	}

	public List<String> getKeys() {
		if (this.keys == null) {
			this.keys = new ArrayList<>();
		}
		return this.keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

}