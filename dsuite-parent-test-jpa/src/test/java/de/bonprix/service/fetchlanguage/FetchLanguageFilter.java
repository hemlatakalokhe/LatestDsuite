package de.bonprix.service.fetchlanguage;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchLanguageFilter extends Paged implements IdsFilter {

	/**
	 * @description exampleLanguageIds filter list
	 */
	@QueryParam("filterFetchLanguageIds")
	private List<Long> exampleLanguageIds;

	public FetchLanguageFilter() {
		super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
	}

	public FetchLanguageFilter(Integer page, Integer pageSize) {
		super(page, pageSize);
	}

	public List<Long> getFetchLanguageIds() {
		if (this.exampleLanguageIds == null) {
			this.exampleLanguageIds = new ArrayList<>();
		}
		return this.exampleLanguageIds;
	}

	public void setFetchLanguageIds(List<Long> exampleLanguageIds) {
		this.exampleLanguageIds = exampleLanguageIds;
	}

	@Override
	public void setIds(List<Long> ids) {
		setFetchLanguageIds(ids);
	}

}
