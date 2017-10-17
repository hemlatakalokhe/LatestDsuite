package de.bonprix.demo.baseConfiguredUnitTest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

import de.bonprix.SecurityContextSetter;
import de.bonprix.user.dto.Principal;

/**
 * @author vbaghdas
 */
public class BaseConfiguredUnitTestTest {

	@Test
	public void creationTest() throws Exception {
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { DummyBaseConfiguredUnitTest.class });
		testng.addListener(tla);
		testng.run();

		// 1. check client security part
		PreAuthenticatedAuthenticationToken authentication = (PreAuthenticatedAuthenticationToken) SecurityContextHolder
			.getContext()
			.getAuthentication();
		Principal principal = (Principal) authentication.getPrincipal();
		Assert.assertSame(principal.getId(), SecurityContextSetter.DEFAULT_PRINCIPAL_ID);
		Assert.assertSame(principal.getName(), SecurityContextSetter.DEFAULT_PRINCIPAL);
		Assert.assertSame(principal.getClientId(), SecurityContextSetter.DEFAULT_CLIENT_ID);
		Assert.assertSame(principal.getLanguageId(), SecurityContextSetter.DEFAULT_LANGUAGE_ID);

		DummyBaseConfiguredUnitTest tInstance = (DummyBaseConfiguredUnitTest) tla.getPassedTests()
			.get(0)
			.getInstance();
		// 2. check mock injection
		Assert.assertNotNull(tInstance.getBeanIntoWhichInject());
		Assert.assertNotNull(tInstance.getBeanToInject());
		Assert.assertSame(tInstance.getBeanIntoWhichInject()
			.getBeanToInject(), tInstance.getBeanToInject());

		// 3. check post construct method execution
		Assert.assertTrue(tInstance.getBeanIntoWhichInject()
			.getPostConstructExecuted());

		Assert.assertTrue(tInstance.getBeanIntoWhichInject()
			.getSuperPostConstructExecuted());
	}
}
