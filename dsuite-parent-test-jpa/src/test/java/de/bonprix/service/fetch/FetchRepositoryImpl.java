package de.bonprix.service.fetch;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FetchRepositoryImpl implements FetchRepositoryCustom {

	@Resource
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<FetchEntity> findAll(FetchFilter filter) {
		return findAll(filter, new FetchOptions());
	}

	@Override
	public List<FetchEntity> findAll(FetchFilter filter, FetchOptions fetchOptions) {
		return createCriteria(filter, fetchOptions).setFirstResult(filter.getPage() * filter.getPageSize())
			.setMaxResults(filter.getPageSize())
			.list();
	}

	@Override
	public FetchEntity findOne(FetchFilter filter) {
		return findOne(filter, new FetchOptions());
	}

	@Override
	public FetchEntity findOne(FetchFilter filter, FetchOptions fetchOptions) {
		FetchEntity applicationTypeEntity = (FetchEntity) createCriteria(filter, fetchOptions).uniqueResult();
		if (applicationTypeEntity == null) {
			throw new NotFoundException();
		}
		return applicationTypeEntity;
	}

	@Override
	public int count(FetchFilter filter) {
		return Math.toIntExact((Long) createCriteria(filter, new FetchOptions()).setProjection(Projections.rowCount())
			.uniqueResult());
	}

	private Criteria createCriteria(FetchFilter filter, FetchOptions fetchOptions) {
		Session session = (Session) this.entityManager.getDelegate();
		Criteria criteriaApplicationType = session.createCriteria(FetchEntity.class);
		criteriaApplicationType.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteriaApplicationType.setFetchMode("applicationTypeLanguages", FetchMode.JOIN);

		if (!filter.getFetchIds()
			.isEmpty()) {
			criteriaApplicationType.add(Restrictions.in("id", filter.getFetchIds()));
		}

		return criteriaApplicationType;
	}

}