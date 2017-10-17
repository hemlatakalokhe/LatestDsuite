package de.bonprix.model;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

public class Paged {

	protected static final int DEFAULT_PAGE = 0;
	protected static final int DEFAULT_PAGESIZE = 1000;

	@NotNull
	@QueryParam("page")
	/**
	 * @description page of current paging
	 * @default 0
	 */
	private Integer page;

	@NotNull
	@QueryParam("pagesize")
	/**
	 * @description amount of items per page
	 * @default 1000
	 */
	private Integer pageSize;

	public Paged() {
		// Empty constructor
	}

	public Paged(final Integer page, final Integer pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	public static final Paged defaultPaged() {
		return new Paged(Paged.DEFAULT_PAGE, Paged.DEFAULT_PAGESIZE);
	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}

	public Paged withPage(final Integer page) {
		setPage(page);
		return this;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Paged withPageSize(final Integer pageSize) {
		setPageSize(pageSize);
		return this;
	}

}