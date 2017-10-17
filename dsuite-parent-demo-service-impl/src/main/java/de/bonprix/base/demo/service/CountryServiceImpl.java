package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.builder.CountryBuilder;
import de.bonprix.base.demo.jparepository.CountryRepository;
import de.bonprix.base.demo.model.CountryEntity;

@RestService
@Transactional
public class CountryServiceImpl implements CountryService {

    @Resource
    private CountryRepository countryRepo;

    @Override
    public List<Country> findAll() {
        final List<Country> countries = new ArrayList<>();
        for (final CountryEntity country : this.countryRepo.findAll()) {
            countries.add(convertToDto(country));
        }
        return countries;
    }

    private Country convertToDto(final CountryEntity country) {

        return new CountryBuilder().withId(country.getId())
            .withIsoCode(country.getIsoCode())
            .withName(country.getName())
            .build();
    }

}
