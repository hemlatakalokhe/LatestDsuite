package de.bonprix.configuration;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.cache.interceptor.DefaultKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.google.common.collect.Lists;
import de.bonprix.exception.CacheManagerException;

@Configuration
@EnableCaching
public class CachingConfiguration extends CachingConfigurerSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(CachingConfiguration.class);

	@Qualifier("ehCacheCacheManager")
	@Autowired(required = false)
	private CacheManager ehCacheCacheManager;

	@Bean
	@Override
	public CacheManager cacheManager() {
		CachingConfiguration.LOGGER.info("Configuring composite cache manager");

		final List<CacheManager> cacheManagers = Lists.newArrayList();

		final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			for (final Resource resource : resolver.getResources("classpath*:/*ehcache.xml")) {
				cacheManagers.add(createEhCache(resource));
			}
		} catch (final IOException e) {
			throw new CacheManagerException("could not resolve cache configurations", e);
		}

		final CompositeCacheManager cacheManager = new CompositeCacheManager();

		cacheManager.setCacheManagers(cacheManagers);
		cacheManager.setFallbackToNoOpCache(false);

		return cacheManager;
	}

	private CacheManager createEhCache(final Resource resource) {
		CachingConfiguration.LOGGER.debug("Configuring ehcache manager with config file " + resource.getFilename());
		final net.sf.ehcache.config.Configuration configuration = EhCacheManagerUtils.parseConfiguration(resource);

		return new EhCacheCacheManager(new net.sf.ehcache.CacheManager(configuration));

	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}

}