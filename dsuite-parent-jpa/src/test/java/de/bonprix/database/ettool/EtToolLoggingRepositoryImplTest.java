package de.bonprix.database.ettool;

import java.math.BigDecimal;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.database.ettool.EtToolLoggingRepositoryImpl;

public class EtToolLoggingRepositoryImplTest extends BaseConfiguredUnitTest {

	private String loggingBaseSchema = "ds_global";

	@InjectMocks
	private final EtToolLoggingRepositoryImpl etToolLoggingRepository = new EtToolLoggingRepositoryImpl() {
		protected String getLoggingBaseSchema() {
			return EtToolLoggingRepositoryImplTest.this.loggingBaseSchema;
		}
	};

	@Mock
	private EntityManager em;

	@Mock
	private StoredProcedureQuery storedProcedureQuery;

	@Mock
	private Query query;

	@BeforeMethod
	public void init() {
		// storedProcedure
		Mockito.when(this.em.createStoredProcedureQuery(Mockito.anyString()))
			.thenReturn(this.storedProcedureQuery);
		Mockito.when(this.storedProcedureQuery.registerStoredProcedureParameter(Mockito.anyInt(), Mockito.any(),
																				Mockito.any()))
			.thenReturn(this.storedProcedureQuery);
		Mockito.when(this.storedProcedureQuery.setParameter(Mockito.anyInt(), Mockito.any()))
			.thenReturn(this.storedProcedureQuery);

		// query
		Mockito.when(this.em.createNativeQuery(Mockito.anyString()))
			.thenReturn(this.query);
	}

	@Test
	public void testSetPrincipalId() {
		final Long principalId = new Random().nextLong();

		this.etToolLoggingRepository.setPrincipalId(principalId);

		Mockito.verify(this.em)
			.createStoredProcedureQuery(this.loggingBaseSchema + ".LOGINFO.Set_Principal_ID");

		Mockito.verify(this.storedProcedureQuery)
			.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
		Mockito.verify(this.storedProcedureQuery)
			.setParameter(1, principalId);
		Mockito.verify(this.storedProcedureQuery)
			.execute();
	}

	@Test
	public void testGetPrincipalId() {
		final Long principalId = new Random().nextLong();
		final BigDecimal singleResult = new BigDecimal(principalId);
		Mockito.when(this.query.getSingleResult())
			.thenReturn(singleResult);

		final Long result = this.etToolLoggingRepository.getPrincipalId();

		Mockito.verify(this.em)
			.createNativeQuery("SELECT " + this.loggingBaseSchema + ".LOGINFO.Get_Principal_ID() FROM DUAL");

		Mockito.verify(this.query)
			.getSingleResult();

		Assert.assertTrue(principalId.equals(result));
	}

	@Test
	public void testSetApplicationId() {
		final Long applicationId = new Random().nextLong();

		this.etToolLoggingRepository.setApplicationId(applicationId);

		Mockito.verify(this.em)
			.createStoredProcedureQuery(this.loggingBaseSchema + ".LOGINFO.Set_Application_ID");

		Mockito.verify(this.storedProcedureQuery)
			.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
		Mockito.verify(this.storedProcedureQuery)
			.setParameter(1, applicationId);
		Mockito.verify(this.storedProcedureQuery)
			.execute();
	}

	@Test
	public void testGetApplicationId() {
		final Long applicationId = new Random().nextLong();
		final BigDecimal singleResult = new BigDecimal(applicationId);
		Mockito.when(this.query.getSingleResult())
			.thenReturn(singleResult);

		final Long result = this.etToolLoggingRepository.getApplicationId();

		Mockito.verify(this.em)
			.createNativeQuery("SELECT " + this.loggingBaseSchema + ".LOGINFO.Get_Application_ID() FROM DUAL");

		Mockito.verify(this.query)
			.getSingleResult();

		Assert.assertTrue(applicationId.equals(result));
	}

	@Test
	public void testSetPrincipalIdAndApplicationId() {
		final Long principalId = new Random().nextLong();
		final Long applicationId = new Random().nextLong();

		this.etToolLoggingRepository.setPrincipalIdAndApplicationId(principalId, applicationId);

		Mockito.verify(this.em)
			.createStoredProcedureQuery(this.loggingBaseSchema + ".LOGINFO.SetPrincipalAndApplication");

		Mockito.verify(this.storedProcedureQuery)
			.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
		Mockito.verify(this.storedProcedureQuery)
			.setParameter(1, principalId);
		Mockito.verify(this.storedProcedureQuery)
			.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
		Mockito.verify(this.storedProcedureQuery)
			.setParameter(2, applicationId);
		Mockito.verify(this.storedProcedureQuery)
			.execute();
	}

}
