package de.bonprix.base.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(
    intoPackage = "*.builder")
@Entity
@SequenceGenerator(
    schema = "DS_GLOBAL",
    name = "SEQ_MD_APPLICATION",
    sequenceName = "DS_GLOBAL.SEQ_MD_APPLICATION",
    allocationSize = 1)
@Table(
    name = "itemsizes",
    schema = "DS_GLOBAL")
public class ItemSizeEntity {

    @Id
    @GeneratedValue(
        generator = "SEQ_MD_APPLICATION",
        strategy = GenerationType.SEQUENCE)
    @Column(
        name = "itemsize_id")
    private Long itemsizeId;

    @ManyToOne
    @JoinColumn(
        name = "size_id")
    private SizeEntity size;

    @Column(
        name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(
        name = "item_id")
    private ItemEntity item;

    public ItemEntity getItem() {
        return this.item;
    }

    public void setItem(final ItemEntity item) {
        this.item = item;
    }

    public Long getItemsizeId() {
        return this.itemsizeId;
    }

    public void setItemsizeId(final Long itemsizeId) {
        this.itemsizeId = itemsizeId;
    }

    public SizeEntity getSize() {
        return this.size;
    }

    public void setSize(final SizeEntity size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.quantity.toString();
    }
}
