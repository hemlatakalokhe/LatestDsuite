package de.bonprix.module.style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.ui.Tree;

import de.bonprix.I18NAwareUnitTest;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Item;
import de.bonprix.base.demo.dto.ItemSize;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.StyleOverViewFilter;
import de.bonprix.base.demo.dto.builder.ClientBuilder;
import de.bonprix.base.demo.dto.builder.CountryBuilder;
import de.bonprix.base.demo.dto.builder.ItemBuilder;
import de.bonprix.base.demo.dto.builder.ItemSizeBuilder;
import de.bonprix.base.demo.dto.builder.SeasonBuilder;
import de.bonprix.base.demo.dto.builder.SizeBuilder;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogPresenter;
import de.bonprix.vaadin.eventbus.EventBus;

public class StylePresenterTest extends I18NAwareUnitTest {

    @InjectMocks
    private StylePresenter stylePresenter;

    @Mock
    private StyleView styleView;

    @Mock
    private EventBus localEventBus;

    @Mock
    private StyleService styleService;

    @Mock
    private CountryService countryService;

    @Mock
    private ApplicationContext applicationContext;

    private List<Style> styles;

    private Style style;

    @BeforeMethod
    private void beforeMethod() {
        this.styles = new ArrayList<>();
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
    private void initialization() {
        MockitoAnnotations.initMocks(this);
        this.stylePresenter.initialize();
        Mockito.verify(this.localEventBus)
            .addHandler(this.stylePresenter);
    }

    @Test
    private void init() {
        this.styles.add(this.style);
        final List<Country> countries = Arrays.asList(new CountryBuilder().withId(1L)
            .build());
        Mockito.when(this.styleService.findAll())
            .thenReturn(this.styles);
        Mockito.when(this.countryService.findAll())
            .thenReturn(countries);
        this.stylePresenter.init();

        Mockito.verify(this.styleView)
            .setAllStyleBeans(this.styles, countries);
    }

    @Test
    private void openAddDialog() {
        final UpdateMvpDialogPresenter presenter = Mockito.mock(UpdateMvpDialogPresenter.class);
        Mockito.when(this.applicationContext.getBean(Matchers.any(Class.class)))
            .thenReturn(presenter);
        this.stylePresenter.openDialog(new Style(), Mode.ADD);
        Mockito.verify(presenter)
            .open();
    }

    @Test
    private void openEditDialog() {
        final UpdateMvpDialogPresenter presenter = Mockito.mock(UpdateMvpDialogPresenter.class);
        Mockito.when(this.applicationContext.getBean(Matchers.any(Class.class)))
            .thenReturn(presenter);
        this.stylePresenter.openDialog(new StyleBuilder().withId(1L)
            .build(), Mode.EDIT);
        Mockito.verify(presenter)
            .open();
    }

    @Test
    private void deleteStyle() {
        Mockito.doNothing()
            .when(this.styleService)
            .deleteById(Mockito.anyLong());
        final Style style = new StyleBuilder().withId(1L)
            .build();
        this.stylePresenter.deleteStyle(style);
        Mockito.verify(this.styleService)
            .deleteById(Mockito.anyLong());
    }

    @Test
    private void saveInlineEditedStyle() {
        this.styles.add(this.style);
        Mockito.when(this.styleService.findById(Mockito.anyLong()))
            .thenReturn(this.style);
        Mockito.when(this.styleService.saveStyle(this.style))
            .thenReturn(this.style);
        this.stylePresenter.saveInlineEditedStyle(this.styles);
        Mockito.verify(this.styleService)
            .findById(Mockito.anyLong());
        Mockito.verify(this.styleService)
            .saveStyle(this.style);
    }

    @Test
    private void filterStyle() {
        this.styles.add(this.style);
        Mockito.when(this.styleService.findByFilter(Mockito.any(StyleOverViewFilter.class)))
            .thenReturn(this.styles);
        this.stylePresenter.filterStyle("", new CountryBuilder().withId(1L)
            .build());
        Mockito.verify(this.styleView)
            .setAllFilteredStyleBeans(this.styles);
        Mockito.verify(this.styleService)
            .findByFilter(Mockito.any(StyleOverViewFilter.class));
    }

    @Test
    private void setTree() {
        final Tree tree = new Tree();
        this.styles.add(this.style);
        Mockito.when(this.styleService.findAll())
            .thenReturn(this.styles);
        this.stylePresenter.setTree(tree);
        Mockito.verify(this.styleView)
            .displayTree(tree);

    }
}
