package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.builder.SeasonBuilder;
import de.bonprix.base.demo.jparepository.SeasonRepository;
import de.bonprix.base.demo.model.SeasonEntity;

@RestService
@Transactional
public class SeasonServiceImpl implements SeasonService {

    @Resource
    private SeasonRepository seasonRepository;

    @Override
    public List<Season> findAll() {
        final List<Season> seasons = new ArrayList<>();
        for (final SeasonEntity season : this.seasonRepository.findAll()) {
            seasons.add(convertToDto(season));
        }
        return seasons;
    }

    private Season convertToDto(final SeasonEntity season) {
        return new SeasonBuilder().withId(season.getId())
            .withDescription(season.getDescription())
            .withName(season.getName())
            .build();
    }

}
