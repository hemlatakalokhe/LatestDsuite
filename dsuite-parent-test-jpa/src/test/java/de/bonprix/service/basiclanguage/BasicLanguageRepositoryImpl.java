package de.bonprix.service.basiclanguage;

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
public class BasicLanguageRepositoryImpl implements BasicLanguageRepositoryCustom {

	@Resource
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<BasicLanguageEntity> findAll(BasicLanguageFilter filter) {
		return createCriteria(filter).setFirstResult(filter.getPage() * filter.getPageSize())
			.setMaxResults(filter.getPageSize())
			.list();
	}

	@Override
	public BasicLanguageEntity findOne(BasicLanguageFilter filter) {
		BasicLanguageEntity basicLanguageEntity = (BasicLanguageEntity) createCriteria(filter).uniqueResult();
		if (basicLanguageEntity == null) {
			throw new NotFoundException();
		}
		return basicLanguageEntity;
	}

	@Override
	public int count(BasicLanguageFilter filter) {
		return Math.toIntExact((Long) createCriteria(filter).setProjection(Projections.rowCount())
			.uniqueResult());
	}

	private Criteria createCriteria(BasicLanguageFilter filter) {
		Session session = (Session) this.entityManager.getDelegate();
		Criteria criteriaBasicLanguage = session.createCriteria(BasicLanguageEntity.class);
		criteriaBasicLanguage.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteriaBasicLanguage.setFetchMode("basicLanguageLanguages", FetchMode.JOIN);

		if (!filter.getBasicLanguageIds()
			.isEmpty()) {
			criteriaBasicLanguage.add(Restrictions.in("id", filter.getBasicLanguageIds()));
		}

		return criteriaBasicLanguage;
	}

}