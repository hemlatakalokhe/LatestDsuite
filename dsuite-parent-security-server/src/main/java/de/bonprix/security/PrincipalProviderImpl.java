/**
 *
 */
package de.bonprix.security;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Principal;

/**
 * @author cthiel
 * @date 18.11.2016
 *
 */
@Component
public class PrincipalProviderImpl implements PrincipalProvider {

	@Override
	public Principal getAuthenticatedPrincipal() {
		return PrincipalSecurityContext.getAuthenticatedPrincipal();
	}

	@Override
	public Principal getRootPrincipal() {
		return PrincipalSecurityContext.getRootPrincipal();
	}

	@Override
	public Boolean hasCapability(String capabilityKey) {
		if (StringUtils.isEmpty(capabilityKey)) {
			return Boolean.TRUE;
		}
		return !hasPermission(capabilityKey, PermissionType.NONE);
	}

	@Override
	public Boolean hasPermission(String capabilityKey, PermissionType permissionType) {
		return PrincipalSecurityContext.hasPermission(capabilityKey, permissionType);
	}

}
