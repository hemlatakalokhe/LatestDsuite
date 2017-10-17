package de.bonprix.vaadin.fluentui;

import java.util.Date;

import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

/**
 * Provides a fluent API to configure a Vaadin {@link TextField} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class DateFields<CONFIG extends DateFields<CONFIG>> extends AbstractFields<DateField, Date, CONFIG> {

	protected DateFields(final DateField component) {
		super(component);
	}

}
