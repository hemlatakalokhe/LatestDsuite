package de.bonprix.modules.theme.parts.checkbox;

import com.vaadin.spring.annotation.SpringView;
import de.bonprix.VaadinUI;
import de.bonprix.model.SimpleOptionElement;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = CheckBoxViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class CheckBoxViewImpl extends AbstractMvpView<CheckBoxPresenter> implements CheckBoxView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "CHECKBOX";

	private static final String OPTION_ONE = "Option One";
	private static final String OPTION_THREE = "Option Three";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI.vertical()
			.margin()
			.add(FluentUI.label()
				.value("CheckBox")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.checkBox()
					.captionKey("CHECKED")
					.value(true)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("CHECKED_EXPLICIT_WIDTH_SO_THAT_THE_CAPTION_SHOULD_WRAP")
					.width(150, Unit.PIXELS)
					.value(true)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("NOT_CHECKED")
					.value(true)
					.get())
				.add(FluentUI.checkBox()
					.descriptionKey("NO_CAPTION")
					.value(true)
					.get())
				.get())
			.add(FluentUI.label()
				.value("OptionGroup")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.optionGroup()
					.captionKey("Choose one, explicit width")
					.width(200, Unit.PIXELS)
					.add(CheckBoxViewImpl.OPTION_ONE)
					.add("Option Two, with a longer caption that should wrap when the components width is explicitly set.")
					.add(CheckBoxViewImpl.OPTION_THREE)
					.select(CheckBoxViewImpl.OPTION_ONE)
					.get())
				.add(FluentUI.optionGroup()
					.captionKey("Choose many, explicit width")
					.multiselect()
					.width(200, Unit.PIXELS)
					.add(CheckBoxViewImpl.OPTION_ONE)
					.add("Option Two, with a longer caption that should wrap when the components width is explicitly set.")
					.add(CheckBoxViewImpl.OPTION_THREE)
					.select(CheckBoxViewImpl.OPTION_ONE)
					.get())
				.add(FluentUI.optionGroup()
					.captionKey("Horizontal items")
					.style(DSuiteTheme.OPTIONGROUP_HORIZONTAL)
					.add(CheckBoxViewImpl.OPTION_ONE)
					.add("Option Two, with a longer caption")
					.add(CheckBoxViewImpl.OPTION_THREE)
					.add(CheckBoxViewImpl.OPTION_ONE)
					.get())
				.add(FluentUI.optionGroup()
					.captionKey("Horizontal items, explicit width")
					.width(500, Unit.PIXELS)
					.style(DSuiteTheme.OPTIONGROUP_HORIZONTAL)
					.add(CheckBoxViewImpl.OPTION_ONE)
					.add("Option Two, with a longer caption")
					.add(CheckBoxViewImpl.OPTION_THREE)
					.add(CheckBoxViewImpl.OPTION_ONE)
					.get())
				.get())
			.add(FluentUI.label()
				.value("ExtendedOptionGroup")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.beanItemExtendedOptionGroup(SimpleOptionElement.class)
				.captionKey("EXTENDED_OPTION_GROUP")
				.itemCaptionGenerator(new ItemCaptionGenerator<SimpleOptionElement>() {

					@Override
					public String getCaption(SimpleOptionElement item) {
						return item.getValue();
					}
				})
				.itemStylePropertyId("styleName")
				.itemCountPropertyId("documentCount")
				.maxItemsVisible(3)
				.add(new SimpleOptionElement("value red", "red", 912L))
				.add(new SimpleOptionElement("value blue", "blue", 323L))
				.add(new SimpleOptionElement("value green", "green", 654L))
				.add(new SimpleOptionElement("value yellow", "yellow", 232L))
				.add(new SimpleOptionElement("value black", "black", 349L))
				.get())
			.get());
	}

}
