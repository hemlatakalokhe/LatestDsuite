package de.bonprix.vaadin.bean.grid;

import java.util.List;

public abstract class AbstractComboBoxRendererProperties<PROPERTYTYPE> extends RendererProperties<PROPERTYTYPE> {

	private Class<PROPERTYTYPE> clazz;
	private List<PROPERTYTYPE> options;
	private String itemIdPropertyId = "id";
	private String itemCaptionPropertyId = "name";
	private int pageSize = 5;
	private String inputPromptKey = "YOU_CAN_TYPE_HERE";

	public Class<PROPERTYTYPE> getClazz() {
		return this.clazz;
	}

	public void setClazz(Class<PROPERTYTYPE> clazz) {
		this.clazz = clazz;
	}

	public List<PROPERTYTYPE> getOptions() {
		return this.options;
	}

	public void setOptions(List<PROPERTYTYPE> options) {
		this.options = options;
	}

	public String getItemIdPropertyId() {
		return this.itemIdPropertyId;
	}

	public void setItemIdPropertyId(String itemIdPropertyId) {
		this.itemIdPropertyId = itemIdPropertyId;
	}

	public String getItemCaptionPropertyId() {
		return this.itemCaptionPropertyId;
	}

	public void setItemCaptionPropertyId(String itemCaptionPropertyId) {
		this.itemCaptionPropertyId = itemCaptionPropertyId;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getInputPromptKey() {
		return this.inputPromptKey;
	}

	public void setInputPromptKey(String inputPromptKey) {
		this.inputPromptKey = inputPromptKey;
	}

}
