package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
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
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.base.demo.jparepository.ItemRepository;
import de.bonprix.base.demo.jparepository.SizeRepository;
import de.bonprix.base.demo.jparepository.StyleRepository;
import de.bonprix.base.demo.model.ClientEntity;
import de.bonprix.base.demo.model.CountryEntity;
import de.bonprix.base.demo.model.ItemEntity;
import de.bonprix.base.demo.model.ItemSizeEntity;
import de.bonprix.base.demo.model.SeasonEntity;
import de.bonprix.base.demo.model.StyleEntity;
import de.bonprix.base.demo.model.builder.ClientEntityBuilder;
import de.bonprix.base.demo.model.builder.CountryEntityBuilder;
import de.bonprix.base.demo.model.builder.ItemEntityBuilder;
import de.bonprix.base.demo.model.builder.ItemSizeEntityBuilder;
import de.bonprix.base.demo.model.builder.SeasonEntityBuilder;
import de.bonprix.base.demo.model.builder.StyleEntityBuilder;

@RestService
@Transactional
public class StyleServiceImpl implements StyleService {

    @Resource
    private StyleRepository styleRepository;

    @Resource
    private SizeRepository sizeRepository;

    @Resource
    private EntityManager entityManager;

    @Resource
    private ItemRepository itemRepository;

    @Override
    public List<Style> findAll() {
        final List<Style> styles = new ArrayList<>();
        for (final StyleEntity style : this.styleRepository.findAll()) {
            styles.add(this.convertToDto(style));
        }
        return styles;
    }

    public Style convertToDto(final StyleEntity style) {
        final Country countryEntity = new CountryBuilder().withId(style.getCountry()
            .getId())
            .withIsoCode(style.getCountry()
                .getIsoCode())
            .withName(style.getCountry()
                .getName())
            .build();
        final Set<ItemEntity> items = style.getItems();
        final Set<Item> item = new HashSet<>();
        final Set<ItemSize> itemSizeEntities = new HashSet<>();

        for (final ItemEntity itemEntity : items) {
            for (final ItemSizeEntity itemSizeEntity : itemEntity.getItemSizes()) {
                final ItemSize itemSizeDto = new ItemSizeBuilder().withId(itemSizeEntity.getItemsizeId())
                    .withQuantity(itemSizeEntity.getQuantity())
                    .build();
                itemSizeEntities.add(itemSizeDto);
            }
            final Item itemDto = new ItemBuilder().withId(itemEntity.getId())
                .withColor(itemEntity.getColor())
                .withItemSizes(itemSizeEntities)
                .withItemNo(itemEntity.getItemNo())
                .build();
            item.add(itemDto);
        }
        return new StyleBuilder().withId(style.getId())
            .withStyleNo(style.getStyleNo())
            .withDate(style.getDate())
            .withClient(new ClientBuilder().withId(style.getClient()
                .getId())
                .withClientName(style.getClient()
                    .getClientName())
                .build())
            .withDesc(style.getDesc())
            .withItems(item)
            .withSeason(new SeasonBuilder().withId(style.getSeason()
                .getId())
                .withDescription(style.getSeason()
                    .getDescription())
                .withName(style.getSeason()
                    .getName())
                .build())
            .withCountry(countryEntity)
            .build();
    }

    @Override
    public List<Style> findByFilter(final StyleOverViewFilter complexFilter) {
        final String styleNo = complexFilter.getStyleNo();
        CountryEntity country = null;
        if (complexFilter.getCountry() != null) {
            country = new CountryEntityBuilder().withId(complexFilter.getCountry()
                .getId())
                .withIsoCode(complexFilter.getCountry()
                    .getIsoCode())
                .withName(complexFilter.getCountry()
                    .getName())
                .build();
        }

        final StringBuffer sb = new StringBuffer();
        Query query = null;
        sb.append("SELECT s  FROM StyleEntity s WHERE  1 = 1 ");

        if ((styleNo != null) && !styleNo.equals("")) {
            sb.append(" AND s.styleNo=:styleNumber");
        }
        if (country != null) {
            sb.append(" AND s.country=:country  ");
        }
        query = this.entityManager.createQuery(sb.toString());
        if ((styleNo != null) && !styleNo.equals("")) {
            query.setParameter("styleNumber", styleNo);
        }
        if (country != null) {
            query.setParameter("country", country);
        }

        @SuppressWarnings("unchecked")
        final List<StyleEntity> styleEntities = query.getResultList();
        final List<Style> styles = new ArrayList<>();
        for (final StyleEntity style : styleEntities) {
            final Country countryDto = new CountryBuilder().withId(style.getCountry()
                .getId())
                .withIsoCode(style.getCountry()
                    .getIsoCode())
                .withName(style.getCountry()
                    .getName())
                .build();
            final Style styleDto = new StyleBuilder().withId(style.getId())
                .withCountry(countryDto)
                .withDate(style.getDate())
                .withDesc(style.getDesc())
                .withStyleNo(style.getStyleNo())
                .build();
            styles.add(styleDto);
        }
        return styles;
    }

    @Override
    public Style findById(final Long id) {
        final StyleEntity style = this.styleRepository.findOne(id);
        return this.convertToDto(style);
    }

    @Override
    public void deleteById(final Long id) {
        this.styleRepository.delete(id);
    }

    @Override
    public Style saveStyle(final Style style) {
        final StyleEntity styleEntity = this.styleRepository.saveAndFlush(this.convertToModel(style));
        return this.convertToDto(styleEntity);
    }

    public StyleEntity convertToModel(final Style style) {
        final CountryEntity countryEntity = new CountryEntityBuilder().withId(style.getCountry()
            .getId())
            .withIsoCode(style.getCountry()
                .getIsoCode())
            .withName(style.getCountry()
                .getName())
            .build();
        final ClientEntity clientEntity = new ClientEntityBuilder().withId(style.getClient()
            .getId())
            .withClientName(style.getClient()
                .getClientName())
            .build();
        final SeasonEntity seasonEntity = new SeasonEntityBuilder().withId(style.getSeason()
            .getId())
            .withDescription(style.getSeason()
                .getDescription())
            .withName(style.getSeason()
                .getName())
            .build();
        final Set<ItemSizeEntity> itemSizeEntities = new HashSet<>();
        final Set<ItemEntity> itemEntities = new HashSet<>();

        final StyleEntity styleEntityBuilder = new StyleEntityBuilder().withId(style.getId())
            .withDesc(style.getDesc())
            .withStyleNo(style.getStyleNo())
            .withClient(clientEntity)
            .withSeason(seasonEntity)
            .withCountry(countryEntity)
            .withItems(itemEntities)
            .withDate(new Date())
            .build();

        final ItemEntity itemEntity = new ItemEntityBuilder().withItemNo("H118")
            .withStyle(styleEntityBuilder)
            .withColor("Orange")
            .withItemSizes(itemSizeEntities)
            .build();
        final ItemSizeEntity itemSizeEntity = new ItemSizeEntityBuilder().withQuantity(2)
            .withSize(this.sizeRepository.findOne(2l))
            .withItem(itemEntity)
            .build();
        itemEntities.add(itemEntity);
        itemSizeEntities.add(itemSizeEntity);

        return styleEntityBuilder;
    }

}
