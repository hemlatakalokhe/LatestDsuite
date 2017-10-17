package de.bonprix.modules.theme.parts.tabsheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.modules.theme.util.StringGenerator;
import de.bonprix.vaadin.data.BeanItemFieldGroup;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = TabSheetViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class TabSheetViewImpl extends AbstractMvpView<TabSheetPresenter> implements TabSheetView {

	private static final Logger LOGGER = LoggerFactory.getLogger(TabSheetViewImpl.class);
	public static final String VIEW_NAME = "TABS";

	private final BeanItemFieldGroup<SliderPreferences> preferencesFieldGroup;
	private final ValueChangeListener updatePreferencesValueChangeListener;

	private TabSheet tabs;

	public TabSheetViewImpl() {
		this.preferencesFieldGroup = new BeanItemFieldGroup<>(SliderPreferences.class, new SliderPreferences());
		this.preferencesFieldGroup.setBuffered(false);

		this.updatePreferencesValueChangeListener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				VerticalLayout parent = (VerticalLayout) TabSheetViewImpl.this.tabs.getParent();
				parent.removeComponent(TabSheetViewImpl.this.tabs);
				TabSheetViewImpl.this.tabs = getTabSheet();
				parent.addComponent(TabSheetViewImpl.this.tabs);
				parent.setExpandRatio(TabSheetViewImpl.this.tabs, 1f);
			}
		};
	}

	@Override
	protected void initializeUI() {
		this.tabs = getTabSheet();

		setCompositionRoot(FluentUI.vertical()
			.margin()
			.spacing()
			.add(FluentUI.label()
				.value("TabSheet")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.label()
				.valueKey("OPTIONS")
				.style(DSuiteTheme.LABEL_H2)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.checkBox()
					.captionKey("CLOSABLE")
					.bind(this.preferencesFieldGroup, "closable")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("OVERFLOW")
					.bind(this.preferencesFieldGroup, "overflowed")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("DISABLE_TABS")
					.bind(this.preferencesFieldGroup, "disabled")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.get())
			.add(FluentUI.label()
				.valueKey("ADDITIONAL_OPTIONS")
				.style(DSuiteTheme.LABEL_H2)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.checkBox()
					.captionKey("INVERTED_TAB_CONTENT_COLOR")
					.bind(this.preferencesFieldGroup, "invertedColor")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("CENTERED_TABS")
					.bind(this.preferencesFieldGroup, "centered")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("EQUAL_WIDTH_TABS")
					.bind(this.preferencesFieldGroup, "equal")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("COMPACT")
					.bind(this.preferencesFieldGroup, "compact")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.add(FluentUI.checkBox()
					.captionKey("SELECTED_TAB_CLOSABLE")
					.bind(this.preferencesFieldGroup, "onlySelectedClosable")
					.onValueChange(this.updatePreferencesValueChangeListener)
					.get())
				.get())
			.add(this.tabs, 1f)
			.get());
	}

	private TabSheet getTabSheet() {
		TabSheet tabSheet = FluentUI.tabSheet()
			.sizeFull()
			.style(getPreferences().isInvertedColor(), DSuiteTheme.TABSHEET_INVERTED_TABCONTENT)
			.style(getPreferences().isCentered(), DSuiteTheme.TABSHEET_CENTERED_TABS)
			.style(getPreferences().isEqual(), DSuiteTheme.TABSHEET_EQUAL_WIDTH_TABS)
			.style(getPreferences().isCompact(), DSuiteTheme.TABSHEET_COMPACT_TABBAR)
			.style(getPreferences().isOnlySelectedClosable(), DSuiteTheme.TABSHEET_ONLY_SELECTED_TAB_IS_CLOSABLE)
			.onSelectedTab(event -> {
				try {
					// trick to simulate loading data
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					TabSheetViewImpl.LOGGER.error(e.getLocalizedMessage());
					Thread.currentThread()
						.interrupt();
				}
			})
			.get();

		StringGenerator sg = new StringGenerator();

		for (int i = 1; i <= (getPreferences().isOverflowed() ? 20 : 3); i++) {
			tabSheet = FluentUI.tabsheetOf(tabSheet)
				.add(FluentUI.tab(FluentUI.vertical()
					.margin()
					.spacing()
					.add(FluentUI.label()
						.valueKey("CONTENT_FOR_TAB", i)
						.get())
					.add(i == 2, FluentUI.label()
						.value("Excepteur sint obcaecat cupiditat non proident culpa. Magna pars studiorum, prodita quaerimus.")
						.get())
					.get())
					.captionKey(sg.nextString(true) + " " + sg.nextString(false))
					.closable(getPreferences().getClosable())
					.enabled(i == 1 || !getPreferences().isDisabled())
					.get())
				.get();
		}

		return tabSheet;
	}

	private SliderPreferences getPreferences() {
		return this.preferencesFieldGroup.getBean();
	}

	public class SliderPreferences {
		private boolean closable = false;
		private boolean overflowed = false;
		private boolean disabled = false;
		private boolean invertedColor = false;
		private boolean centered = false;
		private boolean equal = false;
		private boolean compact = false;
		private boolean onlySelectedClosable = false;

		public boolean getClosable() {
			return this.closable;
		}

		public void setClosable(boolean closable) {
			this.closable = closable;
		}

		public boolean isOverflowed() {
			return this.overflowed;
		}

		public void setOverflowed(boolean overflowed) {
			this.overflowed = overflowed;
		}

		public boolean isDisabled() {
			return this.disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public boolean isInvertedColor() {
			return this.invertedColor;
		}

		public void setInvertedColor(boolean invertedColor) {
			this.invertedColor = invertedColor;
		}

		public boolean isCentered() {
			return this.centered;
		}

		public void setCentered(boolean centered) {
			this.centered = centered;
		}

		public boolean isEqual() {
			return this.equal;
		}

		public void setEqual(boolean equal) {
			this.equal = equal;
		}

		public boolean isCompact() {
			return this.compact;
		}

		public void setCompact(boolean compact) {
			this.compact = compact;
		}

		public boolean isOnlySelectedClosable() {
			return this.onlySelectedClosable;
		}

		public void setOnlySelectedClosable(boolean onlySelectedClosable) {
			this.onlySelectedClosable = onlySelectedClosable;
		}

	}

}
