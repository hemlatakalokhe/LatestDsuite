package de.bonprix.user.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.bonprix.user.dto.User;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;

/**
 * Use this service for authentication of any service. It encapsulates the
 * fairly complex authentication structure of bonprix. Taking into account that
 * a user might be a real user or a system user and that some users have
 * depending on the client different roles.
 * 
 * This service will hide these complexity and filter just the roles for which
 * this authenticated user is meant for.
 */
@Path("/authenticate")
public interface AuthenticationService {
	/**
	 * Authenticates against user database. The roles are matched against client
	 * and application rules. When successful a user with all granted roles for
	 * this application is returned.
	 *
	 * @param username
	 *            the username which is a login name for the application
	 * @param deprecatedPassword
	 *            plaintext password, @TODO should be removed once all
	 *            webservices use headerPassword
	 * @param headerPassword
	 *            if provider this plaintext password will be taken
	 * @param applicationId
	 *            every application has its own id
	 * @param clientId
	 *            the id of the client to be used
	 * @param generateAuthKey
	 *            when set to true an entry in the persistent_token table will
	 *            be created and the authkey also returned
	 * @return all roles, groups and naming information
	 * @responseMessage 404 if the username does not match any known user
	 * @responseMessage 401 if the password is wrong
	 * @exception NotAuthorizedException
	 *                when the password is wrong
	 * @exception NotFoundException
	 *                when the username is unknown
	 *
	 */
	@GET
	@Path("/user/{username}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User authenticateUser(@NotNull @PathParam("username") final String username,
			@QueryParam("password") final String deprecatedPassword, @HeaderParam("password") String headerPassword,
			@NotNull @QueryParam("applicationId") long applicationId, @NotNull @QueryParam("clientId") long clientId,
			@QueryParam("generateAuthKey") @DefaultValue("false") boolean generateAuthKey)
			throws NotAuthorizedException, NotFoundException;

	/**
	 * Takes a former created key provided by authenticateUser and validates it
	 * against given application. Call this for returning users that provides
	 * the key via cookie.
	 *
	 * @see {@link #authenticateUser(String, String, Long)}
	 *
	 * @param remeberMeKey
	 *            a base64 encoded string containing username, expirationdate
	 *            and a hashed password
	 *
	 * @param applicationId
	 * @return
	 */
	@GET
	@Path("/validate")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User validateRemeberMeKey(@NotNull @QueryParam("key") String remeberMeKey,
			@NotNull @QueryParam("applicationId") Long applicationId) throws NotAuthorizedException;
}