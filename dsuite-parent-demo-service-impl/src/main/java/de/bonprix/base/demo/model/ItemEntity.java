package de.bonprix.base.demo.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    name = "item",
    schema = "DS_GLOBAL")
public class ItemEntity {

    @Id
    @GeneratedValue(
        generator = "SEQ_MD_APPLICATION",
        strategy = GenerationType.SEQUENCE)
    @Column(
        name = "item_id")
    private Long itemId;

    @Column(
        name = "item_color")
    private String color;

    @Column(
        name = "item_number")
    private String itemNo;

    @ManyToOne
    @JoinColumn(
        name = "style_id")
    private StyleEntity style;

    @OneToMany(
        mappedBy = "item",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private Set<ItemSizeEntity> itemSizes;

    public Set<ItemSizeEntity> getItemSizes() {
        return this.itemSizes;
    }

    public void setItemSizes(final Set<ItemSizeEntity> itemSizes) {
        this.itemSizes = itemSizes;
    }

    public Long getId() {
        return this.itemId;
    }

    public void setId(final Long itemId) {
        this.itemId = itemId;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getItemNo() {
        return this.itemNo;
    }

    public void setItemNo(final String itemNo) {
        this.itemNo = itemNo;
    }

    public StyleEntity getStyle() {
        return this.style;
    }

    public void setStyle(final StyleEntity style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return this.color;
    }

}
