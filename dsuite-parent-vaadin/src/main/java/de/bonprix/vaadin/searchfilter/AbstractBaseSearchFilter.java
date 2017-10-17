package de.bonprix.vaadin.searchfilter;

import java.util.HashSet;
import java.util.Set;

import org.vaadin.addons.resetbuttonforlistselect.ResetButtonForListSelect;
import org.vaadin.addons.textfieldmultiline.TextFieldMultiline;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.bonprix.I18N;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.bean.field.BeanItemListSelect;
import de.bonprix.vaadin.bean.field.util.OnEnterKeyListener;
import de.bonprix.vaadin.data.form.EnhancedFieldGroupFieldFactory;

public abstract class AbstractBaseSearchFilter<BEANTYPE> implements SearchViewFilter {

	private final BeanFieldGroup<BEANTYPE> fieldGroup;

	private Component primaryLayout = null;
	private Component secondaryLayout = null;
	private final Button searchBtn = new Button(FontBonprix.REFRESH);
	private final Button resetBtn = new Button(FontBonprix.DELETE);

	private final Set<SubmitListener<BEANTYPE>> submitListeners = new HashSet<SubmitListener<BEANTYPE>>();

	private final OnEnterKeyListener onEnterKeyListener = new OnEnterKeyListener() {

		@Override
		public void onEnterKeyPressed() {
			doSubmit();
		}
	};

	private boolean initialized = false;

	private boolean collapseOnSubmit = true;
	private boolean expandOnSubmit = false;

	@SuppressWarnings("unchecked")
	public AbstractBaseSearchFilter(final BEANTYPE filterBean) {
		this.fieldGroup = new BeanFieldGroup<BEANTYPE>((Class<BEANTYPE>) filterBean.getClass());
		this.fieldGroup.setFieldFactory(new EnhancedFieldGroupFieldFactory());
		this.fieldGroup.setBuffered(false);
		this.fieldGroup.setItemDataSource(filterBean);
	}

	/**
	 * Sets the item data source of this filter.
	 *
	 * @param filter
	 */
	public void setFilter(final BEANTYPE filter) {
		this.fieldGroup.setItemDataSource(filter);
	}

	/**
	 * Creates a main layout consisting of two buttons "send" and "reset" and
	 * all other components aligned in a verticallayout below.
	 *
	 * @param components
	 *            the components
	 * @return the created layout component
	 */
	protected Component createDefaultLayout(final Component... components) {
		final HorizontalLayout formLayout = new HorizontalLayout(components);
		formLayout.setSpacing(true);

		return formLayout;
	}

	/**
	 * Creates a horizontal Layout with the given child components as content,
	 * makes all components 100% width.
	 *
	 * @param components
	 *            the components to add
	 * @return
	 */
	protected HorizontalLayout createChildFormLayout(final Component... components) {
		final HorizontalLayout formLayout = new HorizontalLayout(components);
		formLayout.setSpacing(true);

		for (final Component c : components) {
			c.setWidth(100, Unit.PERCENTAGE);
			formLayout.setExpandRatio(c, 1);
		}

		return formLayout;
	}

	/**
	 * Creates a textfield with the given caption and binds it to the given
	 * property of the bean.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the textfield to
	 * @return the textfield
	 */
	protected TextField buildAndBindTextField(final String captionKey, final String property) {
		ensureNestedPropertyAdded(property);

		return this.onEnterKeyListener.installOn((TextField) this.fieldGroup.buildAndBind(	I18N.get(captionKey),
																							property));
	}

	/**
	 * Creates a textfield with the given caption and binds it to the given
	 * property of the bean.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the textfield to
	 * @return the textfield
	 */
	protected TextFieldMultiline buildAndBindTextFieldMultiline(final String captionKey, final String property) {
		ensureNestedPropertyAdded(property);
		return this.fieldGroup.buildAndBind(I18N.get(captionKey), property, TextFieldMultiline.class);
	}

	/**
	 * Creates a dateField with the given caption and binds it to the given
	 * property of the bean.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the dateField to
	 * @return the dateField
	 */
	protected DateField buildAndBindDateField(final String captionKey, final String property) {
		ensureNestedPropertyAdded(property);

		return this.fieldGroup.buildAndBind(captionKey != null ? I18N.get(captionKey) : null, property,
											DateField.class);
	}

	/**
	 * Creates a checkbox with the given caption and default value and binds it
	 * to the given property of the bean.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the checkbox to
	 * @param defaultValue
	 *            the default value
	 * @return the checkbox
	 */
	protected CheckBox buildAndBindCheckBox(final String captionKey, final String property,
			final boolean defaultValue) {
		ensureNestedPropertyAdded(property);
		final CheckBox box = new CheckBox(captionKey != null ? I18N.get(captionKey) : null, defaultValue);
		this.fieldGroup.bind(box, property);

		return box;
	}

	/**
	 * Creates a combobox with the given caption and binds it to the given
	 * property of the bean. The combobox is backed ba a bean item container.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the combobox to
	 * @return the combobox
	 */
	protected <BEANTYPE> BeanItemComboBox<BEANTYPE> buildAndBindComboBox(final String captionKey,
			final String property) {
		ensureNestedPropertyAdded(property);

		@SuppressWarnings("unchecked")
		final Class<BEANTYPE> clazz = this.fieldGroup	.getItemDataSource()
														.getItemProperty(property)
														.getType();

		final BeanItemComboBox<BEANTYPE> box = new BeanItemComboBox<BEANTYPE>(clazz,
				captionKey != null ? I18N.get(captionKey) : null);
		box.setFilteringMode(FilteringMode.CONTAINS);
		this.fieldGroup.bind(box, property);

		return this.onEnterKeyListener.installOn(box);
	}

	/**
	 * Creates a multiselect combobox with the given caption and binds it to the
	 * given property of the bean. The combobox is backed ba a bean item
	 * container.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the combobox to
	 * @return the multiselect combobox
	 */
	protected <BEANTYPE> BeanItemComboBoxMultiselect<BEANTYPE> buildAndBindComboBoxMultiselect(
			final Class<BEANTYPE> clazz, final String captionKey, final String property) {
		ensureNestedPropertyAdded(property);

		final BeanItemComboBoxMultiselect<BEANTYPE> box = new BeanItemComboBoxMultiselect<BEANTYPE>(clazz,
				captionKey != null ? I18N.get(captionKey) : null);
		box.setFilteringMode(FilteringMode.CONTAINS);
		this.fieldGroup.bind(box, property);

		return box;
	}

	/**
	 * Creates a select with the given caption and binds it to the given
	 * property of the bean. The list select is backed by a bean item container.
	 *
	 * @param clazz
	 *            type of BEANITEM
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param property
	 *            the property to bind the select to
	 * @return the select
	 */
	protected <BEANTYPE> BeanItemListSelect<BEANTYPE> buildAndBindListSelect(final Class<BEANTYPE> clazz,
			final String captionKey, final String property) {
		ensureNestedPropertyAdded(property);

		final BeanItemListSelect<BEANTYPE> listBox = new BeanItemListSelect<BEANTYPE>(clazz,
				captionKey != null ? I18N.get(captionKey) : null);
		listBox.setMultiSelect(true);
		listBox.setImmediate(true);
		listBox.setRows(5);

		// add reset button
		ResetButtonForListSelect.extend(listBox);

		this.fieldGroup.bind(listBox, property);

		return listBox;
	}

	private void ensureNestedPropertyAdded(final String propertyId) {
		if (this.fieldGroup.getItemDataSource() != null) {
			// The data source is set so the property must be found in the item.
			// If it is not we try to add it.
			if (this.fieldGroup	.getItemDataSource()
								.getItemProperty(propertyId) == null) {
				// Not found, try to add a nested property;
				// BeanItem property ids are always strings so this is safe
				this.fieldGroup	.getItemDataSource()
								.addNestedProperty(propertyId);
			}
		}
	}

	/**
	 * Creates a horizontal layout with the given caption and components. The
	 * space for the components will be distributed equally between all
	 * components.
	 *
	 * @param captionKey
	 *            the i18n key for the caption
	 * @param components
	 * @return
	 */
	protected VerticalLayout createFieldGroup(final String captionKey, final Component... components) {
		final VerticalLayout hl = new VerticalLayout(components);
		hl.setCaption(captionKey != null ? I18N.get(captionKey) : null);
		hl.setSpacing(true);

		for (final Component c : hl) {
			c.setWidth(100, Unit.PERCENTAGE);
		}

		return hl;
	}

	protected <C extends Component> C withWidth(final C component, final float width, final Unit unit) {
		component.setWidth(width, unit);
		return component;
	}

	protected <C extends Component> C withHeight(final C component, final float width, final Unit unit) {
		component.setHeight(width, unit);
		return component;
	}

	/**
	 * Creates an empty component with the height of 10 pixels to create a
	 * space.
	 *
	 * @return the spacer
	 */
	protected Component createSpacer() {
		final Label spacer = new Label();
		spacer.setWidth(100, Unit.PERCENTAGE);
		spacer.setHeight(10, Unit.PIXELS);

		return spacer;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void reset() {
		for (final Field f : this.fieldGroup.getFields()) {
			if (f instanceof CheckBox) {
				f.setValue(new Boolean(false));
			} else {
				f.setValue(null);
			}
		}
	}

	protected void doSubmit() {
		for (final SubmitListener<BEANTYPE> sl : this.submitListeners) {
			sl.onSubmit(getCommittedBean());
		}

		submitForm(getCommittedBean());
	}

	/**
	 * Override in case you need initialization. This method is called when the
	 * filter is attached to the UI.
	 */
	abstract protected void init();

	/**
	 * Layouts the component. Gets called at the end of the initialization step.
	 * In this method subclasses should arrange the available form fields. If
	 * the search filter has no primary search fields, <code>null</code> should
	 * be returned.
	 */
	protected abstract Component createPrimaryLayout();

	/**
	 * Layouts the component. Gets called at the end of the initialization step.
	 * In this method subclasses should arrange the available form fields. If
	 * the search filter has no secondary search fields, <code>null</code>
	 * should be returned.
	 */
	protected abstract Component createSecondaryLayout();

	/**
	 * Gets called when the form will be submitted (e.g. the enter button was
	 * pressed).
	 */
	protected void submitForm(BEANTYPE bean) {

	}

	protected BEANTYPE getBean() {
		return this.fieldGroup	.getItemDataSource()
								.getBean();
	}

	protected BeanFieldGroup<BEANTYPE> getFieldGroup() {
		return this.fieldGroup;
	}

	/**
	 * Returns the committed bean.
	 * 
	 * @return the committed bean
	 */
	public BEANTYPE getCommittedBean() {
		try {
			this.fieldGroup.commit();
		} catch (final CommitException e) {
			throw new RuntimeException("Could no commit form content", e);
		}
		return this.fieldGroup	.getItemDataSource()
								.getBean();
	}

	private class FilterLayoutWrapper extends CustomComponent {

		private static final long serialVersionUID = 1L;

		public FilterLayoutWrapper(final Component wrappedComponent, final boolean primary) {
			if (primary) {
				final HorizontalLayout mainPanel = new HorizontalLayout();

				AbstractBaseSearchFilter.this.searchBtn.addStyleName("filter-search-btn");
				AbstractBaseSearchFilter.this.searchBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
				AbstractBaseSearchFilter.this.searchBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				AbstractBaseSearchFilter.this.searchBtn.addStyleName(ValoTheme.BUTTON_HUGE);
				AbstractBaseSearchFilter.this.searchBtn.addClickListener(listener -> doSubmit());

				AbstractBaseSearchFilter.this.resetBtn.addStyleName("filter-reset-btn");
				AbstractBaseSearchFilter.this.resetBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
				AbstractBaseSearchFilter.this.resetBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				AbstractBaseSearchFilter.this.resetBtn.addStyleName(ValoTheme.BUTTON_HUGE);
				AbstractBaseSearchFilter.this.resetBtn.addClickListener(listener -> reset());

				mainPanel.addComponents(wrappedComponent, AbstractBaseSearchFilter.this.searchBtn,
										AbstractBaseSearchFilter.this.resetBtn);
				mainPanel.setExpandRatio(wrappedComponent, 1);
				mainPanel.setSizeFull();

				setCompositionRoot(mainPanel);

				// TODO
				// addAttachListener(event -> init0());
				setStyleName("main-filter primary-filter");
			} else {
				setCompositionRoot(wrappedComponent);
				setStyleName("main-filter secondary-filter");
			}

			setWidth(100, Unit.PERCENTAGE);
		}
	}

	@Override
	public <T> T getPrimaryFilterElements(final Class<T> clazz) {
		if (this.primaryLayout == null) {
			final Component filter = createPrimaryLayout();
			if (filter == null) {
				return null;
			}

			this.primaryLayout = new FilterLayoutWrapper(filter, true);
		}

		return clazz.cast(this.primaryLayout);
	}

	@Override
	public <T> T getSecondaryFilterElements(final Class<T> clazz) {
		if (this.secondaryLayout == null) {
			final Component filter = createSecondaryLayout();
			if (filter == null) {
				return null;
			}

			this.secondaryLayout = new FilterLayoutWrapper(filter, false);
		}

		return clazz.cast(this.secondaryLayout);
	}

	@Override
	public void addSubmitListener(final SubmitListener submitListener) {
		this.submitListeners.add(submitListener);
	}

	@Override
	public void removeSubmitListener(final SubmitListener submitListener) {
		this.submitListeners.remove(submitListener);
	}

	@Override
	public boolean isSecondaryFilterInitiallyExpanded() {
		return false;
	}

	/**
	 * @return the collapseOnSubmit
	 */
	@Override
	public boolean isCollapseOnSubmit() {
		return this.collapseOnSubmit;
	}

	/**
	 * If this setting is true, the secondary panel will be collapsed when the
	 * form is submitted. Default setting is true.
	 * 
	 * @param collapseOnSubmit
	 *            the collapseOnSubmit to set
	 */
	public void setCollapseOnSubmit(final boolean collapseOnSubmit) {
		this.collapseOnSubmit = collapseOnSubmit;
	}

	public void setSearchBtnVisible(final boolean visible) {
		this.searchBtn.setVisible(visible);
	}

	public void setResetBtnVisible(final boolean visible) {
		this.resetBtn.setVisible(visible);
	}

	@Override
	public boolean isExpandOnSubmit() {
		return this.expandOnSubmit;
	}

	/**
	 * If this setting is true, the secondary panel will be expanded when the
	 * form is submitted. Default setting is false.
	 * 
	 * @param expandOnSubmit
	 *            the expandOnSubmit to set
	 */
	public void setExpandOnSubmit(final boolean expandOnSubmit) {
		this.expandOnSubmit = expandOnSubmit;
	}
}
