package de.bonprix.module.style.dialogview.update;

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.model.enums.Mode;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

/**
 * @author h.kalokhe
 *
 * @param <PRESENTER>
 */
public interface UpdateMvpDialogView<PRESENTER extends UpdateMvpDialogView.Presenter> extends MvpDialogView<PRESENTER> {
    interface Presenter extends MvpDialogPresenter {

        /**
         * Update data.
         *
         * @param styleNo the style no
         * @param styleDesc the style desc
         * @param country the country
         * @param client the client
         * @param season the season
         * @param mode the mode
         */
        void updateData(final String styleNo, final String styleDesc, final Country country, final Client client, final Season season, final Mode mode);

        /**
         * Register user.
         *
         * @param username the username
         * @param password the password
         * @param firstname the firstname
         * @param lastname the lastname
         */
        void registerUser(final String username, final String password, final String firstname, final String lastname);
    }

    /**
     * Sets the datl̥a.
     *
     * @param style the style
     * @param countries the countries
     * @param clients the clients
     * @param seasons the seasons
     * @param mode the mode
     */
    void setData(Style style, List<Country> countries, List<Client> clients, List<Season> seasons, Mode mode);

    /**
     * Sets the data.
     *
     * @param countries the countries
     * @param clients the clients
     * @param seasons the seasons
     * @param mode the mode
     */
    void setData(List<Country> countries, List<Client> clients, List<Season> seasons, Mode mode);

    /**
     * Sets the head line.
     *
     * @param mode the new head line
     */
    void setHeadLine(Mode mode);

}
