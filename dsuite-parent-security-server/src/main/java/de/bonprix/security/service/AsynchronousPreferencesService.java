package de.bonprix.security.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.PreferencesService;
import de.bonprix.user.dto.Principal;
import de.bonprix.user.service.ApplicationPreferences;

/**
 * This preferences service is a wrapper for the actual
 * {@link PreferencesService} to provide a batch-interface for reading and
 * writing all preferences.<br>
 * <br>
 * While reading this service will cache the collection of all preferences in
 * request scope so the collection of preferences will only be fetched once
 * during a request.<br>
 * <br>
 * While writing each write request will be enqueued and aggregated to one
 * batch-call every minute. This way multiple calls to
 * <code>setPreference()</code> will not result in multiple calls to the
 * {@link PreferencesService}.
 *
 * @author cthiel
 * @date 19.01.2017
 *
 */
@Component
public class AsynchronousPreferencesService {
	public static final String PREFERENCES_CACHE_KEY = "PREFERENCES_CACHE_KEY";

	private static final Logger LOGGER = LoggerFactory.getLogger(AsynchronousPreferencesService.class);

	@Resource
	private PreferencesService preferencesService;

	@Value("${application.id}")
	Long applicationId;

	final Queue<PreferencesContainer> queue = new ConcurrentLinkedQueue<>();

	private final ObjectMapper jsonObjectMapper;

	public AsynchronousPreferencesService() {
		this.jsonObjectMapper = new ObjectMapper();
	}

	Map<String, String> getPreferences() {
		final Principal principal = PrincipalSecurityContext.getRootPrincipal();

		final RequestAttributes attr = RequestContextHolder.currentRequestAttributes();

		ApplicationPreferences applicationPreferences = (ApplicationPreferences) attr
			.getAttribute(AsynchronousPreferencesService.PREFERENCES_CACHE_KEY, RequestAttributes.SCOPE_SESSION);
		if (applicationPreferences != null) {
			return applicationPreferences.getKeyValues();
		}

		applicationPreferences = this.preferencesService
			.findApplicationPreferences(principal.getId(), this.applicationId, principal.getClientId());

		if (applicationPreferences != null) {
			attr.setAttribute(	AsynchronousPreferencesService.PREFERENCES_CACHE_KEY, applicationPreferences,
								RequestAttributes.SCOPE_SESSION);

			return applicationPreferences.getKeyValues();
		}
		return new HashMap<>();
	}

	/**
	 * Returns the value of the given preference key or <code>null</code>. The
	 * preferences are always fetched for the current active
	 * principal/client/application combination of the current root principal.
	 *
	 * @param key
	 *            the preference key
	 * @return the value or <code>null</code>
	 */
	public String getStringPreference(final String key) {
		return getPreferences().get(key);
	}

	/**
	 * Returns the value of the given preference key as Integer or
	 * <code>null</code>. The preferences are always fetched for the current
	 * active principal/client/application combination of the current root
	 * principal.
	 *
	 * @param key
	 *            the preference key
	 * @return the value or <code>null</code>
	 *
	 * @throws NumberFormatException
	 *             then the stored String value is non-null but no valid integer
	 *             number
	 */
	public Integer getIntegerPreference(final String key) {
		final String preference = getPreferences().get(key);

		return preference == null ? null : Integer.valueOf(preference);
	}

	/**
	 * Returns the value of the given preference key as Long or
	 * <code>null</code>. The preferences are always fetched for the current
	 * active principal/client/application combination of the current root
	 * principal.
	 *
	 * @param key
	 *            the preference key
	 * @return the value or <code>null</code>
	 *
	 * @throws NumberFormatException
	 *             then the stored String value is non-null but no valid long
	 *             number
	 */
	public Long getLongPreference(final String key) {
		final String preference = getPreferences().get(key);

		return preference == null ? null : Long.valueOf(preference);
	}

	/**
	 * Returns the value of the given preference key as parsed JSON object or
	 * <code>null</code>. The JSON ObjectMapper will try to parse the content of
	 * the string into the given class object. <br>
	 * <br>
	 * The preferences are always fetched for the current active
	 * principal/client/application combination of the current root principal.
	 *
	 * @param key
	 *            the preference key
	 * @param clazz
	 *            the target class object
	 * @return the value or <code>null</code>
	 *
	 * @throws JsonProcessingException
	 *             in case of any JSON parser error
	 * @throws IOException
	 *             in case of any IOException ocurring during parsing
	 */
	public <E> E getJsonPreference(final String key, final Class<E> clazz) throws JsonProcessingException, IOException {
		final String preference = getPreferences().get(key);

		return preference == null ? null : this.jsonObjectMapper.readValue(preference, clazz);
	}

	/**
	 * Sets the value of the given key. This method actually does not directly
	 * call the persistence but only enqueues such a storage request to
	 * aggregate all preference storage actions will be aggregated and sent in
	 * batch-mode to the PreferencesService.
	 *
	 * @param key
	 *            the preference key
	 * @param value
	 *            the new preference value
	 */
	public void setPreference(final String key, final Integer value) {
		setPreference(key, Integer.toString(value));
	}

	/**
	 * Sets the value of the given key. This method actually does not directly
	 * call the persistence but only enqueues such a storage request to
	 * aggregate all preference storage actions will be aggregated and sent in
	 * batch-mode to the PreferencesService.
	 *
	 * @param key
	 *            the preference key
	 * @param value
	 *            the new preference value
	 */
	public void setPreference(final String key, final Long value) {
		setPreference(key, Long.toString(value));
	}

	/**
	 * Sets the value of the given key. This method will encode the given Object
	 * into a JSON format by using the ObjectMapper.<br>
	 * <br>
	 * This method actually does not directly call the persistence but only
	 * enqueues such a storage request to aggregate all preference storage
	 * actions will be aggregated and sent in batch-mode to the
	 * PreferencesService.
	 *
	 * @param key
	 *            the preference key
	 * @param value
	 *            the new preference value
	 *
	 * @throws JsonProcessingException
	 *             in case of any error during the JSON conversion
	 */
	public void setPreference(final String key, final Object value) throws JsonProcessingException {
		setPreference(key, this.jsonObjectMapper.writeValueAsString(value));
	}

	/**
	 * Sets the value of the given key. This method actually does not directly
	 * call the persistence but only enqueues such a storage request to
	 * aggregate all preference storage actions will be aggregated and sent in
	 * batch-mode to the PreferencesService.
	 *
	 * @param key
	 *            the preference key
	 * @param value
	 *            the new preference value
	 */
	public void setPreference(final String key, final String value) {
		final Principal principal = PrincipalSecurityContext.getRootPrincipal();
		final PreferencesContainer container = new PreferencesContainer(principal.getClientId(), principal.getId(), key,
				value);

		AsynchronousPreferencesService.LOGGER
			.debug("Setting preference " + key + " to " + value + " for principal " + principal.getName());
		getPreferences().put(key, value);

		synchronized (this.queue) {
			this.queue.add(container);
		}
	}

	@Scheduled(cron = "0 * * * * *")
	void processQueue() {
		final List<PreferencesContainer> preferencesContainers = new ArrayList<>();
		synchronized (this.queue) {
			PreferencesContainer preferencesContainer = null;
			while ((preferencesContainer = this.queue.poll()) != null) {
				preferencesContainers.add(preferencesContainer);
			}
		}
		final HashMap<PrincipalClient, HashMap<String, String>> principalKeyValueMap = new HashMap<>();
		for (final PreferencesContainer container : preferencesContainers) {
			HashMap<String, String> keyValueMap = principalKeyValueMap
				.get(new PrincipalClient(container.principalId, container.clientId));
			if (keyValueMap == null) {
				keyValueMap = new HashMap<>();
				principalKeyValueMap.put(new PrincipalClient(container.principalId, container.clientId), keyValueMap);
			}
			keyValueMap.put(container.key, container.value);
		}
		AsynchronousPreferencesService.LOGGER.trace(
													"started to proceed {} updateApplicationPreferences for applicationId: {}",
													principalKeyValueMap.size(), this.applicationId);
		for (final Entry<PrincipalClient, HashMap<String, String>> principalEntry : principalKeyValueMap.entrySet()) {
			final ApplicationPreferences preferences = new ApplicationPreferences();
			preferences.setApplicationId(this.applicationId);
			preferences.setPrincipalId(principalEntry.getKey().principalId);
			preferences.setClientId(principalEntry.getKey().clientId);
			preferences.setKeyValues(principalEntry.getValue());

			this.preferencesService.updateApplicationPreferences(preferences);
		}
	}

	static class PreferencesContainer {
		public PreferencesContainer(final long clientId, final long principalId, final String key, final String value) {
			this.clientId = clientId;
			this.principalId = principalId;
			this.key = key;
			this.value = value;
		}

		long clientId;
		long principalId;
		String key;
		String value;
	}

	static class PrincipalClient {
		long clientId;
		long principalId;

		public PrincipalClient(final long principalId, final long clientId) {
			super();
			this.clientId = clientId;
			this.principalId = principalId;
		}

		public long getClientId() {
			return this.clientId;
		}

		public void setClientId(final long clientId) {
			this.clientId = clientId;
		}

		public long getPrincipalId() {
			return this.principalId;
		}

		public void setPrincipalId(final long principalId) {
			this.principalId = principalId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (this.clientId ^ (this.clientId >>> 32));
			result = prime * result + (int) (this.principalId ^ (this.principalId >>> 32));
			return result;
		}

		@Override
		public String toString() {
			return "PrincipalClient [clientId=" + this.clientId + ", principalId=" + this.principalId + "]";
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final PrincipalClient other = (PrincipalClient) obj;
			if (this.clientId != other.clientId) {
				return false;
			}
			if (this.principalId != other.principalId) {
				return false;
			}
			return true;
		}

	}
}
