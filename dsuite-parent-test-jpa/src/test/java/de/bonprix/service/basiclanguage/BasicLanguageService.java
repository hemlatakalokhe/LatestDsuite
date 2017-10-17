package de.bonprix.service.basiclanguage;

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

@Path("/basicLanguages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BasicLanguageService {

	/**
	 * find all basicLanguages with filter
	 * 
	 * @param filter
	 * @return list of basicLanguages
	 */
	@GET
	@Path("/")
	List<BasicLanguage> findAll(@BeanParam @Valid BasicLanguageFilter filter);

	/**
	 * find basicLanguage by id
	 */
	@GET
	@Path("/{id : \\d+}")
	BasicLanguage findById(@PathParam("id") @NotNull Long id);

	/**
	 * delete basicLanguage by id
	 * 
	 * @errorResponse 404 not found
	 */
	@DELETE
	@Path("/{id : \\d+}")
	void deleteById(@PathParam("id") @NotNull Long id);

	/**
	 * create basicLanguage
	 * 
	 * @errorResponse 400 bad request (can't create basicLanguage with id)
	 */
	@POST
	@Path("/")
	long create(@NotNull BasicLanguage basicLanguage);

	/**
	 * update basicLanguage
	 * 
	 * @errorResponse 400 bad request (can't update basicLanguage without id)
	 * @errorResponse 404 not found
	 */
	@PUT
	@Path("/{id : \\d+}")
	void update(@PathParam("id") @NotNull Long id, @NotNull BasicLanguage basicLanguage);
}
