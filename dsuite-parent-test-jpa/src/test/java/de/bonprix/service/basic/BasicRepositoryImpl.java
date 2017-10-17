package de.bonprix.service.basic;

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
public class BasicRepositoryImpl implements BasicRepositoryCustom {

	@Resource
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<BasicEntity> findAll(BasicFilter filter) {
		return createCriteria(filter).setFirstResult(filter.getPage() * filter.getPageSize())
			.setMaxResults(filter.getPageSize())
			.list();
	}

	@Override
	public BasicEntity findOne(BasicFilter filter) {
		BasicEntity applicationTypeEntity = (BasicEntity) createCriteria(filter).uniqueResult();
		if (applicationTypeEntity == null) {
			throw new NotFoundException();
		}
		return applicationTypeEntity;
	}

	@Override
	public int count(BasicFilter filter) {
		return Math.toIntExact((Long) createCriteria(filter).setProjection(Projections.rowCount())
			.uniqueResult());
	}

	private Criteria createCriteria(BasicFilter filter) {
		Session session = (Session) this.entityManager.getDelegate();
		Criteria criteriaApplicationType = session.createCriteria(BasicEntity.class);
		criteriaApplicationType.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteriaApplicationType.setFetchMode("applicationTypeLanguages", FetchMode.JOIN);

		if (!filter.getBasicIds()
			.isEmpty()) {
			criteriaApplicationType.add(Restrictions.in("id", filter.getBasicIds()));
		}

		return criteriaApplicationType;
	}

}