package de.bonprix.module.style.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.vaadin.addons.scrollablepanel.ScrollablePanel;
import org.vaadin.grid.cellrenderers.EditableRenderer.ItemEditListener;
import org.vaadin.sliderpanel.SliderPanelStyles;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;
import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState.TableSelectionMode;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.renderers.DateRenderer;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.style.StyleView;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.bean.grid.FilterHeader;
import de.bonprix.vaadin.bean.grid.builder.DateFieldRendererPropertiesBuilder;
import de.bonprix.vaadin.bean.grid.builder.TextFieldRendererPropertiesBuilder;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.messagebox.MessageBoxConfigurationBuilder;
import de.bonprix.vaadin.messagebox.MessageBoxIcon;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNavigationProvider;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarItem;
import de.bonprix.vaadin.ui.SliderPanelNoLayover;

/**
 * @author h.kalokhe
 *
 */
@SpringView(
    name = StyleViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class StyleViewImpl extends AbstractMvpView<StyleView.StylePresenter> implements StyleView {
    protected static final String COUNTRY = "country";
    protected static final String STYLE = "Style";
    protected static final String EDIT = "Edit";
    protected static final String DELETE = "Delete";
    protected static final String CLIENT = "client";
    protected static final String SEASON = "season";
    protected static final String STYLE_NO = "styleNo";
    protected static final String DESCRIPTION = "desc";
    protected static final String TREE = "Tree";
    protected static final String REFRESH = "Refresh";
    protected static final String LOGOUT = "Log Out";
    protected static final String DATE = "date";
    protected static final String ITEM = "items";
    protected static final String CREATE = "CREATE";
    protected static final String INLINEEDIT = "Update";

    private static final long serialVersionUID = 2688782241672861374L;
    public static final String VIEW_NAME = "Style";
    private BeanItemGrid<Style> styleGrid;
    private Tree tree;
    private List<Style> styles;
    private TextField filter;
    private BeanItemComboBox<Country> countryComboBox;
    private ItemEditListener<String> itemEdit;
    private Button inlineEdit;
    private Style inhabitant;
    private List<Style> inlineStyles;
    private FilterHeader filterHeader;
    private ItemEditListener<Date> dateEdit;
    private BeanItemGrid<Country> countryGrid;
    private SliderPanelNoLayover sliderPanelNoLayover;
    private ScrollablePanel scrollablePanel;
    private static int count = 0;
    @Resource
    private UiNavigationProvider navigationProvider;
    @Resource
    private UiNotificationProvider notificationProvider;

    @Override
    protected void initializeUI() {
        initializeGrid();
        initializeTree();
        initializeButtons();

        this.setCompositionRoot(FluentUI.horizontal()
            .add(sliderPanel())
            .sizeFull()
            .spacing(true)
            .margin(false, false, false, false)
            .get());
    }

    public Component mainLayout() {
        return FluentUI.vertical()
            .add(FluentUI.horizontal()
                .add(this.styleGrid, this.tree)
                .spacing(true)
                .get())
            .spacing(true)
            .margin(false, false, false, true)
            .get();
    }

    public Component sliderPanel() {
        this.sliderPanelNoLayover = new SliderPanelNoLayover(scrollablePanel(), subLayout(), SliderMode.LEFT, SliderTabPosition.MIDDLE, "Countries",
                SliderPanelStyles.COLOR_WHITE, 400, 0, Unit.PERCENTAGE);

        return this.sliderPanelNoLayover;
    }

    public Component subLayout() {
        return FluentUI.vertical()
            .add(this.countryGrid)
            .sizeFull()
            .get();
    }

    public Component scrollablePanel() {
        this.scrollablePanel = new ScrollablePanel();
        this.scrollablePanel.setWidth("50%");
        this.scrollablePanel.setHeight("400px");
        this.scrollablePanel.setStyleName("scroll-box");
        this.scrollablePanel.setContent(mainLayout());
        return this.scrollablePanel;
    }

    public void initializeButtons() {
        addMenuElement(new ComponentBarItem(StyleViewImpl.CREATE, StyleViewImpl.CREATE));
        addMenuElement(new ComponentBarItem(StyleViewImpl.STYLE, StyleViewImpl.STYLE).withParentId(StyleViewImpl.CREATE)
            .withClickAction(() -> getPresenter().openDialog(new Style(), Mode.ADD)));
        addMenuElement(new ComponentBarItem("Edit", "Edit").withClickAction(() -> getPresenter().openDialog(getSelectedBean(), Mode.EDIT)));
        addMenuElement(new ComponentBarItem(StyleViewImpl.DELETE, StyleViewImpl.DELETE).withClickAction(() -> delete(getSelectedBean())));
        addMenuElement(new ComponentBarItem(StyleViewImpl.TREE, StyleViewImpl.TREE).withClickAction(() -> getTreeView()));
        addMenuElement(new ComponentBarItem(StyleViewImpl.REFRESH, StyleViewImpl.REFRESH).withClickAction(() -> getPresenter().refreshGrid()));
        addMenuElement(new ComponentBarItem(StyleViewImpl.INLINEEDIT, StyleViewImpl.INLINEEDIT).withClickAction(() -> inlineEditing()));
        addMenuElement(new ComponentBarItem(StyleViewImpl.LOGOUT, StyleViewImpl.LOGOUT).withClickAction(() -> logout()));

        this.inlineStyles = new ArrayList<>();
        this.styles = new ArrayList<>();
        this.filter = new TextField();
        this.filter.setInputPrompt("Style No");
        this.countryGrid = new BeanItemGrid<>(Country.class);
        this.countryGrid.setColumns("id", "name", "isoCode");
        this.countryComboBox = new BeanItemComboBox<>(Country.class);
        this.countryComboBox.setInputPrompt("Country");
    }

    private void logout() {
        this.notificationProvider.showMessageBox(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
            .withHtmlMessage("Do you want to exit?")
            .withButton(DialogButton.YES, () -> getPresenter().openRating())
            .withButton(DialogButton.NO, () -> this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME))
            .build());
    }

    private void getTreeView() {
        StyleViewImpl.count++;
        this.tree.setCaption("Styles");
        this.getPresenter()
            .setTree(this.tree);
        if (StyleViewImpl.count % 2 != 0) {
            this.tree.setVisible(true);
        }
        else {
            this.tree.collapseItem(this.tree.getValue());
            this.tree.setVisible(false);
        }
    }

    private Style getSelectedBean() {

        return this.styleGrid.getSelectedItem();
    }

    public void initializeGrid() {
        this.styleGrid = new BeanItemGrid<>(Style.class);
        this.styleGrid.setSelectionMode(TableSelectionMode.SINGLE);
        this.styleGrid.setColumns("id", StyleViewImpl.DESCRIPTION, StyleViewImpl.STYLE_NO, StyleViewImpl.COUNTRY, StyleViewImpl.DATE);
        this.styleGrid.sort("id", SortDirection.ASCENDING);
        this.filterHeader = this.styleGrid.addFilterHeader();
        this.filterHeader.addStringFilter(StyleViewImpl.DESCRIPTION, "Description");
        this.dateEdit = event -> StyleViewImpl.this.styleGrid.getColumn(StyleViewImpl.DATE);
        this.styleGrid.setDateFieldRenderer(StyleViewImpl.DATE, new DateFieldRendererPropertiesBuilder().withItemEditListener(this.dateEdit)
            .withItemClickListener(event -> {
                this.inhabitant = new Style();

                this.inhabitant = (Style) event.getItemId();
                this.inlineStyles.add(this.inhabitant);

                this.inlineEdit.addClickListener(e4 -> {
                    if (CollectionUtils.isNotEmpty(this.inlineStyles)) {
                        this.notificationProvider.showMessageBox(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
                            .withHtmlMessage("Do you want to Update")
                            .withButton(DialogButton.YES, () -> this.getPresenter()
                                .saveInlineEditedStyle(this.inlineStyles))
                            .withButton(DialogButton.NO, () -> this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME))
                            .build());
                    }
                });
            })
            .build());
        this.styleGrid.setTextFieldRenderer(StyleViewImpl.DESCRIPTION, new TextFieldRendererPropertiesBuilder().withItemClickListener(event -> {
            this.inhabitant = new Style();
            this.inhabitant = (Style) event.getItemId();
            this.inlineStyles.add(this.inhabitant);

        })
            .withItemEditListener(this.itemEdit)
            .build());
        this.styleGrid.getColumn(StyleViewImpl.DATE)
            .setRenderer(new DateRenderer(new SimpleDateFormat("dd.MM.yyyy")));
        this.filterHeader.addDateFilter(StyleViewImpl.DATE);
        this.styleGrid.setWidth(40, Unit.CM);
    }

    public void initializeTree() {
        this.tree = new Tree();
    }

    public void inlineEditing() {
        if (CollectionUtils.isNotEmpty(this.inlineStyles)) {
            this.notificationProvider.showMessageBox(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
                .withHtmlMessage("Do you want to Update")
                .withButton(DialogButton.YES, () -> this.getPresenter()
                    .saveInlineEditedStyle(this.inlineStyles))
                .withButton(DialogButton.NO, () -> this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME))
                .build());
        }
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
    }

    public void delete(final Style style) {
        this.notificationProvider.showMessageBox(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
            .withHtmlMessage("Do you want to delete")
            .withButton(DialogButton.YES, () -> getPresenter().deleteStyle(style))
            .withButton(DialogButton.NO, () -> this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME))
            .build());

    }

    @Override
    public void setAllStyleBeans(final List<Style> beans, final List<Country> coutryBeans) {
        this.styles = beans;
        this.countryComboBox.addAllBeans(coutryBeans);
        this.styleGrid.addAllBeans(this.styles);
        this.countryGrid.addAllBeans(coutryBeans);
        this.filterHeader.addComboBoxFilter(StyleViewImpl.COUNTRY, "Country", coutryBeans);

    }

    @Override
    public void setAllFilteredStyleBeans(final List<Style> beans) {
        this.styleGrid.replaceAllBeans(beans);
    }

    @Override
    public void displayTree(final Tree tree) {
        this.tree = tree;
    }

    @Override
    public void setSliderGridData(final List<Style> style) {
    }

    @Override
    public void resfreshGrid(final List<Style> style) {
        if (this.tree.isVisible()) {
            this.tree.setVisible(false);
            StyleViewImpl.count++;
        }
        this.styleGrid.replaceAllBeans(style);
    }

}
