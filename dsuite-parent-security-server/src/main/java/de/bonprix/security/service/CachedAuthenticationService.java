package de.bonprix.security.service;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * Cached variant for @AuthenticationService
 */
public class CachedAuthenticationService implements AuthenticationService {

	private static final Logger LOG = LoggerFactory.getLogger(CachedAuthenticationService.class);

	private static final String WEBSERVICE_AUTHENTICATION = "webservice-auth";

	private AuthenticationService delegatedAuthenticationService;

	public void setDelegatedAuthenticationService(final AuthenticationService delegatedAuthenticationService) {
		this.delegatedAuthenticationService = delegatedAuthenticationService;
	}

	@Override
	@Cacheable(CachedAuthenticationService.WEBSERVICE_AUTHENTICATION)
	public User authenticateUser(final String username, final String deprecatedPassword, final String headerPassword,
			final long applicationId, final long clientId, final boolean generateAuthKey)
			throws NotAuthorizedException, NotFoundException {
		if (CachedAuthenticationService.LOG.isTraceEnabled()) {
			CachedAuthenticationService.LOG.trace("called authenticateUser {}", username);
		}
		return this.delegatedAuthenticationService.authenticateUser(username, deprecatedPassword, headerPassword,
																	applicationId, clientId, generateAuthKey);
	}

	@Override
	@Cacheable(CachedAuthenticationService.WEBSERVICE_AUTHENTICATION)
	public User validateRemeberMeKey(final String remeberMeKey, final Long applicationId)
			throws NotAuthorizedException {
		return this.delegatedAuthenticationService.validateRemeberMeKey(remeberMeKey, applicationId);
	}
}
