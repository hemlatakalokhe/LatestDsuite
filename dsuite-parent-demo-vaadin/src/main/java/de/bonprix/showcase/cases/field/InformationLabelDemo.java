package de.bonprix.showcase.cases.field;

import com.vaadin.ui.Component;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.information.InformationLabel.Type;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringViewComponent
public class InformationLabelDemo extends ShowcaseWrapper {

	public InformationLabelDemo() {
		super("STRUCTURE_HIERARCHY", "INFORMATIONLABELDEMO");
	}

	@Override
	protected Component createLayout() {
		final String loremText = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt "
				+ "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi "
				+ "ut aliquip ex ea commodo consequat.";

		return FluentUI	.vertical()
						.spacing()
						.margin()
						.width(40, Unit.PERCENTAGE)
						.add(FluentUI	.vertical()
										.add(FluentUI	.label()
														.valueKey("InformationLabel of Type.INFO")
														.style(DSuiteTheme.LABEL_H1)
														.get())
										.add(FluentUI	.informationLabel()
														.messageKey(loremText)
														.get())
										.get())
						.add(FluentUI	.vertical()
										.add(FluentUI	.label()
														.valueKey("InformationLabel of Type.WARNING")
														.style(DSuiteTheme.LABEL_H1)
														.get())
										.add(FluentUI	.informationLabel()
														.messageKey(loremText)
														.type(Type.WARNING)
														.get())
										.get())
						.add(FluentUI	.vertical()
										.add(FluentUI	.label()
														.valueKey("InformationLabel of Type.ERROR")
														.style(DSuiteTheme.LABEL_H1)
														.get())
										.add(FluentUI	.informationLabel()
														.messageKey(loremText)
														.type(Type.ERROR)
														.get())
										.get())

						.get();
	}
}
