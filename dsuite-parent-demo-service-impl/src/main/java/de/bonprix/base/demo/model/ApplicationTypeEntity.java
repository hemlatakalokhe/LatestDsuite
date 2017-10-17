package de.bonprix.base.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "MD_APPLICATIONTYPE",
schema = "DS_GLOBAL")
@SequenceGenerator(schema = "DS_GLOBAL",
name = "SEQ_MD_APPLICATION",
sequenceName = "DS_GLOBAL.SEQ_MD_APPLICATION",
allocationSize = 1)
public class ApplicationTypeEntity {

    @Id
    @GeneratedValue(generator = "SEQ_MD_APPLICATION",
    strategy = GenerationType.SEQUENCE)
    @Column(name = "MD_APPLICATIONTYPE_ID")
    private Long id;

    @Column(name = "MD_APPLICATIONTYPE_NAME_KEY")
    private String nameKey;

    @Version
    @Column(name = "OPTLOCK",
    nullable = false,
    columnDefinition = "Number(18) default '0'")
    private Long optlock;

    @OneToMany(mappedBy = "applicationType")
    private Set<Application> applications;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNameKey() {
        return this.nameKey;
    }

    public void setNameKey(final String nameKey) {
        this.nameKey = nameKey;
    }

    public Long getOptlock() {
        return this.optlock;
    }

    public void setOptlock(final Long optlock) {
        this.optlock = optlock;
    }

    public Set<Application> getApplications() {
        return this.applications;
    }

    public void setApplications(final Set<Application> applications) {
        this.applications = applications;
    }

}
