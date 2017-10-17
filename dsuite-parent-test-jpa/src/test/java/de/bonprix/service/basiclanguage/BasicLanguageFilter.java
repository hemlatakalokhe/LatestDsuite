package de.bonprix.service.basiclanguage;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.IdsFilter;
import de.bonprix.model.Paged;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicLanguageFilter extends Paged implements IdsFilter {

	/**
	 * @description exampleLanguageIds filter list
	 */
	@QueryParam("filterBasicLanguageIds")
	private List<Long> exampleLanguageIds;

	public BasicLanguageFilter() {
		super(DEFAULT_PAGE, DEFAULT_PAGESIZE);
	}

	public BasicLanguageFilter(Integer page, Integer pageSize) {
		super(page, pageSize);
	}

	public List<Long> getBasicLanguageIds() {
		if (this.exampleLanguageIds == null) {
			this.exampleLanguageIds = new ArrayList<>();
		}
		return this.exampleLanguageIds;
	}

	public void setBasicLanguageIds(List<Long> exampleLanguageIds) {
		this.exampleLanguageIds = exampleLanguageIds;
	}

	@Override
	public void setIds(List<Long> ids) {
		setBasicLanguageIds(ids);
	}

}
