package de.bonprix.service.fetchlanguage;

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
public class FetchLanguageRepositoryImpl implements FetchLanguageRepositoryCustom {

	@Resource
	private EntityManager entityManager;

	@Override
	public List<FetchLanguageEntity> findAll(FetchLanguageFilter filter) {
		return findAll(filter, new FetchLanguageFetchOptions());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FetchLanguageEntity> findAll(FetchLanguageFilter filter, FetchLanguageFetchOptions fetchOptions) {
		return createCriteria(filter, fetchOptions).setFirstResult(filter.getPage() * filter.getPageSize())
			.setMaxResults(filter.getPageSize())
			.list();
	}

	@Override
	public FetchLanguageEntity findOne(FetchLanguageFilter filter) {
		return findOne(filter, new FetchLanguageFetchOptions());
	}

	@Override
	public FetchLanguageEntity findOne(FetchLanguageFilter filter, FetchLanguageFetchOptions fetchOptions) {
		FetchLanguageEntity fetchLanguageEntity = (FetchLanguageEntity) createCriteria(filter, fetchOptions)
			.uniqueResult();
		if (fetchLanguageEntity == null) {
			throw new NotFoundException();
		}
		return fetchLanguageEntity;
	}

	@Override
	public int count(FetchLanguageFilter filter) {
		return Math.toIntExact((Long) createCriteria(filter, new FetchLanguageFetchOptions())
			.setProjection(Projections.rowCount())
			.uniqueResult());
	}

	private Criteria createCriteria(FetchLanguageFilter filter, FetchLanguageFetchOptions fetchOptions) {
		Session session = (Session) this.entityManager.getDelegate();
		Criteria criteriaFetchLanguage = session.createCriteria(FetchLanguageEntity.class);
		criteriaFetchLanguage.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteriaFetchLanguage.setFetchMode("fetchLanguageLanguages", FetchMode.JOIN);

		if (!filter.getFetchLanguageIds()
			.isEmpty()) {
			criteriaFetchLanguage.add(Restrictions.in("id", filter.getFetchLanguageIds()));
		}

		return criteriaFetchLanguage;
	}

}