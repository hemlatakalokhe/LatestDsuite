package de.bonprix.vaadin.fluentui;

import org.vaadin.addons.tokenfilter.TokenFilter;
import org.vaadin.addons.tokenfilter.TokenPopupButton;
import org.vaadin.addons.tokenfilter.model.FilterType;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.bean.field.BeanItemExtendedOptionGroup;
import de.bonprix.vaadin.bean.field.BeanItemListSelect;
import de.bonprix.vaadin.fluentui.Layouts.AbstractLayouts;
import de.bonprix.vaadin.fluentui.Layouts.AbstractOrderedLayouts;
import de.bonprix.vaadin.fluentui.Layouts.CssLayouts;
import de.bonprix.vaadin.information.InformationLabel;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.ComponentBar;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class FluentUI {

    private FluentUI() {
        // make default constructor private to prevent initialization
    }

    /**
     * Creates and returns a new {@link Components} instance using the provided {@code component} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <C extends Component, CONFIG extends Components<?, ?>> Components<C, CONFIG> componentOf(final C component) {
        return (Components<C, CONFIG>) new Components<C, CONFIG>(component).defaults();
    }

    /**
     * Creates and returns a new {@link Fields} instance using the provided {@code field} with defaults already set.
     */
    public static <FIELD extends AbstractField<T>, T, CONFIG extends AbstractFields<FIELD, T, CONFIG>> AbstractFields<FIELD, T, CONFIG> fieldOf(
            final FIELD field) {
        return (AbstractFields<FIELD, T, CONFIG>) new AbstractFields<FIELD, T, CONFIG>(field).defaults();
    }

    /**
     * Creates and returns a new {@link TextFields} instance with an internally created {@link TextField} with defaults already set.
     */
    public static <CONFIG extends TextFields<CONFIG>> TextFields<CONFIG> textField() {
        return (TextFields<CONFIG>) new TextFields<CONFIG>(new TextField()).defaults();
    }

    /**
     * Provides a fluent API to create a i18n grid for term translations
     * 
     * @param containerArg
     * @param languagePropertyArg
     * @return
     */
    public static <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> LanguageGrid<CONTAINER, ELEMENT> languageGrid(
            Class<ELEMENT> propertyClazz, final BeanFieldGroup<CONTAINER> fieldGroup) {
        return new LanguageGrid<CONTAINER, ELEMENT>(propertyClazz, fieldGroup);
    }

    /**
     * Provides a fluent API to create a components array containing language textfields for specific properties
     * 
     * @param propertyClazz
     * @param fieldGroup
     */
    public static <CONFIG extends LanguageTextFields<CONFIG, CONTAINER, ELEMENT>, CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> LanguageTextFields<CONFIG, CONTAINER, ELEMENT> languageTextFields(
            Class<ELEMENT> propertyClazz, BeanFieldGroup<CONTAINER> fieldGroup) {
        return (LanguageTextFields<CONFIG, CONTAINER, ELEMENT>) new LanguageTextFields<CONFIG, CONTAINER, ELEMENT>(propertyClazz, fieldGroup);
    }

    /**
     * Creates and returns a new {@link TextFields} instance with an internally created {@link TextField} with defaults already set.
     */
    public static <CONFIG extends TextAreas<CONFIG>> TextAreas<CONFIG> textArea() {
        return (TextAreas<CONFIG>) new TextAreas<CONFIG>(new TextArea()).defaults();
    }

    /**
     * Creates and returns a new {@link TextFields} instance with an internally created {@link TextField} with defaults already set.
     */
    public static <CONFIG extends RichTextAreas<CONFIG>> RichTextAreas<CONFIG> richTextArea() {
        return (RichTextAreas<CONFIG>) new RichTextAreas<CONFIG>(new RichTextArea()).defaults();
    }

    /**
     * Creates and returns a new {@link DateFields} instance with an internally created {@link DateField} with defaults already set.
     */
    public static <CONFIG extends DateFields<CONFIG>> DateFields<CONFIG> dateField() {
        return (DateFields<CONFIG>) new DateFields<CONFIG>(new DateField()).defaults();
    }

    /**
     * Creates and returns a new {@link Sliders} instance with an internally created {@link Slider} with defaults already set.
     */
    public static <CONFIG extends Sliders<CONFIG>> Sliders<CONFIG> slider() {
        return (Sliders<CONFIG>) new Sliders<CONFIG>(new Slider()).defaults();
    }

    /**
     * Creates and returns a new {@link TextFields} instance with an internally created {@link TextField} with defaults already set.
     */
    public static <CONFIG extends ProgressBars<CONFIG>> ProgressBars<CONFIG> progressBar() {
        return (ProgressBars<CONFIG>) new ProgressBars<CONFIG>(new ProgressBar()).defaults();
    }

    /**
     * Creates and returns a new {@link TextFields} instance with an internally created {@link TextField} with defaults already set.
     */
    public static <CONFIG extends TokenFilters<CONFIG, FILTERTYPE>, FILTERTYPE extends FilterType<?>> TokenFilters<CONFIG, FILTERTYPE> tokenFilter(
            final Class<FILTERTYPE> clazz) {
        return new TokenFilters<CONFIG, FILTERTYPE>(new TokenFilter<>()).defaults();
    }

    /**
     * Creates and returns a new {@link BeanItemComboBoxs} instance with an internally created {@link BeanItemComboBox} with defaults already set.
     */
    public static <CONFIG extends BeanItemComboBoxs<CONFIG, BEANTYPE>, BEANTYPE> BeanItemComboBoxs<CONFIG, BEANTYPE> beanItemComboBox(
            final Class<BEANTYPE> clazz) {
        return new BeanItemComboBoxs<CONFIG, BEANTYPE>(new BeanItemComboBox<>(clazz)).defaults();
    }

    /**
     * Creates and returns a new {@link BeanItemComboBoxMultiselects} instance with an internally created {@link BeanItemComboBoxMultiselect} with defaults
     * already set.
     */
    public static <CONFIG extends BeanItemComboBoxMultiselects<CONFIG, BEANTYPE>, BEANTYPE> BeanItemComboBoxMultiselects<CONFIG, BEANTYPE> beanItemComboBoxMultiselect(
            final Class<BEANTYPE> clazz) {
        return new BeanItemComboBoxMultiselects<CONFIG, BEANTYPE>(new BeanItemComboBoxMultiselect<>(clazz)).defaults();
    }

    /**
     * Creates and returns a new {@link BeanItemListSelects} instance with an internally created {@link BeanItemListSelect} with defaults already set.
     */
    public static <CONFIG extends BeanItemExtendedOptionGroups<CONFIG, BEANTYPE>, BEANTYPE> BeanItemExtendedOptionGroups<CONFIG, BEANTYPE> beanItemExtendedOptionGroup(
            final Class<BEANTYPE> clazz) {
        return new BeanItemExtendedOptionGroups<CONFIG, BEANTYPE>(new BeanItemExtendedOptionGroup<>(clazz)).defaults();
    }

    /**
     * Creates and returns a new {@link BeanItemListSelects} instance with an internally created {@link BeanItemListSelect} with defaults already set.
     */
    public static <CONFIG extends BeanItemListSelects<CONFIG, BEANTYPE>, BEANTYPE> BeanItemListSelects<CONFIG, BEANTYPE> beanItemListSelect(
            final Class<BEANTYPE> clazz) {
        return new BeanItemListSelects<CONFIG, BEANTYPE>(new BeanItemListSelect<>(clazz)).defaults();
    }

    /**
     * Creates and returns a new {@link TextFields} instance using the provided {@code textField}.
     */
    public static <CONFIG extends TextFields<CONFIG>> TextFields<CONFIG> textFieldOf(final TextField textField) {
        return (TextFields<CONFIG>) new TextFields<CONFIG>(textField).defaults();
    }

    /**
     * Creates and returns a new {@link PasswordFields} instance with an internally created {@link PasswordField} with defaults already set.
     */
    public static <CONFIG extends PasswordFields<CONFIG>> PasswordFields<CONFIG> passwordField() {
        return (PasswordFields<CONFIG>) new PasswordFields<CONFIG>(new PasswordField()).defaults();
    }

    /**
     * Creates and returns a new {@link PasswordFields} instance using the provided {@code passwordField}.
     */
    public static <CONFIG extends PasswordFields<CONFIG>> PasswordFields<CONFIG> passwordFieldOf(final PasswordField passwordField) {
        return (PasswordFields<CONFIG>) new PasswordFields<CONFIG>(passwordField).defaults();
    }

    /**
     * Creates and returns a new {@link Fields} instance with an internally created {@link CheckBox} with defaults already set.
     */
    public static <CONFIG extends AbstractFields<CheckBox, Boolean, CONFIG>> AbstractFields<CheckBox, Boolean, CONFIG> checkBox() {
        return (AbstractFields<CheckBox, Boolean, CONFIG>) new AbstractFields<CheckBox, Boolean, CONFIG>(new CheckBox()).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractSelects} instance with an internally created {@link OptionGroup} with defaults already set.
     */
    public static <CONFIG extends AbstractSelects<OptionGroup, CONFIG>> AbstractSelects<OptionGroup, CONFIG> optionGroup() {
        return (AbstractSelects<OptionGroup, CONFIG>) new AbstractSelects<OptionGroup, CONFIG>(new OptionGroup()).defaults();
    }

    /**
     * Creates and returns a new {@link Labels} instance with an internally created {@link Label} with defaults already set.
     */
    public static <CONFIG extends Labels<CONFIG>> Labels<CONFIG> label() {
        return (Labels<CONFIG>) new Labels<CONFIG>(new Label()).defaults();
    }

    /**
     * Creates and returns a new {@link Labels} instance with an internally created {@link Label} with defaults already set.
     */
    public static <CONFIG extends Labels<CONFIG>> Labels<CONFIG> spinner() {
        return (Labels<CONFIG>) new Labels<CONFIG>(new Label()).defaults()
                .style(DSuiteTheme.LABEL_SPINNER);
    }

    /**
     * Creates and returns a new {@link InformationLabels} using a newly created {@link InformationLabel} with defaults already set.
     */
    public static <CONFIG extends InformationLabels<CONFIG>> InformationLabels<CONFIG> informationLabel() {
        return (InformationLabels<CONFIG>) new InformationLabels<CONFIG>(new InformationLabel()).defaults();
    }

    /**
     * Creates and returns a new {@link MenuBars} instance with an internally created {@link MenuBar} with defaults already set.
     */
    public static <CONFIG extends MenuBars<CONFIG>> MenuBars<CONFIG> menuBar() {
        return (MenuBars<CONFIG>) new MenuBars<CONFIG>(new MenuBar()).defaults();
    }

    /**
     * Creates and returns a new {@link Labels} instance with an internally created {@link Label} with an line break.
     */
    public static <CONFIG extends Labels<?>> Labels<CONFIG> labelLineBreak() {
        return new Labels<>(new Label("<br>", ContentMode.HTML));
    }

    /**
     * Creates and returns a new {@link Buttons} instance with an internally created {@link Button} with defaults already set.
     */
    public static <CONFIG extends Buttons<CONFIG>> Buttons<CONFIG> button() {
        return (Buttons<CONFIG>) new Buttons<CONFIG>(new Button()).defaults();
    }

    /**
     * Creates and returns a new {@link Links} instance with an internally created {@link Link} with defaults already set.
     */
    public static <CONFIG extends Links<CONFIG>> Links<CONFIG> link() {
        return (Links<CONFIG>) new Links<CONFIG>(new Link()).defaults();
    }

    /**
     * Creates and returns a new {@link ComponentBars} instance with an internally created {@link ComponentBars} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends ComponentBars<?>> ComponentBars<CONFIG> componentBar() {
        return (ComponentBars<CONFIG>) new ComponentBars<CONFIG>(new ComponentBar()).defaults();
    }

    /**
     * Creates and returns a new {@link Buttons} instance using the provided {@code button}.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends Buttons<?>> Buttons<CONFIG> buttonOf(final Button button) {
        return (Buttons<CONFIG>) new Buttons<CONFIG>(button).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractOrderedLayouts} using a newly created {@link VerticalLayout} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends AbstractOrderedLayouts<VerticalLayout, ?>> AbstractOrderedLayouts<VerticalLayout, CONFIG> vertical() {
        return (AbstractOrderedLayouts<VerticalLayout, CONFIG>) new AbstractOrderedLayouts<VerticalLayout, CONFIG>(new VerticalLayout()).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractLayouts} using a newly created {@link CssLayout} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends CssLayouts<CssLayout, ?>> CssLayouts<CssLayout, CONFIG> css() {
        return (CssLayouts<CssLayout, CONFIG>) new CssLayouts<CssLayout, CONFIG>(new CssLayout()).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractLayouts} using a newly created {@link CssLayout} with defaults already set.
     * 
     * @param title
     * @param field
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends CssLayouts<CssLayout, ?>> CssLayouts<CssLayout, CONFIG> tokenPopupButton(final String title, final AbstractSelect field) {
        return (CssLayouts<CssLayout, CONFIG>) new CssLayouts<CssLayout, CONFIG>(new TokenPopupButton(title, field)).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractOrderedLayouts} using a newly created {@link HorizontalLayout} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends AbstractOrderedLayouts<HorizontalLayout, ?>> AbstractOrderedLayouts<HorizontalLayout, CONFIG> horizontal() {
        return (AbstractOrderedLayouts<HorizontalLayout, CONFIG>) new AbstractOrderedLayouts<HorizontalLayout, CONFIG>(new HorizontalLayout()).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractOrderedLayouts} using a newly created {@link FormLayout} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends AbstractOrderedLayouts<FormLayout, ?>> AbstractOrderedLayouts<FormLayout, CONFIG> form() {
        return (AbstractOrderedLayouts<FormLayout, CONFIG>) new AbstractOrderedLayouts<FormLayout, CONFIG>(new FormLayout()).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractOrderedLayouts} using a newly created {@link FormLayout} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends Panels<Panel, ?>> Panels<Panel, CONFIG> panel() {
        return (Panels<Panel, CONFIG>) new Panels<Panel, CONFIG>(new Panel()).defaults();
    }

    /**
     * Creates and returns a new {@link AbstractOrderedLayouts} using the provided {@link AbstractOrderedLayout} with defaults already set.
     */
    @SuppressWarnings("unchecked")
    public static <L extends AbstractOrderedLayout, CONFIG extends AbstractOrderedLayouts<L, ?>> AbstractOrderedLayouts<L, CONFIG> layoutOf(final L layout) {
        return (AbstractOrderedLayouts<L, CONFIG>) new AbstractOrderedLayouts<L, CONFIG>(layout).defaults();
    }

    /**
     * Creates and returns a new {@link Layouts} using the provided {@link Layout} with defaults already set.
     */
    public static <CONFIG extends Layouts<Layout, ?>> Layouts<Layout, ?> layoutOf(final Layout layout) {
        return new Layouts<Layout, CONFIG>(layout).defaults();
    }

    /**
     * Creates and returns a new Tabsheet {@link TabSheet}
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends TabSheets<?>> TabSheets<CONFIG> tabSheet() {
        return (TabSheets<CONFIG>) new TabSheets<CONFIG>(new TabSheet()).defaults();
    }

    /**
     * Creates and returns a new {@link Components} instance using the provided {@code component} with defaults already set.
     */
    public static <TABSHEET extends TabSheet, CONFIG extends TabSheets<CONFIG>> TabSheets<CONFIG> tabsheetOf(final TABSHEET tabSheet) {
        return (TabSheets<CONFIG>) new TabSheets<CONFIG>(tabSheet);
    }

    /**
     * Creates and returns a new Tabsheet {@link Tab}
     * 
     * @param component
     */
    public static <CONFIG extends Tabs<CONFIG>> Tabs<CONFIG> tab(Component component) {
        TabSheet fakeTabSheet = new TabSheet();
        return (Tabs<CONFIG>) new Tabs<CONFIG>(fakeTabSheet.addTab(component)).defaults();
    }

    /**
     * Creates and returns a new Tabsheet {@link TabSheet}
     */
    @SuppressWarnings("unchecked")
    public static <CONFIG extends Images<?>> Images<CONFIG> image() {
        return (Images<CONFIG>) new Images<CONFIG>(new Image()).defaults();
    }

}
