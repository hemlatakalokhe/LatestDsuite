package de.bonprix.showcase.cases.field;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import de.bonprix.I18N;
import de.bonprix.model.AutocompleteNames;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.provider.CollectionSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.provider.MatchMode;
import eu.maxschuster.vaadin.autocompletetextfield.shared.ScrollBehavior;

/**
 * 
 * @author Torben Meißner
 *
 */

@SuppressWarnings("serial")
@SpringViewComponent
public class AutocompleteTextFieldDemo extends ShowcaseWrapper {

	public AutocompleteTextFieldDemo() {
		super("STRUCTURE_HIERARCHY", "AUTOCOMPLETETEXTFIELDDEMO");
	}

	@Override
	protected Component createLayout() {

		AutocompleteSuggestionProvider suggestionProvider = new CollectionSuggestionProvider(
				Arrays.asList(AutocompleteNames.getNames()), MatchMode.CONTAINS, true);

		AutocompleteTextField autocompleteTextField = new AutocompleteTextField();
		autocompleteTextField.setWidth(100, Unit.PERCENTAGE);

		// ===============================
		// Available configuration options
		// ===============================

		autocompleteTextField.withSuggestionProvider(suggestionProvider)
			// The required value length to trigger a query
			.withMinChars(2)
			// The max amount of suggestions send to the client. If the limit is
			// >=
			// 0 no limit is applied
			.withSuggestionLimit(20)
			// Client side should cache suggestions
			.withCache(true)
			// Delay before sending a query to the server
			.withDelay(150)
			// Suggestions contain html formating. If true, make sure that the
			// html
			// is save to use!
			.withItemAsHtml(false)
			// The method that should be used to compensate scrolling of the
			// page
			.withScrollBehavior(ScrollBehavior.NONE)
			.withInputPrompt("Enter ...")
			.withTextChangeListener(this::onAutocompleteTextChange)
			.withValueChangeListener(this::onAutocompleteValueChange);

		FormLayout optionLayout = new FormLayout();
		optionLayout.setCaption(I18N.get("AUTOCOMPLETE_OPTION_PANEL_DESCRIPTION"));
		optionLayout.setMargin(true);
		optionLayout.addStyleName("outlined");
		optionLayout.setSizeFull();

		final TextField delayField = new TextField(I18N.get("DELAY"), "150");
		delayField.setWidth(100.0f, Unit.PERCENTAGE);
		optionLayout.addComponent(delayField);

		final TextField minCharsField = new TextField(I18N.get("AUTOCOMPLETE_MIN_CHARS"), "1");
		minCharsField.setWidth(100.0f, Unit.PERCENTAGE);
		optionLayout.addComponent(minCharsField);

		final TextField maxSuggestionField = new TextField(I18N.get("AUTOCOMPLETE_SUGGESTION_LIMIT"), "20");
		delayField.setWidth(100.0f, Unit.PERCENTAGE);
		optionLayout.addComponent(maxSuggestionField);

		List<ScrollBehavior> scrollBehaviorList = Arrays.asList(ScrollBehavior.values());
		ComboBox scrollBehaviorComboBox = new ComboBox(I18N.get("AUTOCOMPLETE_SCROLL_BEHAVIOR"), scrollBehaviorList);
		scrollBehaviorComboBox.setNullSelectionAllowed(false);
		scrollBehaviorComboBox.setImmediate(true);
		scrollBehaviorComboBox.setValue(scrollBehaviorComboBox.getItemIds()
			.iterator()
			.next());
		scrollBehaviorComboBox.setWidth(100.0f, Unit.PERCENTAGE);
		optionLayout.addComponent(scrollBehaviorComboBox);

		CheckBox cacheCheckBox = new CheckBox(I18N.get("AUTOCOMPLETE_CACHE_ENABLED"));
		cacheCheckBox.setImmediate(true);
		cacheCheckBox.setWidth(100.0f, Unit.PERCENTAGE);
		cacheCheckBox.setValue(true);
		optionLayout.addComponent(cacheCheckBox);

		Button applyButton = new Button(I18N.get("APPLY"));
		applyButton.addClickListener(e -> {
			autocompleteTextField.setDelay(Integer.parseInt(delayField.getValue()));
			autocompleteTextField.setMinChars(Integer.parseInt(minCharsField.getValue()));
			autocompleteTextField.setSuggestionLimit(Integer.parseInt(maxSuggestionField.getValue()));
			autocompleteTextField.setScrollBehavior((ScrollBehavior) scrollBehaviorComboBox.getValue());
			autocompleteTextField.setCache(cacheCheckBox.getValue());
		});
		optionLayout.addComponent(applyButton);

		return FluentUI.vertical()
			.margin()
			.spacing()
			.width(40, Unit.PERCENTAGE)
			.add(FluentUI.panel()
				.captionKey("AUTOCOMPLETETEXTFIELDDEMO")
				.content(autocompleteTextField)
				.style(DSuiteTheme.PANEL_INVERTED)
				.get())
			.add(FluentUI.panel()
				.captionKey("AUTOCOMPLETE_OPTIONS_CAPTION")
				.content(FluentUI.vertical()
					.margin()
					.spacing()
					.add(optionLayout)
					.get())
				.style(DSuiteTheme.PANEL_INVERTED)
				.get())
			.get();

	}

	private void onAutocompleteTextChange(FieldEvents.TextChangeEvent event) {
		String text = "Text changed to: " + event.getText();
		Notification.show(text, Notification.Type.TRAY_NOTIFICATION);
	}

	private void onAutocompleteValueChange(Property.ValueChangeEvent event) {
		String text = "Value changed to: " + event.getProperty()
			.getValue();
		Notification notification = new Notification(text, Notification.Type.TRAY_NOTIFICATION);
		notification.setPosition(Position.BOTTOM_LEFT);
		notification.show(Page.getCurrent());
	}
}
