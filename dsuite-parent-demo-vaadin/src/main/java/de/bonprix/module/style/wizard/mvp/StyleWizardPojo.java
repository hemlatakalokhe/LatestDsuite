package de.bonprix.module.style.wizard.mvp;

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;

public class StyleWizardPojo {

    private List<Country> countries;

    private List<Style> styles;

    private List<Season> seasons;

    private List<Client> clients;

    private Style style;

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(final Style style) {
        this.style = style;
    }

    public List<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(final List<Country> countries) {
        this.countries = countries;
    }

    public List<Style> getStyles() {
        return this.styles;
    }

    public void setStyles(final List<Style> styles) {
        this.styles = styles;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public void setClients(final List<Client> clients) {
        this.clients = clients;
    }

    public List<Season> getSeasons() {
        return this.seasons;
    }

    public void setSeasons(final List<Season> seasons) {
        this.seasons = seasons;
    }

}
