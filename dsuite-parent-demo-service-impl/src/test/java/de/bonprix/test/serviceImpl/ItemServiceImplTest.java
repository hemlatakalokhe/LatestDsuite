package de.bonprix.test.serviceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import de.bonprix.base.demo.dto.builder.ItemBuilder;
import de.bonprix.base.demo.dto.builder.ItemSizeBuilder;
import de.bonprix.base.demo.dto.builder.SizeBuilder;
import de.bonprix.base.demo.jparepository.ItemRepository;
import de.bonprix.base.demo.model.ItemEntity;
import de.bonprix.base.demo.model.ItemSizeEntity;
import de.bonprix.base.demo.model.builder.ItemEntityBuilder;
import de.bonprix.base.demo.model.builder.ItemSizeEntityBuilder;
import de.bonprix.base.demo.model.builder.SizeEntityBuilder;
import de.bonprix.base.demo.service.ItemServiceImpl;
import de.bonprix.model.Paged;

public class ItemServiceImplTest
{
    @InjectMocks
    private ItemServiceImpl itemServiceImpl;

    @Mock
    private ItemRepository itemRepository;

    private ItemEntity itemEntity;

    private Item item;

    @BeforeMethod
    public void init(){
        MockitoAnnotations.initMocks(this);
        final Set<ItemSizeEntity> itemSizeEntities=new HashSet<>();
        final ItemSizeEntity itemSizeEntity=new ItemSizeEntityBuilder().withItemsizeId(1L).withSize(new SizeEntityBuilder().withSizeId(1L).build()).build();
        itemSizeEntities.add(itemSizeEntity);
        this.itemEntity=new ItemEntityBuilder().withId(1L).withItemSizes(itemSizeEntities).build();
        final Set<ItemSize> itemSizes=new HashSet<>();
        itemSizes.add(new ItemSizeBuilder().withSize(new SizeBuilder().withId(2L).build()).build());
        this.item=new ItemBuilder().withItemSizes(itemSizes).build();
    }

    @Test
    public void findAll(){
        final List<ItemEntity> itemEntities =Arrays.asList(this.itemEntity);
        Mockito.when(this.itemRepository.findAll()).thenReturn(itemEntities);
        final List<Item> items = this.itemServiceImpl.findAll(new Paged(0, 100));
        Mockito.verify(this.itemRepository).findAll();
        Assert.assertEquals(itemEntities.size(), items.size());
    }

    @Test
    public void findOne(){
        Mockito.when(this.itemRepository.findOne(Mockito.anyLong())).thenReturn(this.itemEntity);
        final Item item = this.itemServiceImpl.findById(1L);
        Mockito.verify(this.itemRepository).findOne(Mockito.anyLong());
        Assert.assertEquals(this.itemEntity.getId(), item.getId());
    }

    @Test
    public void saveItem(){
        Mockito.when(this.itemRepository.saveAndFlush(Mockito.any(ItemEntity.class))).thenReturn(this.itemEntity);
        final Item items = this.itemServiceImpl.saveItem(this.item);
        Mockito.verify(this.itemRepository).saveAndFlush(Mockito.any(ItemEntity.class));
        Assert.assertEquals(this.itemEntity.getId(), items.getId());
    }
}
