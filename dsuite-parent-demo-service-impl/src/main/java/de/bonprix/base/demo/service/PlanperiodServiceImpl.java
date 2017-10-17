package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.builder.PlanperiodBuilder;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.Paged;

@RestService
@Transactional
public class PlanperiodServiceImpl implements PlanperiodService {

    @Override
    public List<Planperiod> findAll(final Paged pageable) {
        final List<Planperiod> planperiods = new ArrayList<>();
        planperiods.add(new PlanperiodBuilder().withId(1l)
            .withName("133")
            .build());
        planperiods.add(new PlanperiodBuilder().withId(2l)
            .withName("134")
            .build());
        planperiods.add(new PlanperiodBuilder().withId(3l)
            .withName("135")
            .build());
        planperiods.add(new PlanperiodBuilder().withId(4l)
            .withName("136")
            .build());
        planperiods.add(new PlanperiodBuilder().withId(5l)
            .withName("137")
            .build());

        return planperiods;
    }

    @Override
    public List<Planperiod> findByFilter(final ComplexFilter complexFilter) {
        return new ArrayList<>();
    }

    @Override
    public Planperiod findById(final Long id) {
        return new Planperiod();
    }

    @Override
    public void deleteById(final Long id) {
        //
    }

    @Override
    public Planperiod savePlanperiod(final Planperiod planperiod) {
        return new Planperiod();
    }

}
