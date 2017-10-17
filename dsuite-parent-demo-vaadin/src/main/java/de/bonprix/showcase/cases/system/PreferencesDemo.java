package de.bonprix.showcase.cases.system;

import javax.annotation.Resource;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import de.bonprix.security.service.AsynchronousPreferencesService;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SuppressWarnings("serial")
@SpringViewComponent
public class PreferencesDemo extends ShowcaseWrapper {

	private static final String DEMO_PREFERENCES_NAME = "DEMO_PREFERENCES";

	@Resource
	private AsynchronousPreferencesService asynchronousPreferencesService;

	@Resource
	private UiNotificationProvider notificationProvider;

	private TextField preferencesField;

	public PreferencesDemo() {
		super("SYSTEM", "PREFERENCESDEMO");
	}

	private void loadPreferences() {
		this.preferencesField
			.setValue(this.asynchronousPreferencesService.getStringPreference(PreferencesDemo.DEMO_PREFERENCES_NAME));

	}

	private void savePreferences(final String preferences) {
		this.asynchronousPreferencesService.setPreference(PreferencesDemo.DEMO_PREFERENCES_NAME, preferences);

	}

	@Override
	protected Component createLayout() {
		this.preferencesField = FluentUI.textField()
			.captionKey("PREFERENCES")
			.widthFull()
			.get();

		return FluentUI.vertical()
			.margin()
			.spacing()
			.sizeUndefined()
			.add(this.preferencesField)
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.button()
					.captionKey("SAVE")
					.onClick(event -> {
						savePreferences(this.preferencesField.getValue());
						this.notificationProvider.showWarningMessageBox("PREFERENCES_INFO_60_SECONDS_SAVE_SCHEDULER");
					})
					.get())
				.add(FluentUI.button()
					.captionKey("LOAD")
					.onClick(event -> loadPreferences())
					.get())
				.get())
			.get();

	}
}