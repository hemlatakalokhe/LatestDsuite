package de.bonprix.module.style;

import java.util.List;

import com.vaadin.ui.Tree;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.model.enums.Mode;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
public interface StyleView extends MvpView {

    interface Presenter extends MvpViewPresenter<StyleView> {

        void proceedCheckBox(Boolean value, NavigationRequest request);

        /**
         * Delete style.
         *
         * @param style the style
         */
        void deleteStyle(Style style);

        /**
         * Save inline edited style.
         *
         * @param style the style
         */
        void saveInlineEditedStyle(List<Style> style);

        /**
         * Filter style.
         *
         * @param styleNo the style no
         * @param country the country
         */
        void filterStyle(String styleNo, Country country);

        /**
         * Sets the tree.
         *
         * @param tree the new tree
         */
        void setTree(Tree tree);

        /**
         * Open dialog.
         *
         * @param style the style
         * @param mode the mode
         */
        void openDialog(Style style, Mode mode);

        /**
         * Open rating.
         */
        void openRating();

        /**
         * Sets the slider grid data.
         *
         * @param style the new slider grid data
         */
        void setSliderGridData(Style style);

        /**
         * Open delete dialog.
         *
         * @param style the style
         */
        void openDeleteDialog(Style style);

        /**
         * Refresh grid.
         */
        void refreshGrid();
    }

    void checkCheckBox(NavigationRequest request);

    /**
     * Sets the all style beans.
     *
     * @param beans the beans
     * @param countryBeans the country beans
     */
    void setAllStyleBeans(List<Style> beans, List<Country> countryBeans);

    /**
     * Sets the all filtered style beans.
     *
     * @param beans the new all filtered style beans
     */
    void setAllFilteredStyleBeans(List<Style> beans);

    /**
     * Display tree.
     *
     * @param tree the tree
     */
    void displayTree(Tree tree);

    /**
     * Sets the slider grid data.
     *
     * @param style the new slider grid data
     */
    void setSliderGridData(List<Style> style);

    /**
     * Resfresh grid.
     *
     * @param style the style
     */
    void resfreshGrid(List<Style> style);

}
