package de.bonprix.base.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name="season",schema = "DS_GLOBAL")
public class SeasonEntity
{
    @Id
    @Column(name="season_id")
    private Long id;

    @Column(name="season_name")
    private String name;

    @Column(name="season_description")
    private String description;

    public Long getId() {
        return this.id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return  this.description;
    }


}
