package de.bonprix.base.demo.dto;

import java.util.Set;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage= "*.builder")
public class Item extends Entity{

	private static final long serialVersionUID = 1L;

	private String color;

	private String itemNo;

	private Style style;

	private Set<ItemSize> itemSizes;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Set<ItemSize> getItemSizes() {
		return itemSizes;
	}

	public void setItemSizes(Set<ItemSize> itemSizes) {
		this.itemSizes = itemSizes;
	}

	@Override
	public String toString() {
		return  color;
	}

}
