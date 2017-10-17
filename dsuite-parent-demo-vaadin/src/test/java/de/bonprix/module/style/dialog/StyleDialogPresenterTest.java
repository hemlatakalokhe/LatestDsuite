package de.bonprix.module.style.dialog;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.I18NAwareUnitTest;
import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Login;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.ClientBuilder;
import de.bonprix.base.demo.dto.builder.CountryBuilder;
import de.bonprix.base.demo.dto.builder.LoginBuilder;
import de.bonprix.base.demo.dto.builder.SeasonBuilder;
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
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogPresenter;
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogView;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.provider.UiNavigationProvider;

public class StyleDialogPresenterTest extends I18NAwareUnitTest {
    @InjectMocks
    private UpdateMvpDialogPresenter updateMvpDialogPresenter;

    @SuppressWarnings("rawtypes")
    @Mock
    private UpdateMvpDialogView updateMvpDialogView;

    @Mock
    private EventBus localEevntBus;

    @Mock
    private SeasonService seasonService;

    @Mock
    private ClientService clientService;

    @Mock
    private StyleService styleService;

    @Mock
    private CountryService countryService;

    @Mock
    private LoginService loginService;

    @Mock
    private UiNavigationProvider navigationProvider;

    private DialogModeEvent dialogModeEvent;

    private List<Season> seasons;

    private List<Client> clients;

    private List<Country> countries;

    private List<Login> logins;

    private Login login;

    private Style style;

    @BeforeMethod
    private void initialize() {
        this.style = new StyleBuilder().withId(1L)
            .build();
        this.seasons = Arrays.asList(new SeasonBuilder().withId(1L)
            .build());
        this.clients = Arrays.asList(new ClientBuilder().withId(1L)
            .build());
        this.countries = Arrays.asList(new CountryBuilder().withId(1L)
            .build());
        this.logins = Arrays.asList(new LoginBuilder().withId(1L)
            .build());
        this.dialogModeEvent = new DialogModeEvent(Mode.ADD);
        this.login = new LoginBuilder().withFirstname("ABC")
            .withLastname("XYZ")
            .withUsername("PQR")
            .withPassword("LMN")
            .build();
    }

    @Test
    private void init() {
        MockitoAnnotations.initMocks(this);
        this.updateMvpDialogPresenter.initialize();
        Mockito.verify(this.localEevntBus)
            .addHandler(this.updateMvpDialogPresenter);
    }

    @Test
    private void setHeadline() {
        this.updateMvpDialogPresenter.handle(this.dialogModeEvent);
        Mockito.verify(this.updateMvpDialogView)
            .setHeadLine(Mode.ADD);
    }

    @SuppressWarnings("unchecked")
    @Test
    private void eventHandlerAdd() {
        Mockito.when(this.seasonService.findAll())
            .thenReturn(this.seasons);
        Mockito.when(this.clientService.findAll())
            .thenReturn(this.clients);
        Mockito.when(this.countryService.findAll())
            .thenReturn(this.countries);
        this.updateMvpDialogPresenter.eventHandler(new DialogEvent(Mode.ADD, new StyleBuilder().build()));
        Mockito.verify(this.updateMvpDialogView)
            .setData(this.countries, this.clients, this.seasons, Mode.ADD);
    }

    @SuppressWarnings("unchecked")
    @Test
    private void eventHandlerEdit() {
        Mockito.when(this.seasonService.findAll())
            .thenReturn(this.seasons);
        Mockito.when(this.clientService.findAll())
            .thenReturn(this.clients);
        Mockito.when(this.countryService.findAll())
            .thenReturn(this.countries);
        this.updateMvpDialogPresenter.eventHandler(new DialogEvent(Mode.EDIT, new StyleBuilder().withId(1L)
            .build()));
        Mockito.verify(this.updateMvpDialogView)
            .setData(this.style, this.countries, this.clients, this.seasons, Mode.EDIT);
    }

    @Test
    private void updateData() {
        Mockito.when(this.styleService.findById(Mockito.anyLong()))
            .thenReturn(this.style);
        this.style.setDesc("Jeans");
        Mockito.when(this.styleService.saveStyle(this.style))
            .thenReturn(this.style);
        this.updateMvpDialogPresenter.updateData("", "", new CountryBuilder().withId(1L)
            .build(), new ClientBuilder().withId(1L)
                .build(), new SeasonBuilder().withId(1L)
                    .build(), Mode.EDIT);
    }

    @Test
    private void saveData() {
        this.style.setDesc("Jeans");
        Mockito.when(this.styleService.saveStyle(this.style))
            .thenReturn(this.style);
        this.updateMvpDialogPresenter.updateData("", "", new CountryBuilder().withId(1L)
            .build(), new ClientBuilder().withId(1L)
                .build(), new SeasonBuilder().withId(1L)
                    .build(), Mode.ADD);
    }

    @Test
    private void registerUser() {
        Mockito.when(this.loginService.findAll(Mockito.any(Paged.class)))
            .thenReturn(this.logins);
        this.updateMvpDialogPresenter.registerUser("", "", "", "");
        Mockito.verify(this.loginService)
            .saveLogin(this.login);
        Mockito.verify(this.navigationProvider)
            .navigateTo(Mockito.anyString());

    }
}