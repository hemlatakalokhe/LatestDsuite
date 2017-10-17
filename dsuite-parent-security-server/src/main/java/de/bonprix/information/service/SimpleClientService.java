package de.bonprix.information.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.dto.masterdata.SimpleClient;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SimpleClientService {

	/**
	 * find client by id
	 */
	@GET
	@Path("/{id : \\d+}")
	SimpleClient findById(@PathParam("id") @NotNull Long id);

}
