package de.bonprix.service.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import de.bonprix.service.FetchTableDtoConverter;
import de.bonprix.service.I18NLanguageContainerEntity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage = "*.builder")
@Entity
@Table(name = "EXAMPLE", schema = "TEST")
@SequenceGenerator(schema = "TEST", name = "SEQ_EXAMPLE", sequenceName = "TEST.SEQ_EXAMPLE", allocationSize = 1)
public class ExampleLanguageContainerEntity implements
I18NLanguageContainerEntity<ExampleLanguageContainerEntity, ExampleLanguageElementEntity, ExampleLanguageContainer, ExampleLanguageElement>,
FetchTableDtoConverter<ExampleLanguageContainer, ExampleFetchOptions> {

    @Id
    @GeneratedValue(generator = "SEQ_MD_APPLICATION", strategy = GenerationType.SEQUENCE)
    @Column(name = "MD_APPLICATIONTYPE_ID")
    private Long id;

    @OneToMany(orphanRemoval = true, mappedBy = "applicationType", cascade = CascadeType.ALL)
    private Set<ExampleLanguageElementEntity> exampleLanguageElementEntities;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public Set<ExampleLanguageElementEntity> getExampleLanguageElementEntities() {
        return this.exampleLanguageElementEntities;
    }

    public void setExampleLanguageElementEntities(final Set<ExampleLanguageElementEntity> exampleLanguageElementEntities) {
        this.exampleLanguageElementEntities = exampleLanguageElementEntities;
    }

    @Override
    public Set<ExampleLanguageElementEntity> getLanguageElementEntities() {
        return getExampleLanguageElementEntities();
    }

    @Override
    public void setLanguageElementEntities(final Set<ExampleLanguageElementEntity> languageElementEntities) {
        setExampleLanguageElementEntities(languageElementEntities);
    }

    @Override
    public void updateDto(final ExampleLanguageContainer containerDto) {

    }

    @Override
    public void updateEntity(final ExampleLanguageContainer containerDto) {

    }

    @Override
    public void updateDto(final ExampleLanguageContainer dto, final ExampleFetchOptions fetchOptions) {
    }

}
