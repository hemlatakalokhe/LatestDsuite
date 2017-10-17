/**
 *
 */
package de.bonprix.security.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import java.util.Locale;

import org.testng.annotations.Test;

import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Principal;
import de.bonprix.user.dto.PrincipalRole;

/**
 * @author cthiel
 * @date 24.10.2016
 *
 */
public class PrincipalTest {

	@Test
	public void testHasAnyRoles() {
		final Principal principal = new Principal(42L, "Unittest");
		final PrincipalRole role1 = new PrincipalRole(1L, "Role 1");
		final PrincipalRole role2 = new PrincipalRole(2L, "Role 2");
		final PrincipalRole role3 = new PrincipalRole(3L, "Role 3");

		principal.addRole(role1);
		principal.addRole(role2);

		assertThat(principal.hasAnyRoles(role3), equalTo(false));
		assertThat(principal.hasAnyRoles(role1), equalTo(true));
		assertThat(principal.hasAnyRoles(role1, role3), equalTo(true));
		assertThat(principal.hasAnyRoles(role1, role2), equalTo(true));
	}

	@Test
	public void testHasAllRoles() {
		final Principal principal = new Principal(42L, "Unittest");
		final PrincipalRole role1 = new PrincipalRole(1L, "Role 1");
		final PrincipalRole role2 = new PrincipalRole(2L, "Role 2");
		final PrincipalRole role3 = new PrincipalRole(3L, "Role 3");

		principal.addRole(role1);
		principal.addRole(role2);

		assertThat(principal.hasAllRoles(role3), equalTo(false));
		assertThat(principal.hasAllRoles(role1), equalTo(true));
		assertThat(principal.hasAllRoles(role1, role3), equalTo(false));
		assertThat(principal.hasAllRoles(role1, role2), equalTo(true));
	}

	@Test
	public void testHasRole() {
		final Principal principal = new Principal(42L, "Unittest");
		final PrincipalRole role1 = new PrincipalRole(1L, "Role 1");
		final PrincipalRole role2 = new PrincipalRole(2L, "Role 2");
		final PrincipalRole role3 = new PrincipalRole(3L, "Role 3");

		principal.addRole(role1);
		principal.addRole(role2);

		assertThat(principal.hasRole(role3), equalTo(false));
		assertThat(principal.hasRole(role1), equalTo(true));
	}

	@Test
	public void testGetLocale() {
		final Principal principal = new Principal(42L, "Unittest");
		principal.setLanguageCode("de");

		assertThat(principal.getLocale(), equalTo(Locale.GERMAN));
	}

	@Test
	public void testGetLocaleBR() {
		final Principal principal = new Principal(42L, "Unittest");
		principal.setLanguageCode("br");

		assertThat(principal.getLocale(), equalTo(new Locale("br")));
	}

	@Test
	public void testGetLocaleUS() {
		final Principal principal = new Principal(42L, "Unittest");
		principal.setLanguageCode("us");

		assertThat(principal.getLocale(), equalTo(new Locale("us")));
	}

	@Test
	public void testGetLocaleNull() {
		final Principal principal = new Principal(42L, "Unittest");

		assertThat(principal.getLocale(), nullValue());
	}

	@Test
	public void testHasCapability() {
		final Principal principal = new Principal(42L, "Unittest");

		principal.addCapability("CAP_001", PermissionType.READ);

		assertThat(principal.hasCapability("CAP_001"), equalTo(true));
		assertThat(principal.hasCapability("CAP_002"), equalTo(false));
	}

	@Test
	public void testGetPermissionType() {
		final Principal principal = new Principal(42L, "Unittest");

		principal.addCapability("CAP_001", PermissionType.READ);
		principal.addCapability("CAP_002", PermissionType.EDIT);

		assertThat(principal.getPermissionType("CAP_001"), equalTo(PermissionType.READ));
		assertThat(principal.getPermissionType("CAP_002"), equalTo(PermissionType.EDIT));
		assertThat(principal.getPermissionType("CAP_003"), equalTo(PermissionType.NONE));
	}

}
