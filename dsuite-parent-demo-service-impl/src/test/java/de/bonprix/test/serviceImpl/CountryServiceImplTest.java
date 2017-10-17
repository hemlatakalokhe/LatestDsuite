
package de.bonprix.test.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.jparepository.CountryRepository;
import de.bonprix.base.demo.model.CountryEntity;
import de.bonprix.base.demo.model.builder.CountryEntityBuilder;
import de.bonprix.base.demo.service.CountryServiceImpl;

/**
 * @author h.kalokhe
 *
 */
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryServiceImpl;

    private CountryEntity countryEntity;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.countryEntity = new CountryEntityBuilder().withId(1L)
            .build();
    }

    @Test
    public void findAllCountries() {
        final List<CountryEntity> countryEntities = Arrays.asList(this.countryEntity);
        Mockito.when(this.countryRepository.findAll())
            .thenReturn(countryEntities);
        final List<Country> countries = this.countryServiceImpl.findAll();
        Mockito.verify(this.countryRepository)
            .findAll();
        Assert.assertEquals(countries.size(), countryEntities.size());
    }

}
