package de.bonprix.vaadin.bean.grid;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class ComboBoxRendererProperties<PROPERTYTYPE> extends AbstractComboBoxRendererProperties<PROPERTYTYPE> {

	private boolean nullSelectionAllowed = true;

	public boolean isNullSelectionAllowed() {
		return this.nullSelectionAllowed;
	}

	public void setNullSelectionAllowed(boolean nullSelectionAllowed) {
		this.nullSelectionAllowed = nullSelectionAllowed;
	}

}
