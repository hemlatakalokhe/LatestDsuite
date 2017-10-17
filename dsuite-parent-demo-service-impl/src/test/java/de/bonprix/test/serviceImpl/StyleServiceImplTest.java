package de.bonprix.test.serviceImpl;
/**
 * @author h.kalokhe

 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
import de.bonprix.base.demo.jparepository.SizeRepository;
import de.bonprix.base.demo.jparepository.StyleRepository;
import de.bonprix.base.demo.model.ItemEntity;
import de.bonprix.base.demo.model.ItemSizeEntity;
import de.bonprix.base.demo.model.StyleEntity;
import de.bonprix.base.demo.model.builder.ClientEntityBuilder;
import de.bonprix.base.demo.model.builder.CountryEntityBuilder;
import de.bonprix.base.demo.model.builder.ItemEntityBuilder;
import de.bonprix.base.demo.model.builder.ItemSizeEntityBuilder;
import de.bonprix.base.demo.model.builder.SeasonEntityBuilder;
import de.bonprix.base.demo.model.builder.SizeEntityBuilder;
import de.bonprix.base.demo.model.builder.StyleEntityBuilder;
import de.bonprix.base.demo.service.StyleServiceImpl;

public class StyleServiceImplTest {

    @Mock
    private StyleRepository styleRepository;

    @Mock
    private SizeRepository sizeRepository;

    @InjectMocks
    private StyleServiceImpl styleServiceImpl;

    private StyleEntity styleEntity;

    private Style style;

    private Style style1;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        final Set<ItemEntity> itemEntities = new HashSet<>();
        final Set<ItemSizeEntity> itemSizeEntities = new HashSet<>();
        itemSizeEntities.add(new ItemSizeEntityBuilder().withItemsizeId(1L)
            .withSize(new SizeEntityBuilder().withSizeId(1L)
                .build())
            .build());
        itemEntities.add(new ItemEntityBuilder().withId(1L)
            .withStyle(new StyleEntityBuilder().withId(1L)
                .build())
            .withItemSizes(itemSizeEntities)
            .build());
        this.styleEntity = new StyleEntityBuilder().withId(1L)
            .withClient(new ClientEntityBuilder().withId(1L)
                .build())
            .withCountry(new CountryEntityBuilder().withId(1L)
                .build())
            .withItems(itemEntities)
            .withSeason(new SeasonEntityBuilder().withId(1L)
                .build())
            .build();

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

        this.style1 = new StyleBuilder().withId(1L)
            .withClient(new ClientBuilder().withId(1L)
                .build())
            .withCountry(new CountryBuilder().withId(1L)
                .build())
            .withItems(items)
            .withSeason(new SeasonBuilder().withId(1L)
                .build())
            .build();
    }

    @Test
    public void findAllStyles() {
        final List<StyleEntity> styleEntities = new ArrayList<>();
        styleEntities.add(this.styleEntity);
        Mockito.when(this.styleRepository.findAll())
            .thenReturn(styleEntities);
        final List<Style> styles = this.styleServiceImpl.findAll();
        Mockito.verify(this.styleRepository)
            .findAll();
        Assert.assertEquals(styleEntities.size(), styles.size());
    }

    @Test
    public void saveStyleTest() {
        Mockito.when(this.styleRepository.saveAndFlush(Mockito.any(StyleEntity.class)))
            .thenReturn(this.styleEntity);
        Mockito.when(this.sizeRepository.findOne(Mockito.anyLong()))
            .thenReturn(new SizeEntityBuilder().withSizeId(1L)
                .build());
        final Style styles = this.styleServiceImpl.saveStyle(this.style);
        Mockito.verify(this.styleRepository)
            .saveAndFlush(Mockito.any(StyleEntity.class));
        Assert.assertEquals(styles.getId(), this.styleEntity.getId());
    }

    @Test(
        expectedExceptions = NullPointerException.class)
    public void saveStyleWithIdTest() {
        this.styleServiceImpl.saveStyle(this.style1);
    }

    @Test
    public void findOne() {
        Mockito.when(this.styleRepository.findOne(Mockito.anyLong()))
            .thenReturn(this.styleEntity);
        final Style style = this.styleServiceImpl.findById(1L);
        Mockito.verify(this.styleRepository)
            .findOne(Mockito.anyLong());
        Assert.assertEquals(this.styleEntity.getId(), style.getId());
    }

    @Test
    public void delete() {
        Mockito.doNothing()
            .when(this.styleRepository)
            .delete(Mockito.anyLong());
        this.styleServiceImpl.deleteById(this.style.getId());
        Mockito.verify(this.styleRepository)
            .delete(Mockito.anyLong());
    }

    @Test
    public void updateStyle() {
        Mockito.when(this.styleRepository.findOne(Mockito.anyLong()))
            .thenReturn(this.styleEntity);
        this.styleEntity.setDesc("Jeans");
        Mockito.when(this.styleRepository.saveAndFlush(Mockito.any(StyleEntity.class)))
            .thenReturn(this.styleEntity);
        final Style style = this.styleServiceImpl.findById(2L);
        final Style styl1 = this.styleServiceImpl.saveStyle(style);
        Mockito.verify(this.styleRepository)
            .findOne(Mockito.anyLong());
        Mockito.verify(this.styleRepository)
            .saveAndFlush(Mockito.any(StyleEntity.class));
        Assert.assertEquals(this.styleEntity.getId(), styl1.getId());
    }

    @Test
    private void filter() {
        final Map<String, List<String>> map = new HashMap<>();
        map.put("a", Arrays.asList("1", "2", "3", "4"));
        map.put("b", Arrays.asList("6", "7", "4", "5"));
        map.entrySet()
            .stream()
            .filter(no -> no.toString()
                .startsWith("0"))
            .forEach(System.out::println);
        ;
    }

}
