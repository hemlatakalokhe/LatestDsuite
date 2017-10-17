package de.bonprix.service.fetch;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/applicationtypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface FetchService {

	/**
	 * find all applicationTypes with filter
	 * 
	 * @param filter
	 * @param fetchOptions
	 * @return list of applicationTypes
	 */
	@GET
	@Path("/")
	List<Fetch> findAll(@BeanParam @Valid FetchFilter filter, @BeanParam @Valid FetchOptions fetchOptions);

	/**
	 * find applicationType by id
	 * 
	 * @param fetchOptions
	 */
	@GET
	@Path("/{id : \\d+}")
	Fetch findById(@PathParam("id") @NotNull Long id, @BeanParam @Valid FetchOptions fetchOptions);

	/**
	 * delete applicationType by id
	 * 
	 * @errorResponse 404 not found
	 */
	@DELETE
	@Path("/{id : \\d+}")
	void deleteById(@PathParam("id") @NotNull Long id);

	/**
	 * create applicationType
	 * 
	 * @errorResponse 400 bad request (can't create applicationType with id)
	 */
	@POST
	@Path("/")
	long create(@NotNull Fetch applicationType);

	/**
	 * update applicationType
	 * 
	 * @errorResponse 400 bad request (can't update applicationType without id)
	 * @errorResponse 404 not found
	 */
	@PUT
	@Path("/{id : \\d+}")
	void update(@PathParam("id") @NotNull Long id, @NotNull Fetch applicationType);
}
