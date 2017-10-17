package de.bonprix.module.style.presenter;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import de.bonprix.I18NAwareUnitTest;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.CountryBuilder;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.module.update.UpdatePresenter;
import de.bonprix.module.update.UpdateView;
import de.bonprix.vaadin.provider.UiNavigationProvider;

public class UpdatePresenterTest extends I18NAwareUnitTest {

    @InjectMocks
    private UpdatePresenter updatePresenter;

    @Mock
    private UpdateView updateView;

    @Mock
    private StyleService styleService;

    @Mock
    private CountryService countryService;

    @Mock
    private UiNavigationProvider navigationProvider;

    @Test
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateStyle() {
        final Style style = new StyleBuilder().withId(1L)
            .build();
        final List<Country> countries = Arrays.asList(new CountryBuilder().withId(1L)
            .build());
        Mockito.when(this.countryService.findAll())
            .thenReturn(countries);
        this.updatePresenter.updateStyle(style);
        Mockito.verify(this.countryService)
            .findAll();
        Mockito.verify(this.updateView)
            .setCountryBeans(countries);
        Mockito.verify(this.updateView)
            .setFields(style.getStyleNo(), style.getDesc(), style.getCountry());
    }

    @Test
    public void saveUpdatedStyle() {
        final Style style = new StyleBuilder().withId(1L)
            .build();
        Mockito.when(this.styleService.findById(Mockito.anyLong()))
            .thenReturn(style);
        this.updatePresenter.saveUpdatedStyle("", "", new CountryBuilder().withId(1L)
            .build());
        Mockito.when(this.styleService.saveStyle(Mockito.any(Style.class)))
            .thenReturn(style);
        Mockito.verify(this.styleService)
            .findById(Mockito.anyLong());
        Mockito.verify(this.styleService)
            .saveStyle(style);
        Mockito.verify(this.navigationProvider)
            .navigateTo(Mockito.anyString());
    }
}
