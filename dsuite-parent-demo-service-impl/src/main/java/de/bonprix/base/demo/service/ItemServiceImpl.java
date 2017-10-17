package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import de.bonprix.base.demo.dto.Item;
import de.bonprix.base.demo.dto.ItemSize;
import de.bonprix.base.demo.dto.builder.ItemBuilder;
import de.bonprix.base.demo.dto.builder.ItemSizeBuilder;
import de.bonprix.base.demo.jparepository.ItemRepository;
import de.bonprix.base.demo.jparepository.ItemSizeRepository;
import de.bonprix.base.demo.model.ItemEntity;
import de.bonprix.base.demo.model.ItemSizeEntity;
import de.bonprix.base.demo.model.builder.ItemEntityBuilder;
import de.bonprix.base.demo.model.builder.ItemSizeEntityBuilder;
import de.bonprix.base.demo.model.builder.SizeEntityBuilder;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.Paged;

public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemRepository itemRepository;

    @Resource
    private ItemSizeRepository itemSizeRepository;

    @Override
    public List<Item> findAll(final Paged pageable) {
        final List<Item> items = new ArrayList<>();
        for (final ItemEntity itemEntity : this.itemRepository.findAll()) {
            items.add(convertToDto(itemEntity));
        }
        return items;
    }

    @Override
    public List<Item> findByFilter(final ComplexFilter complexFilter) {
        return new ArrayList<>();
    }

    @Override
    public Item findById(final Long id) {
        return convertToDto(this.itemRepository.findOne(id));
    }

    @Override
    public void deleteById(final Long id) {
        //
    }

    @Override
    public Item saveItem(final Item item) {
        return convertToDto(this.itemRepository.saveAndFlush(convertToModel(item)));
    }

    public Item convertToDto(final ItemEntity itemEntity) {
        final Set<ItemSize> itemSizeEntities = new HashSet<>();
        for (final ItemSizeEntity itemSizeEntity : itemEntity.getItemSizes()) {
            final ItemSize itemSize = new ItemSizeBuilder().withId(itemSizeEntity.getItemsizeId())
                .withQuantity(itemSizeEntity.getQuantity())
                .build();
            itemSizeEntities.add(itemSize);
        }

        return new ItemBuilder().withId(itemEntity.getId())
            .withColor(itemEntity.getColor())
            .withItemNo(itemEntity.getItemNo())
            .withItemSizes(itemSizeEntities)
            .build();
    }

    public ItemEntity convertToModel(final Item item) {
        final Set<ItemSizeEntity> itemSizeEntities = new HashSet<>();
        for (final ItemSize itemSize : item.getItemSizes()) {
            final ItemSizeEntity itemSizeEntity = new ItemSizeEntityBuilder().withQuantity(itemSize.getQuantity())
                .withSize(new SizeEntityBuilder().withSizeId(1L)
                    .build())
                .build();
            itemSizeEntities.add(itemSizeEntity);
        }
        return new ItemEntityBuilder().withColor(item.getColor())
            .withItemNo(item.getItemNo())
            .withItemSizes(itemSizeEntities)
            .build();
    }
}
