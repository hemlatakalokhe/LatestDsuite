package de.bonprix.test.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.jparepository.SeasonRepository;
import de.bonprix.base.demo.jparepository.StyleRepository;
import de.bonprix.base.demo.model.SeasonEntity;
import de.bonprix.base.demo.model.builder.SeasonEntityBuilder;
import de.bonprix.base.demo.service.SeasonService;
import de.bonprix.base.demo.service.SeasonServiceImpl;
import de.bonprix.base.demo.service.StyleServiceImpl;

public class SeasonServiceImplTest {

    @Mock
    private SeasonService seasonService;

    @Mock
    private SeasonRepository seasonRepository;

    @InjectMocks
    private SeasonServiceImpl seasonServiceImpl;

    @InjectMocks
    private StyleServiceImpl styServiceImpl;

    @Mock
    private StyleRepository styleRepository;

    SeasonEntity seasonEntity;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.seasonEntity = new SeasonEntityBuilder().withId(1L)
            .build();
    }

    @Test
    public void findAll() {
        final List<SeasonEntity> seasons = new ArrayList<>();
        seasons.add(new SeasonEntityBuilder().withId(1L)
            .build());
        Mockito.when(this.seasonRepository.findAll())
            .thenReturn(seasons);
        final List<Season> seasons2 = this.seasonServiceImpl.findAll();
        Mockito.verify(this.seasonRepository)
            .findAll();
        Assert.assertEquals(seasons2.size(), seasons.size());
    }

}
