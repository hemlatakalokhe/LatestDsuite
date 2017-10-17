package de.bonprix.configuration;

import java.util.Properties;

import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.CommonsLogLevel;
import net.ttddyy.dsproxy.listener.CommonsQueryLoggingListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.support.ProxyDataSource;

/**
 * Configuration for in memory databases used in unit tests
 * 
 * @author vbaghdas
 * 
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "de.bonprix.*")
@PropertySource("classpath:/h2.properties")
public class InMemoryJpaConfig {

	@Resource
	private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();

		lcemfb.setDataSource(this.dataSource());
		lcemfb.setPackagesToScan(new String[] { "de.bonprix" });
		lcemfb.setPersistenceUnitName("InMemoryJpaConfigPU");

		final HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		lcemfb.setJpaVendorAdapter(va);

		final Properties ps = new Properties();
		ps.put("hibernate.show_sql", true);
		ps.put("hibernate.use_sql_comments", true);
		ps.put("hibernate.format_sql", true);
		ps.put("hibernate.type", "trace");
		ps.put("hibernate.hbm2ddl.auto", "create");
		ps.put("hibernate.hbm2ddl.import_files", "db/data.sql");
		ps.put(	"hibernate.cache.use_second_level_cache",
				this.environment.getRequiredProperty("use.second.level.cache"));
		ps.put(	"spring.jpa.properties.hibernate.cache.use_query_cache",
				this.environment.getRequiredProperty("use_query_cache"));

		lcemfb.setJpaProperties(ps);
		lcemfb.afterPropertiesSet();

		return lcemfb;
	}

	@Bean
	public ProxyDataSource dataSource() {
		ProxyDataSource proxyDataSource = new ProxyDataSource();
		// set datasource
		proxyDataSource.setDataSource(new EmbeddedDatabaseBuilder()	.setType(EmbeddedDatabaseType.H2)
																	.addScript("db/create.sql")
																	.build());

		// query listeners
		ChainListener chainListener = new ChainListener();
		CommonsQueryLoggingListener commonsQueryLoggingListener = new CommonsQueryLoggingListener();
		commonsQueryLoggingListener.setLogLevel(CommonsLogLevel.INFO);
		chainListener.addListener(commonsQueryLoggingListener);
		DataSourceQueryCountListener dataSourceQueryCountListener = new DataSourceQueryCountListener();
		chainListener.addListener(dataSourceQueryCountListener);
		proxyDataSource.setListener(chainListener);

		return proxyDataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		final JpaTransactionManager tm = new JpaTransactionManager();

		tm.setEntityManagerFactory(this	.entityManagerFactory()
										.getObject());

		return tm;

	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
