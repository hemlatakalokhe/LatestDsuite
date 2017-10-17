package de.bonprix.vaadin.searchfilter.configurable;


public class SearchFilterElement {

	private final String id;
	private final String captionKey;
	private final Class<?> beantype;
	private final SearchFilterElementType type;

	public SearchFilterElement(final String id, final String captionKey, final Class<?> beantype, final SearchFilterElementType type) {
		super();
		this.id = id;
		this.captionKey = captionKey;
		this.beantype = beantype;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getCaptionKey() {
		return captionKey;
	}

	public Class<?> getBeantype() {
		return beantype;
	}

	public SearchFilterElementType getType() {
		return type;
	}
}
