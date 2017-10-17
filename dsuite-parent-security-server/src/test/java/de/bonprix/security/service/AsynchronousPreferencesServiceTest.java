package de.bonprix.security.service;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;

import de.bonprix.security.BonprixAuthentication;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.security.service.AsynchronousPreferencesService.PreferencesContainer;
import de.bonprix.user.dto.PreferencesService;
import de.bonprix.user.dto.Principal;
import de.bonprix.user.service.ApplicationPreferences;
import de.bonprix.user.service.builder.ApplicationPreferencesBuilder;

public class AsynchronousPreferencesServiceTest {

	private static final long APPLICATION_ID = 42L;

	private static final long CLIENT_ID = 1L;

	private static final long PRINCIPAL_ID = 4711L;

	@InjectMocks
	private AsynchronousPreferencesService service;

	@Mock
	private PreferencesService preferencesService;

	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);

		final Principal principal = new Principal(AsynchronousPreferencesServiceTest.PRINCIPAL_ID, "testjohnny");
		principal.setClientId(AsynchronousPreferencesServiceTest.CLIENT_ID);

		final SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(new BonprixAuthentication(principal, "qwertzuio", null));

		ReflectionTestUtils.setField(this.service, "applicationId", AsynchronousPreferencesServiceTest.APPLICATION_ID);
		this.service.queue.clear();
	}

	@Test
	public void testGetPreferencesNocache() {
		final RequestAttributes attr = Mockito.mock(RequestAttributes.class);
		RequestContextHolder.setRequestAttributes(attr);

		final HashMap<String, String> prefs = Maps.<String, String> newHashMap();
		prefs.put("key1", "value1");
		prefs.put("key2", "value2");
		Mockito.when(this.preferencesService.findApplicationPreferences(AsynchronousPreferencesServiceTest.PRINCIPAL_ID,
																		AsynchronousPreferencesServiceTest.APPLICATION_ID,
																		AsynchronousPreferencesServiceTest.CLIENT_ID))
			.thenReturn(new ApplicationPreferencesBuilder()
				.withApplicationId(AsynchronousPreferencesServiceTest.APPLICATION_ID)
				.withPrincipalId(AsynchronousPreferencesServiceTest.PRINCIPAL_ID)
				.withClientId(AsynchronousPreferencesServiceTest.CLIENT_ID)
				.withKeyValues(prefs)
				.build());
		final Map<String, String> result = this.service.getPreferences();
		MatcherAssert.assertThat(result, Matchers.notNullValue());
		MatcherAssert.assertThat(result.size(), Matchers.equalTo(2));
		MatcherAssert.assertThat(result, Matchers.hasEntry("key1", "value1"));
		MatcherAssert.assertThat(result, Matchers.hasEntry("key2", "value2"));
	}

	@Test
	public void testGetPreferencesCached() {
		final RequestAttributes attr = Mockito.mock(RequestAttributes.class);
		RequestContextHolder.setRequestAttributes(attr);

		final HashMap<String, String> prefs = Maps.<String, String> newHashMap();
		prefs.put("key1", "value1");
		prefs.put("key2", "value2");
		Mockito.when(attr.getAttribute(	AsynchronousPreferencesService.PREFERENCES_CACHE_KEY,
										RequestAttributes.SCOPE_SESSION))
			.thenReturn(new ApplicationPreferencesBuilder()
				.withApplicationId(AsynchronousPreferencesServiceTest.APPLICATION_ID)
				.withPrincipalId(AsynchronousPreferencesServiceTest.PRINCIPAL_ID)
				.withClientId(AsynchronousPreferencesServiceTest.CLIENT_ID)
				.withKeyValues(prefs)
				.build());
		final String value1_1 = this.service.getStringPreference("key1");
		final String value1_2 = this.service.getStringPreference("key1");
		final String value2 = this.service.getStringPreference("key2");

		Mockito.verify(this.preferencesService, Mockito.never())
			.findApplicationPreferences(AsynchronousPreferencesServiceTest.PRINCIPAL_ID,
										AsynchronousPreferencesServiceTest.APPLICATION_ID,
										AsynchronousPreferencesServiceTest.CLIENT_ID);

		MatcherAssert.assertThat(value1_1, Matchers.notNullValue());
		MatcherAssert.assertThat(value1_1, Matchers.equalTo(value1_2));
		MatcherAssert.assertThat(value1_1, Matchers.equalTo("value1"));
		MatcherAssert.assertThat(value2, Matchers.equalTo("value2"));
	}

	@Test
	public void testSetPreferenceString() {
		MatcherAssert.assertThat(this.service.queue.size(), Matchers.equalTo(0));
		this.service.setPreference("key1", "value1");
		MatcherAssert.assertThat(this.service.queue.size(), Matchers.equalTo(1));
		final PreferencesContainer preferenceContainer = this.service.queue.peek();
		MatcherAssert.assertThat(preferenceContainer.key, Matchers.equalTo("key1"));
		MatcherAssert.assertThat(preferenceContainer.value, Matchers.equalTo("value1"));
		MatcherAssert.assertThat(	preferenceContainer.principalId,
									Matchers.equalTo(AsynchronousPreferencesServiceTest.PRINCIPAL_ID));
	}

	@Test
	public void testSetPreferenceJson() throws JsonProcessingException {
		MatcherAssert.assertThat(this.service.queue.size(), Matchers.equalTo(0));
		this.service.setPreference("key1", PrincipalSecurityContext.getRootPrincipal());
		MatcherAssert.assertThat(this.service.queue.size(), Matchers.equalTo(1));
		final PreferencesContainer preferenceContainer = this.service.queue.peek();
		MatcherAssert.assertThat(preferenceContainer.key, Matchers.equalTo("key1"));
		MatcherAssert
			.assertThat(preferenceContainer.value,
						Matchers.startsWith("{\"id\":4711,\"name\":\"testjohnny\",\"fullname\":\"testjohnny\""));
		MatcherAssert.assertThat(	preferenceContainer.principalId,
									Matchers.equalTo(AsynchronousPreferencesServiceTest.PRINCIPAL_ID));
	}

	@Test
	public void testProcessQueue() {
		this.service.setPreference("key1", "value1");
		this.service.setPreference("key1", "value2");
		this.service.setPreference("key2", "value3");
		final Principal principalNew = new Principal(1234L, "testmax");
		principalNew.setClientId(1L);
		SecurityContextHolder.getContext()
			.setAuthentication(new BonprixAuthentication(principalNew, "sdfghjk", null));
		this.service.setPreference("key2", "value3");

		this.service.processQueue();
		final HashMap<String, String> prinipal1KeyValues = new HashMap<>();
		prinipal1KeyValues.put("key1", "value2");
		prinipal1KeyValues.put("key2", "value3");
		final HashMap<String, String> prinipal2KeyValues = new HashMap<>();
		prinipal2KeyValues.put("key2", "value3");

		Mockito.verify(this.preferencesService)
			.updateApplicationPreferences(new ApplicationPreferencesBuilder()
				.withApplicationId(AsynchronousPreferencesServiceTest.APPLICATION_ID)
				.withPrincipalId(1234L)
				.withClientId(AsynchronousPreferencesServiceTest.CLIENT_ID)
				.withKeyValues(prinipal2KeyValues)
				.build());
		Mockito.verify(this.preferencesService)
			.updateApplicationPreferences(new ApplicationPreferencesBuilder()
				.withApplicationId(AsynchronousPreferencesServiceTest.APPLICATION_ID)
				.withPrincipalId(AsynchronousPreferencesServiceTest.PRINCIPAL_ID)
				.withClientId(AsynchronousPreferencesServiceTest.CLIENT_ID)
				.withKeyValues(prinipal1KeyValues)
				.build());
		Mockito.verify(this.preferencesService, Mockito.times(2))
			.updateApplicationPreferences(Mockito.any(ApplicationPreferences.class));
	}

}
