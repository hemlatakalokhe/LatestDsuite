package de.bonprix.module.style.dialogview.update;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Login;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.LoginBuilder;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.base.demo.service.ClientService;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.LoginService;
import de.bonprix.base.demo.service.SeasonService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.DialogEvent;
import de.bonprix.model.DialogModeEvent;
import de.bonprix.model.Paged;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.login.ui.LoginViewImpl;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.eventbus.EventHandler;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogPresenter;
import de.bonprix.vaadin.provider.UiNavigationProvider;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * The Class UpdateMvpDialogPresenter.
 * 
 * @author h.kalokhe
 *
 */
@SuppressWarnings("rawtypes")
@SpringPresenter
public class UpdateMvpDialogPresenter extends AbstractMvpDialogPresenter<UpdateMvpDialogView<UpdateMvpDialogView.Presenter>>
        implements UpdateMvpDialogView.Presenter {

    @Resource
    private StyleService styleService;

    @Resource
    private ClientService clientService;

    @Resource
    private SeasonService seasonService;

    @Resource
    private CountryService countryService;

    @Resource
    private LoginService loginService;

    @Resource
    private UiNotificationProvider notificationProvider;

    @Resource
    private UiNavigationProvider navigationProvider;

    @Resource
    private EventBus localEventBus;
    public static Long styleId;

    @PostConstruct
    public void initialize() {
        this.localEventBus.addHandler(this);
    }

    @EventHandler
    public void handle(final DialogModeEvent event) {
        getView().setHeadLine(event.getMode());
    }

    @EventHandler
    public void eventHandler(final DialogEvent event) {
        UpdateMvpDialogPresenter.styleId = 0l;
        if (event.getMode()
            .equals(Mode.EDIT)) {
            UpdateMvpDialogPresenter.styleId = event.getBean()
                .getId();
            getView().setData(event.getBean(), countries(), clients(), seasons(), event.getMode());
        }
        else {
            getView().setData(countries(), clients(), seasons(), event.getMode());
        }
    }

    @Override
    public void updateData(final String styleNo, final String styleDesc, final Country country, final Client client, final Season season, final Mode mode) {
        if (mode.equals(Mode.EDIT)) {
            final Style style = this.styleService.findById(UpdateMvpDialogPresenter.styleId);
            style.setStyleNo(styleNo);
            style.setDesc(styleDesc);
            style.setCountry(country);
            style.setClient(client);
            style.setSeason(season);
            this.styleService.saveStyle(style);
        }
        else if (mode.equals(Mode.ADD)) {
            final Style style = new StyleBuilder().withStyleNo(styleNo)
                .withDesc(styleDesc)
                .withCountry(country)
                .withClient(client)
                .withSeason(season)
                .build();
            this.styleService.saveStyle(style);
        }
    }

    @Override
    public void registerUser(final String username, final String password, final String firstname, final String lastname) {

        final List<Login> logins = new ArrayList<>();
        for (final Login login : this.loginService.findAll(new Paged(0, 100))) {
            if (username.equals(login.getUsername())) {
                logins.add(login);
            }
        }
        if (CollectionUtils.isNotEmpty(logins)) {
            this.notificationProvider.showErrorMessageBox("Username Already exist");
        }
        else {
            this.loginService.saveLogin(new LoginBuilder().withUsername(username)
                .withPassword(password)
                .withFirstname(firstname)
                .withLastname(lastname)
                .build());
        }
        this.navigationProvider.navigateTo(LoginViewImpl.VIEW_NAME);
    }

    public List<Client> clients() {
        return this.clientService.findAll();
    }

    public List<Season> seasons() {
        return this.seasonService.findAll();
    }

    public List<Country> countries() {
        return this.countryService.findAll();
    }

}
