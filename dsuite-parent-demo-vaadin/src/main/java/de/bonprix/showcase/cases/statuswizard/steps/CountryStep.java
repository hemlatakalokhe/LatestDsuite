package de.bonprix.showcase.cases.statuswizard.steps;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import de.bonprix.model.Country;
import de.bonprix.vaadin.ui.statuswizard.AbstractStatusWizardStep;

public class CountryStep extends AbstractStatusWizardStep<Country> {

	public CountryStep(final Class<Country> beanType, final Country country, final String caption,
			final String explanation) {
		super(beanType, country, caption, explanation);
	}

	@Override
	public Component layout() {

		getFieldGroup().setBuffered(true);
		TextField name = getFieldGroup().buildAndBind("name", "name", TextField.class);
		name.setRequired(true);
		TextField isoCode = getFieldGroup().buildAndBind("isoCode", "isoCode", TextField.class);

		return new FormLayout(name, isoCode);
	}
}
