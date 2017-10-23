package de.bonprix.module.style;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Tree;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Item;
import de.bonprix.base.demo.dto.ItemSize;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.StyleOverViewFilter;
import de.bonprix.base.demo.dto.builder.StyleOverViewFilterBuilder;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.DialogEvent;
import de.bonprix.model.DialogModeEvent;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.style.dialogview.ratingstar.RatingStarDialogPresenter;
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogPresenter;
import de.bonprix.module.style.ui.StyleViewImpl;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
@SpringPresenter
public class StylePresenter extends AbstractMvpViewPresenter<StyleView> implements StyleView.StylePresenter {

    @Resource
    private StyleService styleService;

    @Resource
    private CountryService country;

    private Long id;
    @Resource
    private EventBus localEventBus;

    @PostConstruct
    public void initialize() {
        this.localEventBus.addHandler(this);
    }

    @Override
    public void init() {
        this.getView()
            .setAllStyleBeans(this.styleService.findAll(), this.country.findAll());
    }

    @Override
    public void onViewEnter() {
        //
    }

    @Override
    public void tryNavigateTo(final NavigationRequest request) {
        this.getView()
            .checkCheckBox(request);
    }

    @Override
    public void proceedCheckBox(final Boolean value, final NavigationRequest request) {
        if (!value) {
            super.tryNavigateTo(request);
            return;
        }
    }

    @Override
    public void deleteStyle(final Style style) {
        this.id = style.getId();
        this.styleService.deleteById(this.id);
        this.tryNavigateTo(new NavigationRequest(StyleViewImpl.VIEW_NAME));
    }

    @Override
    public void saveInlineEditedStyle(final List<Style> styledata) {

        for (final Style styles : styledata) {
            final Style styleDto = this.styleService.findById(styles.getId());
            styleDto.setStyleNo(styles.getStyleNo());
            styleDto.setDesc(styles.getDesc());
            styleDto.setCountry(styles.getCountry());
            this.styleService.saveStyle(styleDto);
        }

        this.tryNavigateTo(new NavigationRequest(StyleViewImpl.VIEW_NAME));
    }

    @Override
    public void filterStyle(final String styleNo, final Country country) {
        final StyleOverViewFilter styleFilter = new StyleOverViewFilterBuilder().withStyleNo(styleNo)
            .withCountry(country)
            .build();
        final List<Style> styles = this.styleService.findByFilter(styleFilter);
        this.getView()
            .setAllFilteredStyleBeans(styles);
    }

    @Override
    public void setTree(final Tree tree) {
        final List<Style> styles = this.styleService.findAll();

        for (final Style style : styles) {

            if (!(style.getItems()
                .isEmpty())) {
                tree.addItem(style.getStyleNo());
                tree.setItemIcon(style.getStyleNo(), FontAwesome.CART_PLUS);
                tree.setChildrenAllowed(style.getStyleNo(), true);

                for (final Item items : style.getItems()) {
                    tree.addItem(items);
                    tree.setParent(items, style.getStyleNo());

                    if (!(items.getItemSizes()
                        .isEmpty())) {
                        tree.setChildrenAllowed(items, true);

                        for (final ItemSize itemSize : items.getItemSizes()) {
                            tree.addItem(itemSize);
                            tree.setChildrenAllowed(itemSize, false);
                            tree.setParent(itemSize, items);
                        }
                    }
                    else {
                        tree.setChildrenAllowed(items, false);
                    }
                }
            }
            else {
                tree.setChildrenAllowed(style.getStyleNo(), false);
            }
        }
        this.getView()
            .displayTree(tree);
    }

    @Override
    public void openDialog(final Style style, final Mode mode) {
        final UpdateMvpDialogPresenter mvpDialogPresenter = createPresenter(UpdateMvpDialogPresenter.class);
        this.localEventBus.fireEvent(new DialogModeEvent(mode));
        mvpDialogPresenter.open();
        this.localEventBus.fireEvent(new DialogEvent(mode, style));
    }

    @Override
    public void openRating() {
        final RatingStarDialogPresenter dialogPresenter = createPresenter(RatingStarDialogPresenter.class);
        dialogPresenter.open();
        this.localEventBus.fireEvent(new DialogEvent(Mode.ADD, null));
    }

    @Override
    public void setSliderGridData(final Style style) {
        final List<Style> styles = Arrays.asList(style);
        getView().setSliderGridData(styles);
    }

    @Override
    public void openDeleteDialog(final Style style) {
        //
    }

    @Override
    public void refreshGrid() {
        getView().resfreshGrid(this.styleService.findAll());
    }

}