/**
 *
 */
package de.bonprix.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author cthiel
 * @date 28.10.2016
 *
 */
public class BonprixRememberMeAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        return authentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return BonprixAuthentication.class.isAssignableFrom(authentication);
    }

}
