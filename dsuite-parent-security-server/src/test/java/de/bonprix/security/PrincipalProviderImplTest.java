package de.bonprix.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Principal;

public class PrincipalProviderImplTest {

	private PrincipalProvider principalProvider;
	private SecurityContext context;
	private final String authKey = "qwertzuio";
	private Principal testPrincipal;
	private BonprixAuthentication authentication;

	@BeforeMethod
	public void init() {
		this.principalProvider = new PrincipalProviderImpl();

		this.context = SecurityContextHolder.getContext();

		this.testPrincipal = new Principal();
		this.testPrincipal.setId(1L);
		this.testPrincipal.setName("unittest");
		this.testPrincipal.setClientId(1L);
		this.testPrincipal.setLanguageId(301L);

		this.authentication = new BonprixAuthentication(this.testPrincipal, this.authKey, null);

		this.context.setAuthentication(this.authentication);
	}

	@Test
	public void hasCapabilityTest() {
		// Setup Data
		this.testPrincipal.addCapability("TEST_CAPABILITY", PermissionType.READ);

		// Actual call
		Boolean isCapable = this.principalProvider.hasCapability("TEST_CAPABILITY");
		Assert.assertTrue(isCapable);
	}

	@Test
	public void hasNotCapabilityTest() {
		// Setup Data
		this.testPrincipal.addCapability("TEST_CAPABILITY", PermissionType.NONE);

		// Actual call
		Boolean isCapable = this.principalProvider.hasCapability("TEST_CAPABILITY");
		Assert.assertFalse(isCapable);
	}
}
