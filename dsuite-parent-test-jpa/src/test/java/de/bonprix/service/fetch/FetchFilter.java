package de.bonprix.service.fetch;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchFilter extends Paged implements IdsFilter {

	/**
	 * @description fetchIds filter list
	 */
	@QueryParam("filterFetchIds")
	private List<Long> fetchIds;

	public FetchFilter() {
		super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
	}

	public FetchFilter(Integer page, Integer pageSize) {
		super(page, pageSize);
	}

	public List<Long> getFetchIds() {
		if (this.fetchIds == null) {
			this.fetchIds = new ArrayList<>();
		}
		return this.fetchIds;
	}

	public void setFetchIds(List<Long> fetchIds) {
		this.fetchIds = fetchIds;
	}

	@Override
	public void setIds(List<Long> ids) {
		setFetchIds(ids);
	}

}
