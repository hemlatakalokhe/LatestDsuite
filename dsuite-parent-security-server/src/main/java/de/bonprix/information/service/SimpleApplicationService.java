package de.bonprix.information.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.dto.masterdata.SimpleApplication;
import de.bonprix.dto.masterdata.SimpleApplicationFetchOptions;

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SimpleApplicationService {

	/**
	 * find application by id with fetch options
	 */
	@GET
	@Path("/{id : \\d+}")
	SimpleApplication findById(@PathParam("id") @NotNull Long id,
			@BeanParam @Valid SimpleApplicationFetchOptions fetchOptions);

}
