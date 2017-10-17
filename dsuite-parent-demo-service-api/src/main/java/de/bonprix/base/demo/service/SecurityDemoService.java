package de.bonprix.base.demo.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.base.demo.dto.SecurityInfo;

/**
 * This is a security demo service to demonstrate the different principals.
 *
 * @author cthiel
 * @date 31.10.2016
 *
 */
@FunctionalInterface
@Path("/securitydemo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SecurityDemoService {

	/**
	 * Returns a security info object with information about the principals of
	 * this webservice request.
	 *
	 * @return the security info
	 */
	@GET
	@Path("/")
	SecurityInfo get();

}
