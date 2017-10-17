/**
 *
 */
package de.bonprix.security;

import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.exception.WrongPrincipalTypeException;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Principal;

/**
 * Spring bean helper class for {@link PrincipalSecurityContext}. Actually this
 * bean has the very same functionality as the {@link PrincipalSecurityContext}
 * as it simply forwards all calls to that class. It is simply a helper class
 * for not using static method access in presenters or other beans so Unit tests
 * are a bit more simple because the dependency can be mocked.
 *
 * @author cthiel
 * @date 18.11.2016
 *
 */
public interface PrincipalProvider {

	/**
	 * Search for principal set by Spring Security.
	 *
	 * @return the current principal otherwise an Exception is risen
	 * @throws PrincipalMissingException
	 *             when no Principal is set at all
	 * @throws WrongPrincipalTypeException
	 *             when principal in SecurityContext is of wrong type
	 */
	Principal getRootPrincipal();

	/**
	 * Search for principal set by Spring Security.
	 *
	 * @return the current principal otherwise an Exception is risen
	 * @throws PrincipalMissingException
	 *             when no Principal is set at all
	 * @throws WrongPrincipalTypeException
	 *             when principal in SecurityContext is of wrong type
	 */
	Principal getAuthenticatedPrincipal();

	/**
	 * Checks from the capability map whether a capabilityKey resolves the
	 * target to be visible/invisible. Used primarily to resolve access
	 * permission for views/navigation.
	 * 
	 * @param capabilityKey
	 * @return True if View is visible
	 */
	Boolean hasCapability(String capabilityKey);

	/**
	 * Search for the capability key in the principal capability list and checks
	 * the permission types.
	 * 
	 * @param capabilityKey
	 * @param permissionType
	 */
	Boolean hasPermission(String capabilityKey, PermissionType permissionType);

}