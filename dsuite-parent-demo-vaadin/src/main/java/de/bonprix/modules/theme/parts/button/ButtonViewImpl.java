package de.bonprix.modules.theme.parts.button;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import de.bonprix.VaadinUI;
import de.bonprix.common.dummydata.GridDummyData;
import de.bonprix.model.Inhabitants.Country;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.fluentui.Links.Target;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarComponent;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarItem;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarPlacement;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = ButtonViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class ButtonViewImpl extends AbstractMvpView<ButtonPresenter> implements ButtonView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "BUTTON";
	public static final String ACTION = "action";
	public static final String CAPTION = "CAPTION";
	public static final String VALUE_CHANGED = "Combobox value changed: ";

	@Override
	protected void initializeUI() {
		// Menu
		addMenuElement(new ComponentBarItem("action", "ACTION"));
		addMenuElement(new ComponentBarItem("do", "DO").withParentId("action")
			.withHighlightedMenu());
		addMenuElement(new ComponentBarItem("some", "SOME").withParentId("do")
			.withHighlightedMenu());
		addMenuElement(new ComponentBarItem("more", "MORE").withParentId("do"));
		addMenuElement(new ComponentBarItem("different", "DIFFERENT").withParentId("action")
			.withHighlightedMenu());
		addMenuElement(new ComponentBarItem("things", "THINGS").withParentId("action"));
		addMenuElement(new ComponentBarItem("bar", "BAR"));
		addMenuElement(new ComponentBarItem("disabled", "DISABLED").withEnabled(false));

		addMenuElement(new ComponentBarComponent(FluentUI.beanItemComboBoxMultiselect(Country.class)
			.captionKey(ButtonViewImpl.CAPTION)
			.add(GridDummyData.COUNTRIES)
			.onValueChange(event -> Notification.show(ButtonViewImpl.VALUE_CHANGED + event.getProperty()
				.getValue()))
			.get()));

		addMenuElement(new ComponentBarComponent(FluentUI.checkBox()
			.captionKey(ButtonViewImpl.CAPTION)
			.onValueChange(event -> Notification.show("CheckBox toggled: " + event.getProperty()
				.getValue()))
			.get()).withPlacement(ComponentBarPlacement.CENTER));

		addMenuElement(new ComponentBarComponent(FluentUI.beanItemComboBox(Country.class)
			.captionKey(ButtonViewImpl.CAPTION)
			.add(GridDummyData.COUNTRIES)
			.onValueChange(event -> Notification.show(ButtonViewImpl.VALUE_CHANGED + event.getProperty()
				.getValue()))
			.get()).withPlacement(ComponentBarPlacement.RIGHT));

		addMenuElement(new ComponentBarItem("between", "ANOTHER_ITEM"));

		addMenuElement(new ComponentBarComponent(FluentUI.beanItemComboBoxMultiselect(Country.class)
			.captionKey("DESCRIPTION")
			.add(GridDummyData.COUNTRIES)
			.onValueChange(event -> Notification.show(ButtonViewImpl.VALUE_CHANGED + event.getProperty()
				.getValue()))
			.get()));

		setCompositionRoot(FluentUI.vertical()
			.margin()
			.spacing()
			.add(FluentUI.informationLabel()
				.messageKey("THEME_MENU_DESCRIPTION")
				.contentMode(ContentMode.HTML)
				.get())
			// Buttons
			.add(FluentUI.label()
				.style(DSuiteTheme.LABEL_H1)
				.valueKey("BUTTONS")
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.button()
					.captionKey(ButtonViewImpl.VIEW_NAME)
					.descriptionKey(ButtonViewImpl.VIEW_NAME)
					.get())
				.add(FluentUI.button()
					.captionKey("DISABLED_BUTTON")
					.descriptionKey("DISABLED_BUTTON")
					.disabled()
					.get())
				.add(FluentUI.button()
					.style(DSuiteTheme.BUTTON_PRIMARY)
					.captionKey("PRIMARY_BUTTON")
					.descriptionKey("PRIMARY_BUTTON")
					.get())
				.add(FluentUI.button()
					.style(DSuiteTheme.BUTTON_ICON_ONLY)
					.icon(FontAwesome.ADJUST)
					.descriptionKey("ICON_BUTTON")
					.get())
				.get())
			// ComponentBar
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.label()
					.style(DSuiteTheme.LABEL_H1)
					.valueKey("COMPONENT_BAR")
					.get())
				.add(FluentUI.link()
					.captionKey("CONFLUENCE")
					.target(Target.NEW_TAB)
					.url("https://extranet.bonprix.net/confluence/display/dsp/ComponentBar")
					.get(), Alignment.MIDDLE_CENTER)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.widthFull()
				.add(FluentUI.componentBar()
					.add(new ComponentBarItem(ButtonViewImpl.ACTION, "ACTION"))
					.add(new ComponentBarItem("do", "DO").withParentId(ButtonViewImpl.ACTION))
					.add(new ComponentBarItem("some", "SOME").withParentId("do"))
					.add(new ComponentBarItem("more", "MORE").withParentId("do"))
					.add(new ComponentBarItem("different", "DIFFERENT").withParentId(ButtonViewImpl.ACTION))
					.add(new ComponentBarItem("things", "THINGS").withParentId(ButtonViewImpl.ACTION))
					.add(new ComponentBarItem("bar", "BAR"))
					.add(new ComponentBarItem("disabled", "DISABLED").withEnabled(false))
					.add(new ComponentBarComponent(FluentUI.checkBox()
						.captionKey(ButtonViewImpl.CAPTION)
						.onValueChange(event -> Notification.show("CheckBox toggled: " + event.getProperty()
							.getValue()))
						.get()))
					.add(new ComponentBarComponent(FluentUI.beanItemComboBox(Country.class)
						.captionKey(ButtonViewImpl.CAPTION)
						.add(GridDummyData.COUNTRIES)
						.onValueChange(event -> Notification.show(ButtonViewImpl.VALUE_CHANGED + event.getProperty()
							.getValue()))
						.get()))
					.add(new ComponentBarItem("between", "ANOTHER_ITEM"))
					.add(new ComponentBarComponent(FluentUI.beanItemComboBoxMultiselect(Country.class)
						.add(GridDummyData.COUNTRIES)
						.captionKey(ButtonViewImpl.CAPTION)
						.onValueChange(event -> Notification.show(ButtonViewImpl.VALUE_CHANGED + event.getProperty()
							.getValue()))
						.get()).withPlacement(ComponentBarPlacement.RIGHT))
					.get())
				.get())
			// Links
			.add(FluentUI.label()
				.style(DSuiteTheme.LABEL_H1)
				.valueKey("LINKS")
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.widthFull()
				.add(FluentUI.link()
					.captionKey("LINK")
					.target(Target.NEW_TAB)
					.url("https://vaadin.com")
					.get())
				.get())
			.get());
	}

}
