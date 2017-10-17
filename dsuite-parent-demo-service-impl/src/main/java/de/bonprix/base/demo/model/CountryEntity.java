package de.bonprix.base.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(
    intoPackage = "*.builder")

@Entity
@Table(
    name = "countries",
    schema = "DS_GLOBAL")
public class CountryEntity {

    @Id
    @Column(
        name = "country_id")
    private Long id;

    @Column(
        name = "country_iso_code")
    private String isoCode;

    @Column(
        name = "country_name")
    private String name;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public void setIsoCode(final String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
