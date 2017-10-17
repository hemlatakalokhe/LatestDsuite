package de.bonprix.base.demo.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
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
       name = "style",
       schema = "DS_GLOBAL")
@NamedEntityGraph(
                  name = "graph.Style.items",
                  attributeNodes = @NamedAttributeNode(
                                                       value = "items",
                                                       subgraph = "graph.itemSizes"),
                  subgraphs = @NamedSubgraph(
                                             name = "graph.itemSizes",
                                             attributeNodes = @NamedAttributeNode("itemSizes")))
public class StyleEntity {

    @Id
    @GeneratedValue(
                    generator = "SEQ_MD_APPLICATION",
                    strategy = GenerationType.SEQUENCE)
    @Column(
            name = "style_id")
    private Long id;

    @Column(
            name = "style_no")
    private String styleNo;

    @Column(
            name = "style_desc")
    private String desc;

    @Column(
            name = "style_date")
    private Date date;

    @ManyToOne
    @JoinColumn(
                name = "season_id")
    private SeasonEntity season;

    @ManyToOne
    @JoinColumn(
                name = "country_id")
    private CountryEntity country;

    @OneToMany(
               mappedBy = "style",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.EAGER)
    private Set<ItemEntity> items;

    @ManyToOne
    @JoinColumn(
                name = "client_id")
    private ClientEntity client;

    public StyleEntity() {

    }

    public StyleEntity(final Long id, final String styleNo) {
        this.setId(id);
        this.styleNo = styleNo;
    }

    public ClientEntity getClient() {
        return this.client;
    }

    public void setClient(final ClientEntity client) {
        this.client = client;
    }

    public Set<ItemEntity> getItems() {
        return this.items;
    }

    public void setItems(final Set<ItemEntity> items) {
        this.items = items;
    }

    public String getStyleNo() {
        return this.styleNo;
    }

    public void setStyleNo(final String styleNo) {
        this.styleNo = styleNo;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public SeasonEntity getSeason() {
        return this.season;
    }

    public void setSeason(final SeasonEntity seasonId) {
        this.season = seasonId;
    }

    public CountryEntity getCountry() {
        return this.country;
    }

    public void setCountry(final CountryEntity countryid) {
        this.country = countryid;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.styleNo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
