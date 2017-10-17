package de.bonprix.statuswizard;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import de.bonprix.vaadin.ui.statuswizard.AbstractStatusWizardStep;

public class TestCountryStep extends AbstractStatusWizardStep<TestCountry> {

	public TestCountryStep(final Class<TestCountry> beanType, final TestCountry country, final String caption,
			final String explanation) {
		super(beanType, country, caption, explanation);
	}

	@Override
	public Component layout() {

		getFieldGroup().setBuffered(true);
		TextField name = getFieldGroup().buildAndBind("name", "name", TextField.class);
		name.setRequired(true);
		TextField isoCode = getFieldGroup().buildAndBind("isoCode", "isoCode", TextField.class);

		FormLayout countryFormLayout = new FormLayout(name, isoCode);

		return countryFormLayout;
	}
}
