/**
 *
 */
package de.bonprix.security;

import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.testng.annotations.Test;

import de.bonprix.user.dto.Principal;
import de.bonprix.user.dto.PrincipalRole;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class AnyRoleAccessDecisionManagerTest {

	@Test(expectedExceptions = AccessDeniedException.class)
	public void testDecideNoPrincipal() {
		final Authentication authentication = Mockito.mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(null);

		final AnyRoleAccessDecisionManager manager = new AnyRoleAccessDecisionManager();

		manager.decide(authentication, null, null);
	}

	@Test(expectedExceptions = AccessDeniedException.class)
	public void testDecidePrincipalWrongType() {
		final Authentication authentication = Mockito.mock(Authentication.class);

		when(authentication.getPrincipal()).thenReturn(new Object());

		final AnyRoleAccessDecisionManager manager = new AnyRoleAccessDecisionManager();

		manager.decide(authentication, null, null);
	}

	@Test(expectedExceptions = AccessDeniedException.class)
	public void testDecidePrincipalNoRoles() {
		final Authentication authentication = Mockito.mock(Authentication.class);

		final Principal p = new Principal(42L, "unittest", "Unit Test");

		when(authentication.getPrincipal()).thenReturn(p);

		final AnyRoleAccessDecisionManager manager = new AnyRoleAccessDecisionManager();

		manager.decide(authentication, null, null);
	}

	@Test
	public void testDecidePrincipalOk() {
		final Authentication authentication = Mockito.mock(Authentication.class);

		final Principal p = new Principal(42L, "unittest", "Unit Test");
		p.getPrincipalRoles()
			.add(new PrincipalRole(1L, "someRole"));

		when(authentication.getPrincipal()).thenReturn(p);

		final AnyRoleAccessDecisionManager manager = new AnyRoleAccessDecisionManager();

		manager.decide(authentication, null, null);
	}

}
