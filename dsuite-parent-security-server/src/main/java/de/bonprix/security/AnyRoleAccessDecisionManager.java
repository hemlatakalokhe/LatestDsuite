package de.bonprix.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import de.bonprix.user.dto.Principal;

/**
 * Checks whether the current principal does have any role for the current
 * application.
 */
public class AnyRoleAccessDecisionManager implements AccessDecisionManager {

	private static final Logger LOG = LoggerFactory.getLogger(AnyRoleAccessDecisionManager.class);

	@Override
	public void decide(final Authentication authentication, final Object object,
			final Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		if (authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof Principal)) {
			throw new AccessDeniedException("Principal not set or of wrong type!");
		}

		final Principal principal = (Principal) authentication.getPrincipal();

		if (principal.getPrincipalRoles() == null || principal.getPrincipalRoles()
			.isEmpty()) {
			AnyRoleAccessDecisionManager.LOG.trace("User {} does not have any roles!", principal.getName());
			throw new AccessDeniedException(principal.getName() + " does not have any roles!");
		}
		AnyRoleAccessDecisionManager.LOG.trace(	"User {} does have the following roles {}", principal.getName(),
												principal.getPrincipalRoles());
	}

	@Override
	public boolean supports(final ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

}
