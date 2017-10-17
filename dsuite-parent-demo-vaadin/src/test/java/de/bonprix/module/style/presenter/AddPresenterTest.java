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
import de.bonprix.module.add.AddPresenter;
import de.bonprix.module.add.AddView;
import de.bonprix.vaadin.provider.UiNavigationProvider;

public class AddPresenterTest extends I18NAwareUnitTest {

    @InjectMocks
    private AddPresenter addPresenter;

    @Mock
    private AddView addView;

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
    public void onViewEnter() {

        final List<Style> styles = Arrays.asList(new StyleBuilder().withId(1L)
            .build());
        final List<Country> countries = Arrays.asList(new CountryBuilder().withId(1L)
            .build());
        Mockito.when(this.styleService.findAll())
            .thenReturn(styles);
        Mockito.when(this.countryService.findAll())
            .thenReturn(countries);
        this.addPresenter.onViewEnter();
        Mockito.verify(this.addView)
            .setAllStyleBeans(styles);
        Mockito.verify(this.addView)
            .setAllCountryBeans(countries);
    }

    @Test
    public void saveStyle() {
        final Style style = new StyleBuilder().build();
        Mockito.when(this.styleService.saveStyle(Mockito.any(Style.class)))
            .thenReturn(style);
        this.addPresenter.saveStyle("", "", new CountryBuilder().build());
        Mockito.verify(this.navigationProvider)
            .navigateTo(Mockito.anyString());
    }
}
