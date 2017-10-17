package de.bonprix.model;

import de.bonprix.model.enums.OrderStatus;

public class Orders {
	private String name;
	private String description;
	private OrderStatus status;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public OrderStatus getStatus() {
		return this.status;
	}

	public void setStatus(final OrderStatus status) {
		this.status = status;
	}
}
