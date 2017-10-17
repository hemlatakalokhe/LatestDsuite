package de.bonprix.showcase.cases.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import de.bonprix.showcase.ShowCaseView;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.data.BeanItemFieldGroup;
import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfiguration;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringViewComponent
public class DialogDemo extends ShowcaseWrapper {

	@Autowired
	private ShowCaseView.Presenter presenter;

	public DialogDemo() {
		super("STRUCTURE_HIERARCHY", "DIALOGDEMO");
	}

	@Override
	protected Component createLayout() {
		BeanItemFieldGroup<DialogConfiguration> fieldGroup = new BeanItemFieldGroup<>(DialogConfiguration.class,
				new DialogConfigurationBuilder().withHeadline("Dialog headline")
					.withSubline("Dialog subline")
					.withButton(DialogButton.OK)
					.build());
		fieldGroup.setBuffered(false);

		return FluentUI.vertical()
			.margin()
			.spacing()
			.add(FluentUI.panel()
				.style(DSuiteTheme.PANEL_INVERTED)
				.content(FluentUI.vertical()
					.margin()
					.spacing()
					.add(FluentUI.horizontal()
						.margin()
						.spacing()
						.add(FluentUI.checkBox()
							.caption("create a Modal Dialog")
							.bind(fieldGroup, "modal")
							.get())
						.add(FluentUI.checkBox()
							.caption("create a dragable Dialog")
							.bind(fieldGroup, "dragable")
							.get())
						.add(FluentUI.checkBox()
							.caption("create a closable Dialog")
							.bind(fieldGroup, "closable")
							.get())
						.add(FluentUI.checkBox()
							.caption("create a resizable Dialog")
							.bind(fieldGroup, "resizable")
							.get())
						.get())
					.add(FluentUI.horizontal()
						.margin()
						.spacing()
						.add(FluentUI.textField()
							.caption("Width")
							.bind(fieldGroup, "width")
							.get())
						.add(FluentUI.textField()
							.captionKey("Height")
							.bind(fieldGroup, "height")
							.get())
						.add(FluentUI.informationLabel()
							.messageKey("If width and height are undefined the dialog will have the minimal size.")
							.get())
						.get())
					.add(FluentUI.horizontal()
						.margin()
						.spacing()
						.add(FluentUI.textField()
							.caption("Headline")
							.bind(fieldGroup, "headline")
							.get())
						.add(FluentUI.textField()
							.caption("Subline")
							.bind(fieldGroup, "subline")
							.get())
						.get())
					.add(FluentUI.vertical()
						.margin()
						.add(FluentUI.button()
							.style(DSuiteTheme.BUTTON_PRIMARY)
							.captionKey("NEW_DIALOG")
							.onClick(event -> getUI().addWindow(new SampleDialog(fieldGroup.getBean())))
							.get())
						.get())
					.get())
				.get())
			.add(FluentUI.vertical()
				.margin()
				.add(FluentUI.button()
					.captionKey("OPEN_MVP_DIALOG")
					.onClick(event -> this.presenter.openMvpDialog())
					.get())
				.get())
			.get();
	}

	/**
	 * A simple demo dialog with some form fields and two buttons.
	 *
	 * @author cthiel
	 *
	 */
	private static class SampleDialog extends AbstractBaseDialog {

		public SampleDialog(DialogConfiguration dialogConfiguration) {
			super(dialogConfiguration);

			// add a click listener to the OK button
			addButtonListener(DialogButton.OK, event -> {
				Notification.show("Button clicked");
				close();
			});
		}

		@Override
		protected Component layout() {
			return FluentUI.horizontal()
				.margin(false, true, true, true)
				.add(FluentUI.form()
					.add(FluentUI.textField()
						.captionKey("NAME")
						.get())
					.add(FluentUI.textField()
						.captionKey("STREET")
						.get())
					.add(FluentUI.textField()
						.captionKey("CITY")
						.get())
					.add(FluentUI.checkBox()
						.captionKey("SOME_CHECKBOX")
						.get())
					.add(FluentUI.vertical()
						.captionKey("SOME_CHECKBOX")
						.add(FluentUI.checkBox()
							.get())
						.get())
					.get())
				.get();
		}
	}
}
