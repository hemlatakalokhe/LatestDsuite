package de.bonprix.service.basic;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicFilter extends Paged implements IdsFilter {

	/**
	 * @description basicIds filter list
	 */
	@QueryParam("filterBasicIds")
	private List<Long> basicIds;

	public BasicFilter() {
		super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
	}

	public BasicFilter(Integer page, Integer pageSize) {
		super(page, pageSize);
	}

	public List<Long> getBasicIds() {
		if (this.basicIds == null) {
			this.basicIds = new ArrayList<>();
		}
		return this.basicIds;
	}

	public void setBasicIds(List<Long> basicIds) {
		this.basicIds = basicIds;
	}

	@Override
	public void setIds(List<Long> ids) {
		setBasicIds(ids);
	}

}
