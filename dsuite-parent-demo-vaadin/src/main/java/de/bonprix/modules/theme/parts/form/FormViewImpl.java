package de.bonprix.modules.theme.parts.form;

import java.util.Arrays;

import javax.annotation.Resource;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import de.bonprix.VaadinUI;
import de.bonprix.common.dummydata.GridDummyData;
import de.bonprix.model.Inhabitants;
import de.bonprix.model.Inhabitants.Country;
import de.bonprix.model.enums.GridStatus;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.data.BeanItemFieldGroup;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = FormViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class FormViewImpl extends AbstractMvpView<FormPresenter> implements FormView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "FORM";

	@Resource
	private UiNotificationProvider notificationProvider;

	private final BeanItemFieldGroup<Inhabitants> inhabitantsFieldGroup;

	public FormViewImpl() {
		this.inhabitantsFieldGroup = new BeanItemFieldGroup<>(Inhabitants.class, new Inhabitants());
		this.inhabitantsFieldGroup.setBuffered(false);
	}

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI.vertical()
			.sizeUndefined()
			.margin()
			.add(FluentUI.label()
				.value("Form")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.form()
				.sizeUndefined()
				.style(DSuiteTheme.FORMLAYOUT_LIGHT)
				.add(FluentUI.label()
					.valueKey("PERSONAL_INFO")
					.style(DSuiteTheme.LABEL_H2, DSuiteTheme.LABEL_COLORED)
					.get())
				.add(FluentUI.textField()
					.captionKey("ID")
					.bind(this.inhabitantsFieldGroup, "id")
					.get())
				.add(FluentUI.textField()
					.captionKey("NAME")
					.bind(this.inhabitantsFieldGroup, "name")
					.get())
				.add(FluentUI.dateField()
					.captionKey("BIRTHDAY")
					.bind(this.inhabitantsFieldGroup, "birthday")
					.get())
				.add(FluentUI.checkBox()
					.captionKey("ON_FACEBOOK")
					.descriptionKey("ABC")
					.bind(this.inhabitantsFieldGroup, "onFacebook")
					.get())
				.add(FluentUI.beanItemComboBox(Country.class)
					.captionKey("COUNTRY")
					.bind(this.inhabitantsFieldGroup, "country")
					.add(GridDummyData.COUNTRIES)
					.get())
				.add(FluentUI.beanItemComboBoxMultiselect(Country.class)
					.captionKey("COUNTRIES")
					.bind(this.inhabitantsFieldGroup, "visitedCountries")
					.add(GridDummyData.COUNTRIES)
					.get())
				.add(FluentUI.beanItemComboBox(GridStatus.class)
					.captionKey("STATUS")
					.bind(this.inhabitantsFieldGroup, "status")
					.itemIconGenerator(item -> new ExternalResource(item.getIconName()))
					.add(Arrays.asList(GridStatus.values()))
					.get())
				.get())
			.add(FluentUI.button()
				.captionKey("SHOW_SAVED_INFO")
				.style(DSuiteTheme.BUTTON_PRIMARY)
				.onClick(event -> this.notificationProvider.showInfoNotification(this.inhabitantsFieldGroup.getBean()
					.toString()))
				.get())
			.get());
	}

}
