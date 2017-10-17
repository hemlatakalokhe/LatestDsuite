package de.bonprix.vaadin.ui.statuswizard;

import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * @author dmut
 */

public class CurrentPanel extends VerticalLayout {

	public CurrentPanel(final StatusWizardStep step) {
		layout(step);
	}

	public void updatePanel(final StatusWizardStep step) {
		layout(step);
	}

	private void layout(final StatusWizardStep step) {
		removeAllComponents();
		setSpacing(true);
		VerticalLayout header = FluentUI.vertical()
										.add(FluentUI	.label()
														.value(step.getCaption())
														.style((DSuiteTheme.STATUS_WIZARD_LABEL_HEADER))
														.get())
										.add(FluentUI	.label()
														.value(step.getExplanation())
														.get())
										.sizeFull()
										.get();

		addComponent(header);
		setExpandRatio(header, step.getExpandRatioHeader());

		addComponent(step.getLayout());
		setExpandRatio(step.getLayout(), step.getExpandRatioLayout());
		setHeight(100, Unit.PERCENTAGE);
	}

}
