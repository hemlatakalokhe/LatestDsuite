package de.bonprix.base.demo.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.bonprix.base.demo.dto.Client;

/**
 * This is a demo service for showing how a simple CRUD would look like. It lists a dummy list of applications and can perform create, read, update and delete
 * operations on it.
 *
 * @author h.kalokhe
 * @date 20.06.17
 *
 */
@Path("/cleints")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ClientService {

    /**
     * Paged read of all applications.
     *
     * @param pageable the pageable
     * @return a paged list of all applications
     */
    @GET
    @Path("/")
    List<Client> findAll();

}
