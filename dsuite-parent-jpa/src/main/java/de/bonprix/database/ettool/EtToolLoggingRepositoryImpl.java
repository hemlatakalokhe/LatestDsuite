package de.bonprix.database.ettool;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.bonprix.security.PrincipalSecurityContext;

@Component
public class EtToolLoggingRepositoryImpl implements EtToolLoggingRepository {

	@PersistenceContext
	private EntityManager em;

	@Value("${application.id}")
	private Long applicationId;

	@Value("${database.ettool.logging.baseschema}")
	private String loggingBaseSchema;

	protected String getLoggingBaseSchema() {
		return this.loggingBaseSchema;
	}

	@Override
	public void init() {
		setPrincipalIdAndApplicationId(	PrincipalSecurityContext.getRootPrincipal()
																.getId(),
										this.applicationId);
	}

	@Override
	public void setPrincipalId(final Long principalId) {
		this.em	.createStoredProcedureQuery(getLoggingBaseSchema() + ".LOGINFO.Set_Principal_ID")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.setParameter(1, principalId)
				.execute();
	}

	@Override
	public Long getPrincipalId() {
		return ((BigDecimal) this.em.createNativeQuery("SELECT " + getLoggingBaseSchema()
				+ ".LOGINFO.Get_Principal_ID() FROM DUAL")
									.getSingleResult()).longValue();
	}

	@Override
	public void setApplicationId(final Long applicationId) {
		this.em	.createStoredProcedureQuery(getLoggingBaseSchema() + ".LOGINFO.Set_Application_ID")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.setParameter(1, applicationId)
				.execute();
	}

	@Override
	public Long getApplicationId() {
		return ((BigDecimal) this.em.createNativeQuery("SELECT " + getLoggingBaseSchema()
				+ ".LOGINFO.Get_Application_ID() FROM DUAL")
									.getSingleResult()).longValue();
	}

	@Override
	public void setPrincipalIdAndApplicationId(final Long principalId, final Long applicationId) {
		this.em	.createStoredProcedureQuery(getLoggingBaseSchema() + ".LOGINFO.SetPrincipalAndApplication")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
				.setParameter(1, principalId)
				.setParameter(2, applicationId)
				.execute();
	}

}
