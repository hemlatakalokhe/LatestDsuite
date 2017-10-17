package de.bonprix.service.fetchlanguage;

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

@Path("/fetchLanguages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface FetchLanguageService {

	/**
	 * find all fetchLanguages with filter
	 * 
	 * @param filter
	 * @param fetchOptions
	 * @return list of fetchLanguages
	 */
	@GET
	@Path("/")
	List<FetchLanguage> findAll(@BeanParam @Valid FetchLanguageFilter filter,
			@BeanParam @Valid FetchLanguageFetchOptions fetchOptions);

	/**
	 * find fetchLanguage by id
	 * 
	 * @param fetchOptions
	 */
	@GET
	@Path("/{id : \\d+}")
	FetchLanguage findById(@PathParam("id") @NotNull Long id, @BeanParam @Valid FetchLanguageFetchOptions fetchOptions);

	/**
	 * delete fetchLanguage by id
	 * 
	 * @errorResponse 404 not found
	 */
	@DELETE
	@Path("/{id : \\d+}")
	void deleteById(@PathParam("id") @NotNull Long id);

	/**
	 * create fetchLanguage
	 * 
	 * @errorResponse 400 bad request (can't create fetchLanguage with id)
	 */
	@POST
	@Path("/")
	long create(@NotNull FetchLanguage fetchLanguage);

	/**
	 * update fetchLanguage
	 * 
	 * @errorResponse 400 bad request (can't update fetchLanguage without id)
	 * @errorResponse 404 not found
	 */
	@PUT
	@Path("/{id : \\d+}")
	void update(@PathParam("id") @NotNull Long id, @NotNull FetchLanguage fetchLanguage);
}
