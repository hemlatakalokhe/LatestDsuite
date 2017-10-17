package de.bonprix.showcase.cases.statuswizard.steps;

import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import de.bonprix.model.Item;
import de.bonprix.vaadin.ui.statuswizard.AbstractStatusWizardStep;

public class ItemStep extends AbstractStatusWizardStep<Item> {

	public ItemStep(final Class<Item> beanType, final Item item, final String caption, final String explanation) {
		super(beanType, item, caption, explanation);
	}

	@Override
	public Component layout() {
		ScrollablePanel itemPanel = new ScrollablePanel();

		getFieldGroup().setBuffered(true);
		TextField itemNo = getFieldGroup().buildAndBind("itemNo", "itemNo", TextField.class);
		TextField assetUrl = getFieldGroup().buildAndBind("assetUrl", "assetUrl", TextField.class);

		FormLayout itemFormLayout = new FormLayout(itemNo, assetUrl);
		itemPanel.setContent(itemFormLayout);

		return itemPanel;
	}

}