package de.bonprix.user.dto;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.user.service.ApplicationPreferences;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

@Path("/preferences")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public interface PreferencesService {
	/**
	 * fetches all application specific principal preferences in a List of
	 * keyValue
	 *
	 * @param principalId
	 * @param applicationId
	 * @param clientId
	 * @return all found keyValue pairs in an applicationPreferences wrapper
	 */
	@GET
	@Path("/{principalId}/{applicationId}/{clientId}")
	public ApplicationPreferences findApplicationPreferences(@NotNull @PathParam("principalId") long principalId,
			@NotNull @PathParam("applicationId") long applicationId, @NotNull @PathParam("clientId") long clientId);

	/**
	 * stores all application specific principal preferences in a List of
	 * keyValues<br>
	 * this service just updates/creates the sent keyValues in the db not sent
	 * application and principal keyValues that are not within this map still
	 * exist
	 *
	 * @param applicationPreferences
	 *            contains the list of keyValues and the setted applicationId
	 */
	@POST
	@Path("/")
	public void updateApplicationPreferences(@NotNull ApplicationPreferences applicationPreferences);
}