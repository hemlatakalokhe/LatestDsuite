package de.bonprix.vaadin.searchfilter.configurable;

public class SearchFilterElementLayout {

	private String id;
	private SearchFilterGroup filterGroup;
	private int posX;
	private int posY;
	private int width;
	private int height;

	public SearchFilterElementLayout() {
		super();
	}

	public SearchFilterElementLayout(final SearchFilterElementLayout other) {
		super();

		this.id = other.id;
		this.filterGroup = other.filterGroup;
		this.posX = other.posX;
		this.posY = other.posY;
		this.width = other.width;
		this.height = other.height;
	}

	public SearchFilterElementLayout(final String id, final SearchFilterGroup filterGroup, final int posX, final int posY, final int width, final int height) {
		super();
		this.id = id;
		this.filterGroup = filterGroup;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(final int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(final int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public SearchFilterGroup getFilterGroup() {
		return filterGroup;
	}

	public void setFilterGroup(final SearchFilterGroup filterGroup) {
		this.filterGroup = filterGroup;
	}

}
