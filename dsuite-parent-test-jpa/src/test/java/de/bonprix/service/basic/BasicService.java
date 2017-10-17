package de.bonprix.service.basic;

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
public interface BasicService {

	/**
	 * find all applicationTypes with filter
	 * 
	 * @param filter
	 * @return list of applicationTypes
	 */
	@GET
	@Path("/")
	List<Basic> findAll(@BeanParam @Valid BasicFilter filter);

	/**
	 * find applicationType by id
	 */
	@GET
	@Path("/{id : \\d+}")
	Basic findById(@PathParam("id") @NotNull Long id);

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
	long create(@NotNull Basic applicationType);

	/**
	 * update applicationType
	 * 
	 * @errorResponse 400 bad request (can't update applicationType without id)
	 * @errorResponse 404 not found
	 */
	@PUT
	@Path("/{id : \\d+}")
	void update(@PathParam("id") @NotNull Long id, @NotNull Basic applicationType);
}
