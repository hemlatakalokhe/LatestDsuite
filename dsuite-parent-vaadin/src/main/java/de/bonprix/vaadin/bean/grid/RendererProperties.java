package de.bonprix.vaadin.bean.grid;

import org.vaadin.grid.cellrenderers.EditableRenderer.ItemEditListener;
import org.vaadin.grid.cellrenderers.editable.common.EditableRendererEnabled;

import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class RendererProperties<PROPERTYTYPE> {

	private ItemEditListener<PROPERTYTYPE> itemEditListener;
	private RendererClickListener itemClickListener;
	private EditableRendererEnabled editableRendererEnabled = null;

	public ItemEditListener<PROPERTYTYPE> getItemEditListener() {
		return this.itemEditListener;
	}

	public void setItemEditListener(ItemEditListener<PROPERTYTYPE> itemEditListener) {
		this.itemEditListener = itemEditListener;
	}

	public RendererClickListener getItemClickListener() {
		return this.itemClickListener;
	}

	public void setItemClickListener(RendererClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public EditableRendererEnabled getEditableRendererEnabled() {
		return this.editableRendererEnabled;
	}

	public void setEditableRendererEnabled(EditableRendererEnabled editableRendererEnabled) {
		this.editableRendererEnabled = editableRendererEnabled;
	}

}
