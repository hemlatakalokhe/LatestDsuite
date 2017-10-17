package de.bonprix.showcase.cases.statuswizard.steps;

import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import de.bonprix.model.Orders;
import de.bonprix.vaadin.ui.statuswizard.AbstractStatusWizardStep;

public class OrdersStep extends AbstractStatusWizardStep<Orders> {

	public OrdersStep(final Class<Orders> beanType, final Orders bean, final String caption, final String explanation) {
		super(beanType, bean, caption, explanation);
	}

	@Override
	public Component layout() {
		ScrollablePanel ordersPanel = new ScrollablePanel();

		getFieldGroup().setBuffered(true);
		TextField name = getFieldGroup().buildAndBind("name", "name", TextField.class);
		TextField description = getFieldGroup().buildAndBind("description", "description", TextField.class);

		description.setRequired(true);
		name.setRequired(true);

		FormLayout ordersFormLayout = new FormLayout(name, description);
		ordersPanel.setContent(ordersFormLayout);

		return ordersPanel;
	}

}
