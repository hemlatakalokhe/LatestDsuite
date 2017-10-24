package de.bonprix.module.style.wizard;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.I18NAwareUnitTest;
import de.bonprix.base.demo.dto.Item;
import de.bonprix.base.demo.dto.ItemSize;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.ClientBuilder;
import de.bonprix.base.demo.dto.builder.CountryBuilder;
import de.bonprix.base.demo.dto.builder.ItemBuilder;
import de.bonprix.base.demo.dto.builder.ItemSizeBuilder;
import de.bonprix.base.demo.dto.builder.SeasonBuilder;
import de.bonprix.base.demo.dto.builder.SizeBuilder;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.module.style.wizard.mvp.AddStyleMvpWizardPresenter;
import de.bonprix.module.style.wizard.mvp.AddStyleMvpWizardView;
import de.bonprix.vaadin.eventbus.EventBus;

public class AddStyleWizardPresenterTest extends I18NAwareUnitTest {

    @InjectMocks
    private AddStyleMvpWizardPresenter presenter;

    @SuppressWarnings("rawtypes")
    @Mock
    private AddStyleMvpWizardView view;

    @Mock
    private EventBus localEventBus;

    @Mock
    private StyleService styleService;

    @SuppressWarnings("unused")
    private Style style;

    @BeforeMethod
    private void method() {
        final Set<Item> items = new HashSet<>();
        final Set<ItemSize> itemSizes = new HashSet<>();
        itemSizes.add(new ItemSizeBuilder().withSize(new SizeBuilder().withId(2L)
            .build())
            .build());
        items.add(new ItemBuilder().withItemSizes(itemSizes)
            .build());
        this.style = new StyleBuilder().withClient(new ClientBuilder().withId(1L)
            .build())
            .withCountry(new CountryBuilder().withId(1L)
                .build())
            .withItems(items)
            .withSeason(new SeasonBuilder().withId(1L)
                .build())
            .build();
    }

    @Test
    public void init() {
        MockitoAnnotations.initMocks(this);
        // Mockito.verify(this.localEventBus)
        // .addHandler(this.presenter);
    }

    @Test
    public void saveStyle() {
        // Mockito.when(this.styleService.saveStyle(this.style))
        // .thenReturn(this.style);
        // this.presenter.saveStyle(this.style);
        // Mockito.verify(this.styleService)
        // .saveStyle(this.style);
    }

}
