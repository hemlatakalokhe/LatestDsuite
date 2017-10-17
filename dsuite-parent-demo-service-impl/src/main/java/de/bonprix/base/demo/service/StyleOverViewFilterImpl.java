package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.StyleOverViewFilter;
import de.bonprix.model.Paged;

@RestService
@Transactional
public class StyleOverViewFilterImpl implements StyleOverViewFilterService {

    @Resource
    private StyleOverViewFilterService service;

    @Override
    public List<StyleOverViewFilter> findAll(final Paged pageable) {
        return new ArrayList<>();
    }

    @Override
    public StyleOverViewFilter findById(final Long id) {
        return new StyleOverViewFilter();
    }

    @Override
    public void deleteById(final Long id) {
        //
    }

    @Override
    public StyleOverViewFilter saveStyleOverViewFilter(final StyleOverViewFilter styleOverViewFilter) {
        return new StyleOverViewFilter();
    }

    @Override
    public List<StyleOverViewFilter> findByFilter(final StyleOverViewFilter complexFilter) {
        StyleOverViewFilter styleFilter = null;
        if (complexFilter.getCountry() != null) {
            styleFilter = new StyleOverViewFilter();
        }

        return this.service.findByFilter(styleFilter);
    }

}
