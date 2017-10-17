package de.bonprix.vaadin.bean.grid;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class ComboBoxMultiselectRendererProperties<PROPERTYTYPE> extends ComboBoxRendererProperties<PROPERTYTYPE> {

	private String selectAllTextKey = "SELECT_ALL";
	private String deselectAllTextKey = "DESELECT_ALL";

	public String getSelectAllTextKey() {
		return this.selectAllTextKey;
	}

	public void setSelectAllTextKey(String selectAllTextKey) {
		this.selectAllTextKey = selectAllTextKey;
	}

	public String getDeselectAllTextKey() {
		return this.deselectAllTextKey;
	}

	public void setDeselectAllTextKey(String deselectAllTextKey) {
		this.deselectAllTextKey = deselectAllTextKey;
	}

}
