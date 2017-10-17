package de.bonprix.vaadin.bean.grid.contextmenu;

import java.util.List;

import com.vaadin.addon.contextmenu.MenuItem;
import com.vaadin.server.Resource;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
public class BeanItemGridContextMenuItem<BEANTYPE> {

	private String captionKey;
	private Resource icon;
	private BeanItemGridContextMenuCommand<BEANTYPE> beanCommand;
	private boolean checkable;
	private boolean checked;
	private List<BeanItemGridContextMenuItem<BEANTYPE>> children;
	private boolean seperator;

	public interface BeanItemGridContextMenuCommand<BEANTYPE> {
		void beanSelected(MenuItem selectedItem, BEANTYPE selectedBean);
	}

	public String getCaptionKey() {
		return this.captionKey;
	}

	public void setCaptionKey(String captionKey) {
		this.captionKey = captionKey;
	}

	public Resource getIcon() {
		return this.icon;
	}

	public void setIcon(Resource icon) {
		this.icon = icon;
	}

	public BeanItemGridContextMenuCommand<BEANTYPE> getBeanCommand() {
		return this.beanCommand;
	}

	public void setBeanCommand(BeanItemGridContextMenuCommand<BEANTYPE> beanCommand) {
		this.beanCommand = beanCommand;
	}

	public boolean isCheckable() {
		return this.checkable;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<BeanItemGridContextMenuItem<BEANTYPE>> getChildren() {
		return this.children;
	}

	public void setChildren(List<BeanItemGridContextMenuItem<BEANTYPE>> children) {
		this.children = children;
	}

	public boolean isSeperator() {
		return this.seperator;
	}

	public void setSeperator(boolean seperator) {
		this.seperator = seperator;
	}

}
