/**
 *
 */
package de.bonprix.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import de.bonprix.user.dto.Principal;

/**
 * Bonprix authentication token used for security context. This class stores the
 * principal+authKey, root-principal and the authorities of the principal.
 *
 * @author cthiel
 * @date 18.10.2016
 *
 */
public class BonprixAuthentication extends PreAuthenticatedAuthenticationToken {

	private static final long serialVersionUID = 3530092313645955112L;

	private Principal rootPrincipal;

	private String rootAuthKey;

	/**
	 * Creates a new authentication token. The root principal will be the same
	 * as the authentication principal.
	 *
	 * @param principal
	 * @param authKey
	 * @param anAuthorities
	 */
	public BonprixAuthentication(final Principal principal, final String authKey,
			final Collection<? extends GrantedAuthority> anAuthorities) {
		super(principal, authKey, anAuthorities);

		this.rootPrincipal = principal;
		this.rootAuthKey = authKey;
	}

	public Principal getRootPrincipal() {
		return this.rootPrincipal;
	}

	public void setRootPrincipal(final Principal rootPrincipal) {
		this.rootPrincipal = rootPrincipal;
	}

	@Override
	public Principal getPrincipal() {
		return (Principal) super.getPrincipal();
	}

	public String getAuthKey() {
		return (String) getCredentials();
	}

	/**
	 * @return the rootAuthKey
	 */
	public String getRootAuthKey() {
		return this.rootAuthKey;
	}

	/**
	 * @param rootAuthKey
	 *            the rootAuthKey to set
	 */
	public void setRootAuthKey(final String rootAuthKey) {
		this.rootAuthKey = rootAuthKey;
	}

}
