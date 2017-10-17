package de.bonprix.base.demo.jparepository;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.bonprix.base.demo.ComplexFilterUtils;
import de.bonprix.base.demo.model.Application;
import de.bonprix.model.CollectionPropertyFilter;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.StringPropertyFilter;

/**
 * @author Ivan Slavchev
 */
@Component
@Transactional
public class ApplicationFilterRepository {

    @Resource
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Application> findByFilter(final ComplexFilter complexFilter) {

        return createCriteria(complexFilter).list();
    }

    private Criteria createCriteria(final ComplexFilter complexFilter) {

        final Session session = (Session) this.entityManager.getDelegate();

        final Criteria criteriaApplication = session.createCriteria(Application.class);
        criteriaApplication.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        final Criterion resultCriterion = ComplexFilterUtils.translateToCriterion(complexFilter.getBaseJunctionFilter(), propertyFilter -> {

            if (propertyFilter instanceof StringPropertyFilter) {
                final StringPropertyFilter stringPropertyFilter = (StringPropertyFilter) propertyFilter;
                if (Objects.equals(stringPropertyFilter.getProperty(), de.bonprix.base.demo.dto.Application.NAME)) {
                    return ComplexFilterUtils.getHibernateCriterion("name", stringPropertyFilter);
                }

                if (Objects.equals(stringPropertyFilter.getProperty(), de.bonprix.base.demo.dto.Application.APPLICATION_TYPE_NAME_KEY)) {
                    criteriaApplication.createAlias("applicationType", "at"); // Add alias to handle the nested property
                    return ComplexFilterUtils.getHibernateCriterion("at.nameKey", stringPropertyFilter);
                }
            }

            if (propertyFilter instanceof CollectionPropertyFilter) {
                final CollectionPropertyFilter collectionFilter = (CollectionPropertyFilter) propertyFilter;
                if (Objects.equals(collectionFilter.getProperty(), de.bonprix.base.demo.dto.Application.ID)) {
                    return ComplexFilterUtils.getHibernateCriterion("id", collectionFilter);
                }
            }

            throw new UnsupportedOperationException();

        });

        criteriaApplication.add(resultCriterion);
        return criteriaApplication;
    }
}
