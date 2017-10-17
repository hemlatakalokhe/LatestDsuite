package de.bonprix.base.demo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import de.bonprix.model.CollectionPropertyFilter;
import de.bonprix.model.Filter;
import de.bonprix.model.JunctionFilter;
import de.bonprix.model.PropertyFilter;
import de.bonprix.model.StringPropertyFilter;

/**
 * @author Ivan Slavchev
 */

public class ComplexFilterUtils {
    private static final String OPERATION = "Unsupported operation";

    public static Criterion getHibernateCriterion(final String entityPropertyName, final StringPropertyFilter propertyFilter) {

        switch (propertyFilter.getOperation()) {
            case EQUALS:
                return Restrictions.eq(entityPropertyName, propertyFilter.getValue());
            case CONTAINS:
                return Restrictions.ilike(entityPropertyName, propertyFilter.getValue(), MatchMode.ANYWHERE);
            case ENDS_WITH:
                return Restrictions.ilike(entityPropertyName, propertyFilter.getValue(), MatchMode.END);
            case STARTS_WITH:
                return Restrictions.ilike(entityPropertyName, propertyFilter.getValue(), MatchMode.START);
            default:
                throw new UnsupportedOperationException(ComplexFilterUtils.OPERATION);
        }

    }

    public static Criterion getHibernateCriterion(final String entityPropertyName, final CollectionPropertyFilter propertyFilter) {

        if (propertyFilter.getValue()
            .isEmpty()) {
            return Restrictions.disjunction();
        }

        switch (propertyFilter.getOperation()) {
            case IN:
                return Restrictions.in(entityPropertyName, propertyFilter.getValue());
            case NOT_IN:
                return Restrictions.not(Restrictions.in(entityPropertyName, propertyFilter.getValue()));
            default:
                throw new UnsupportedOperationException(ComplexFilterUtils.OPERATION);
        }

    }

    @SuppressWarnings("rawtypes")
    public static Criterion translateToCriterion(final Filter filter, final PropertyFilterHandler propertyFilterHandler) {

        if (filter instanceof JunctionFilter) {
            final JunctionFilter junctionFilter = (JunctionFilter) filter;

            final List<Criterion> criterions = new ArrayList<>();

            for (final Filter nestedFilter : junctionFilter.getValue()) {
                final Criterion criterion = ComplexFilterUtils.translateToCriterion(nestedFilter, propertyFilterHandler);
                criterions.add(criterion);
            }

            switch (junctionFilter.getOperation()) {
                case AND:
                    return Restrictions.and(criterions.toArray(new Criterion[criterions.size()]));
                case OR:
                    return Restrictions.or(criterions.toArray(new Criterion[criterions.size()]));
                default:
                    throw new UnsupportedOperationException(ComplexFilterUtils.OPERATION);
            }
        }
        else if (filter instanceof PropertyFilter) {
            return propertyFilterHandler.translatePropertyFilter((PropertyFilter) filter);
        }
        else {
            throw new UnsupportedOperationException("Unsupported filter type");
        }
    }

    @FunctionalInterface
    public interface PropertyFilterHandler {

        Criterion translatePropertyFilter(@SuppressWarnings("rawtypes") PropertyFilter propertyFilter);

    }

}
