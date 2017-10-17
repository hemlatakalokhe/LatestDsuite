package de.bonprix.showcase.cases.tokenfilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.addons.tokenfilter.ExtendedOptionGroup;
import org.vaadin.addons.tokenfilter.TokenFilter;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.bonprix.model.SimpleFilterOption;
import de.bonprix.model.SimpleFilterType;
import de.bonprix.model.SimpleOptionElement;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * TokenFilter Demo.
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class TokenFilterDemo extends ShowcaseWrapper {

	private static final String FIRST = "first";
	private static final String VALUE2 = "value 2";

	public TokenFilterDemo() {
		super("INTERACTION", "TOKENFILTERDEMO");
	}

	@Override
	protected Component createLayout() {
		return FluentUI.vertical()
			.margin()
			.spacing()
			.add(FluentUI.panel()
				.captionKey("TOKENFILTER")
				.content(getTokenFilter())
				.style(DSuiteTheme.PANEL_INVERTED)
				.get())
			.add(FluentUI.panel()
				.captionKey("TOKEN_POPUP_BUTTON")
				.content(FluentUI.vertical()
					.margin()
					.spacing()
					.add(FluentUI.tokenPopupButton("TokenPopupButton", genExtendedOptionGroup())
						.get())
					.get())

				.style(DSuiteTheme.PANEL_INVERTED)
				.get())
			.add(FluentUI.panel()
				.captionKey("EXTENDED_OPTION_GROUP")
				.content(FluentUI.vertical()
					.margin()
					.spacing()
					.add(genExtendedOptionGroup())
					.get())
				.style(DSuiteTheme.PANEL_INVERTED)
				.get())
			.get();
	}

	private Component getTokenFilter() {

		final TokenFilter<SimpleFilterType> filter = FluentUI.tokenFilter(SimpleFilterType.class)
			.captionKey("TOKENFILTER")
			.add(Arrays.asList(
								new SimpleFilterType(TokenFilterDemo.FIRST,
										Arrays.asList("value 1", TokenFilterDemo.VALUE2, "value 3")),
								new SimpleFilterType("second", Arrays.asList("abcd", "efgh", "ijkl", "mnop", "qrst")),
								new SimpleFilterType("third", Arrays.asList("123", "456", "789", "012"))))
			.onValueChange(event -> {
				if (event.getProperty()
					.getValue() != null) {
					Notification.show(StringUtils.join(event.getProperty()
						.getValue(), ","), Type.TRAY_NOTIFICATION);
				}
			})
			.get();

		return FluentUI.vertical()
			.spacing()
			.margin()
			.add(filter)
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.button()
					.captionKey("SET_VALUE")
					.onClick(click -> {
						final Set<SimpleFilterType> newValue = new HashSet<>();
						final SimpleFilterType firstValue = new SimpleFilterType(TokenFilterDemo.FIRST, null);
						firstValue
							.setSelected(new HashSet<>(Arrays.asList(new SimpleFilterOption(TokenFilterDemo.VALUE2))));
						newValue.add(firstValue);
						filter.setValue(newValue);
					})
					.get())
				.add(FluentUI.button()
					.captionKey("UPDATE")
					.onClick(click -> {
						final SimpleFilterType firstValue = new SimpleFilterType(TokenFilterDemo.FIRST,
								Arrays.asList("value 1", TokenFilterDemo.VALUE2, "value 3", "value 10"));
						firstValue
							.setSelected(new HashSet<>(Arrays.asList(	new SimpleFilterOption(TokenFilterDemo.VALUE2),
																		new SimpleFilterOption("value 10"))));
						filter.updateItems(Arrays.asList(firstValue));
					})
					.get())
				.get())
			.get();
	}

	private ExtendedOptionGroup<SimpleOptionElement> genExtendedOptionGroup() {
		return FluentUI.beanItemExtendedOptionGroup(SimpleOptionElement.class)
			.captionKey("EXTENDED_OPTION_GROUP")
			.itemCaptionGenerator(SimpleOptionElement::getValue)
			.itemStylePropertyId("styleName")
			.itemCountPropertyId("documentCount")
			.maxItemsVisible(3)
			.add(new SimpleOptionElement("value red", "red", 912L))
			.add(new SimpleOptionElement("value blue", "blue", 323L))
			.add(new SimpleOptionElement("value green", "green", 654L))
			.add(new SimpleOptionElement("value yellow", "yellow", 232L))
			.add(new SimpleOptionElement("value black", "black", 349L))
			.get();
	}
}
