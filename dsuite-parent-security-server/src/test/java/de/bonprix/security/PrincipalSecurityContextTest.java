/**
 *
 */
package de.bonprix.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertTrue;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.exception.WrongPrincipalTypeException;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Principal;
import de.bonprix.user.dto.User;

/**
 * @author cthiel
 * @date 06.10.2016
 *
 */
public class PrincipalSecurityContextTest {

	SecurityContext context;
	String authKey;
	Principal testPrincipal;
	String rootAuthKey;
	Principal rootPrincipal;
	BonprixAuthentication authentication;
	Principal returnPrincipal;
	Principal returnRootPrincipal;

	@BeforeMethod
	public void init() {
		this.context = SecurityContextHolder.getContext();

		this.authKey = "qwertzuio";
		this.testPrincipal = new Principal();
		this.testPrincipal.setId(1L);
		this.testPrincipal.setName("unittest");
		this.testPrincipal.setClientId(1L);
		this.testPrincipal.setLanguageId(301L);

		this.rootAuthKey = "asdfghjkl";
		this.rootPrincipal = new Principal();
		this.rootPrincipal.setId(12L);
		this.rootPrincipal.setName("roottest");
		this.rootPrincipal.setClientId(1L);
		this.rootPrincipal.setLanguageId(301L);

		this.authentication = new BonprixAuthentication(this.testPrincipal, this.authKey, null);
		this.authentication.setRootPrincipal(this.rootPrincipal);
		this.authentication.setRootAuthKey(this.rootAuthKey);
		this.context.setAuthentication(this.authentication);

		this.returnPrincipal = PrincipalSecurityContext.getAuthenticatedPrincipal();
		this.returnRootPrincipal = PrincipalSecurityContext.getRootPrincipal();
	}

	@Test
	public void getPrincipalTest() {
		assertThat(this.returnPrincipal, notNullValue());
		assertThat(this.returnPrincipal.getId(), equalTo(1L));
		assertThat(this.returnPrincipal.getName(), equalTo("unittest"));
		assertThat(this.returnRootPrincipal, notNullValue());
		assertThat(this.returnRootPrincipal.getId(), equalTo(12L));
		assertThat(this.returnRootPrincipal.getName(), equalTo("roottest"));
	}

	@Test(expectedExceptions = { WrongPrincipalTypeException.class })
	public void getPrincipalTestWrongType() {
		final SecurityContext context = SecurityContextHolder.getContext();
		final User testPrincipal = new User();

		final PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
				testPrincipal, null);
		context.setAuthentication(authentication);

		PrincipalSecurityContext.getAuthenticatedPrincipal();
	}

	@Test(expectedExceptions = { PrincipalMissingException.class })
	public void getCurrentPrincipalNull() {
		SecurityContextHolder.getContext()
			.setAuthentication(null);

		PrincipalSecurityContext.getAuthenticatedPrincipal();
	}

	@Test
	public void hasPermissionTest() {
		// Setup Data
		this.testPrincipal.addCapability("TEST_CAPABILITY", PermissionType.EDIT);
		// Actual call
		PermissionType returnPermissionType = this.returnPrincipal.getPermissionType("TEST_CAPABILITY");
		Boolean blnResult = PrincipalSecurityContext.hasPermission("TEST_CAPABILITY", returnPermissionType);
		/**
		 * Final assertion - In all cases the permissionType for a set of
		 * capability should return true for hasPermission call
		 */
		assertTrue(blnResult);
	}
}
