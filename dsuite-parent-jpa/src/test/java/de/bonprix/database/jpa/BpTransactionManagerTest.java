package de.bonprix.database.jpa;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.database.ettool.EtToolLoggingRepository;
import de.bonprix.database.jpa.BpTransactionManager;

public class BpTransactionManagerTest extends BaseConfiguredUnitTest {

	@InjectMocks
	BpTransactionManager bpTransactionManager = new BpTransactionManager();

	@Mock
	EntityManagerFactory entityManagerFactory;

	@Mock
	JpaDialect jpaDialect;

	@Mock
	EtToolLoggingRepository etToolLoggingRepository;

	@BeforeMethod()
	public void init() throws SQLException {
		Mockito.when(this.entityManagerFactory.createEntityManager())
			.thenReturn(new DummyEntityManager());
		this.bpTransactionManager.setJpaDialect(this.jpaDialect);
		Mockito.when(this.jpaDialect.beginTransaction(Mockito.any(), Mockito.any()))
			.thenReturn(new Object());
	}

	@Test
	public void testDoBeginActive() {
		this.bpTransactionManager.useEttoolLogging = true;

		this.bpTransactionManager.getTransaction(new DummyTransactionDefinition());

		Mockito.verify(this.etToolLoggingRepository)
			.init();
	}

	@Test
	public void testDoBeginInactive() {
		this.bpTransactionManager.useEttoolLogging = false;

		this.bpTransactionManager.getTransaction(new DummyTransactionDefinition());

		Mockito.verify(this.etToolLoggingRepository, Mockito.never())
			.init();
	}

	private class DummyTransactionDefinition implements TransactionDefinition {

		@Override
		public boolean isReadOnly() {
			return false;
		}

		@Override
		public int getTimeout() {
			return 0;
		}

		@Override
		public int getPropagationBehavior() {
			return 0;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public int getIsolationLevel() {
			return 0;
		}
	}

	private class DummyEntityManager implements EntityManager {

		@Override
		public <T> T unwrap(final Class<T> cls) {
			return null;
		}

		@Override
		public void setProperty(final String propertyName, final Object value) {
			// empty
		}

		@Override
		public void setFlushMode(final FlushModeType flushMode) {
			// empty
		}

		@Override
		public void remove(final Object entity) {
			// empty
		}

		@Override
		public void refresh(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
			// empty
		}

		@Override
		public void refresh(final Object entity, final LockModeType lockMode) {
			// empty
		}

		@Override
		public void refresh(final Object entity, final Map<String, Object> properties) {
			// empty
		}

		@Override
		public void refresh(final Object entity) {
			// empty
		}

		@Override
		public void persist(final Object entity) {
			// empty
		}

		@Override
		public <T> T merge(final T entity) {
			return null;
		}

		@Override
		public void lock(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
			// empty
		}

		@Override
		public void lock(final Object entity, final LockModeType lockMode) {
			// empty
		}

		@Override
		public void joinTransaction() {
			// empty
		}

		@Override
		public boolean isOpen() {
			return false;
		}

		@Override
		public boolean isJoinedToTransaction() {
			return false;
		}

		@Override
		public EntityTransaction getTransaction() {
			return null;
		}

		@Override
		public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
			return null;
		}

		@Override
		public Map<String, Object> getProperties() {
			return null;
		}

		@Override
		public Metamodel getMetamodel() {
			return null;
		}

		@Override
		public LockModeType getLockMode(final Object entity) {
			return null;
		}

		@Override
		public FlushModeType getFlushMode() {
			return null;
		}

		@Override
		public EntityManagerFactory getEntityManagerFactory() {
			return null;
		}

		@Override
		public <T> List<EntityGraph<? super T>> getEntityGraphs(final Class<T> entityClass) {
			return null;
		}

		@Override
		public EntityGraph<?> getEntityGraph(final String graphName) {
			return null;
		}

		@Override
		public Object getDelegate() {
			return null;
		}

		@Override
		public CriteriaBuilder getCriteriaBuilder() {
			return null;
		}

		@Override
		public void flush() {
		}

		@Override
		public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode,
				final Map<String, Object> properties) {
			return null;
		}

		@Override
		public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode) {
			return null;
		}

		@Override
		public <T> T find(final Class<T> entityClass, final Object primaryKey, final Map<String, Object> properties) {
			return null;
		}

		@Override
		public <T> T find(final Class<T> entityClass, final Object primaryKey) {
			return null;
		}

		@Override
		public void detach(final Object entity) {
		}

		@Override
		public StoredProcedureQuery createStoredProcedureQuery(final String procedureName,
				final String... resultSetMappings) {
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public StoredProcedureQuery createStoredProcedureQuery(final String procedureName,
				final Class... resultClasses) {
			return null;
		}

		@Override
		public StoredProcedureQuery createStoredProcedureQuery(final String procedureName) {
			return null;
		}

		@Override
		public <T> TypedQuery<T> createQuery(final String qlString, final Class<T> resultClass) {
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Query createQuery(final CriteriaDelete deleteQuery) {
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Query createQuery(final CriteriaUpdate updateQuery) {
			return null;
		}

		@Override
		public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> criteriaQuery) {
			return null;
		}

		@Override
		public Query createQuery(final String qlString) {
			return null;
		}

		@Override
		public Query createNativeQuery(final String sqlString, final String resultSetMapping) {
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Query createNativeQuery(final String sqlString, final Class resultClass) {
			return null;
		}

		@Override
		public Query createNativeQuery(final String sqlString) {
			return null;
		}

		@Override
		public StoredProcedureQuery createNamedStoredProcedureQuery(final String name) {
			return null;
		}

		@Override
		public <T> TypedQuery<T> createNamedQuery(final String name, final Class<T> resultClass) {
			return null;
		}

		@Override
		public Query createNamedQuery(final String name) {
			return null;
		}

		@Override
		public EntityGraph<?> createEntityGraph(final String graphName) {
			return null;
		}

		@Override
		public <T> EntityGraph<T> createEntityGraph(final Class<T> rootType) {
			return null;
		}

		@Override
		public boolean contains(final Object entity) {
			return false;
		}

		@Override
		public void close() {
		}

		@Override
		public void clear() {
		}
	}
}
