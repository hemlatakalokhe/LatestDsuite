package de.bonprix.modules.theme.parts.combobox;

import java.util.List;

import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import de.bonprix.VaadinUI;
import de.bonprix.common.dummydata.GridDummyData;
import de.bonprix.model.Inhabitants;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = ComboBoxViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class ComboBoxViewImpl extends AbstractMvpView<ComboBoxPresenter> implements ComboBoxView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "COMBOBOX";
	public static final String TYPE_HERE = "TYPE_HERE";

	@Override
	protected void initializeUI() {
		List<Inhabitants> inhabitants = GridDummyData.genInhabitants(200);

		setCompositionRoot(FluentUI.vertical()
			.margin()
			.add(FluentUI.label()
				.value("ComboBox")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.beanItemComboBox(Inhabitants.class)
					.captionKey("NORMAL")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.nullSelectionAllowed(false)
					.get())
				.add(FluentUI.beanItemComboBox(Inhabitants.class)
					.captionKey("NORMAL_WITH_ICONS")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.itemIconGenerator(Inhabitants::getIcon)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.nullSelectionAllowed(false)
					.get())
				.add(FluentUI.beanItemComboBox(Inhabitants.class)
					.captionKey("ERROR")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.itemIconGenerator(Inhabitants::getIcon)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.nullSelectionAllowed(false)
					.componentError(new UserError("Fix it, now!"))
					.get())
				.add(FluentUI.beanItemComboBox(Inhabitants.class)
					.captionKey("DISABLED")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.nullSelectionAllowed(false)
					.enabled(false)
					.get())
				.get())
			.add(FluentUI.label()
				.value("ComboBoxMultiselect")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.beanItemComboBoxMultiselect(Inhabitants.class)
					.captionKey("NORMAL")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.get())
				.add(FluentUI.beanItemComboBoxMultiselect(Inhabitants.class)
					.captionKey("NORMAL_WITH_ICONS")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.itemIconGenerator(Inhabitants::getIcon)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.get())
				.add(FluentUI.beanItemComboBoxMultiselect(Inhabitants.class)
					.captionKey("ERROR")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.itemIconGenerator(Inhabitants::getIcon)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.componentError(new UserError("Fix it, now!"))
					.get())
				.add(FluentUI.beanItemComboBoxMultiselect(Inhabitants.class)
					.captionKey("DISABLED")
					.inputPromptKey(ComboBoxViewImpl.TYPE_HERE)
					.itemCaptionGenerator(Inhabitants::getName)
					.add(inhabitants)
					.select(inhabitants.get(0))
					.enabled(false)
					.get())
				.get())
			.get());
	}

}
